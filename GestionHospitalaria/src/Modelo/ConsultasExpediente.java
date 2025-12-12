package Modelo;

import java.sql.*;
import java.util.ArrayList;

public class ConsultasExpediente extends Conexion {

    // Obtener ID de Expediente por ID de Paciente
    public int obtenerIdExpediente(int idPaciente) {
        Connection con = getConexion();
        // Seleccionamos todo para ver qué columnas hay disponibles (aunque JDBC no deja ver nombres sin metadata, intentaremos leer ambas posibles columnas)
        String sql = "SELECT * FROM expediente WHERE id_paciente = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idPaciente);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                // Intentamos 'id' primero, si falla, 'id_expediente'
                try {
                    return rs.getInt("id");
                } catch (SQLException ex) {
                    return rs.getInt("id_expediente");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error obtenerIdExpediente: " + e);
        }
        return -1; // No encontrado
    }
    
    // Método para crear expediente automáticamente si no existe
    public int crearExpediente(int idPaciente) {
        Connection con = getConexion();
        String sql = "INSERT INTO expediente (id_paciente) VALUES (?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, idPaciente);
            ps.executeUpdate();
            
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error crearExpediente: " + e);
        }
        return -1;
    }

    // Registrar Diagnóstico
    public boolean registrarDiagnostico(Diagnostico diag) {
        Connection con = getConexion();
        try {
            // 1. Generar ID manual (ya que no es AUTO_INCREMENT en la BD del usuario)
            int nextId = 1;
            Statement st = con.createStatement();
            ResultSet rsID = st.executeQuery("SELECT MAX(id_diagnostico) FROM diagnostico");
            if(rsID.next()) {
                nextId = rsID.getInt(1) + 1;
            }
            
            // 2. Insertar usando el ID generado permitiendo id_paciente
            // Usamos la columna 'notas' por defecto
            String sql = "INSERT INTO diagnostico (id_diagnostico, id_expediente, id_medico, id_paciente, padecimientos, diagnostico, notas, fecha) VALUES (?, ?, ?, ?, ?, ?, ?, NOW())";
            
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, nextId);
            ps.setInt(2, diag.getIdExpediente());
            ps.setInt(3, diag.getIdMedico()); 
            ps.setInt(4, diag.getIdPaciente());
            ps.setString(5, diag.getPadecimientos());
            ps.setString(6, diag.getDiagnostico());
            ps.setString(7, diag.getNotas());
            ps.execute();
            return true;
            
        } catch (SQLException e) {
            System.err.println("Error registrarDiagnostico: " + e);
            
            // Intento de fallback si falla por nombre de columna 'notas' vs 'examen_fisico'
            if (e.getMessage().contains("Unknown column 'notas'")) {
                 // Aquí podríamos intentar insertar en 'examen_fisico' recursivamente o con otro query
                 // Pero por ahora asumimos que 'notas' es lo correcto según los logs previos.
            }
            return false;
        }
    }

    // Registrar Receta
    public boolean registrarReceta(Receta rec) {
        Connection con = getConexion();
        String sql = "INSERT INTO receta (id_expediente, id_medico, medicamentos, instrucciones, fecha) VALUES (?, ?, ?, ?, NOW())";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, rec.getIdExpediente());
            ps.setInt(2, rec.getIdMedico());
            ps.setString(3, rec.getMedicamentos());
            ps.setString(4, rec.getInstrucciones());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }
    }

    // Registrar Examen
    public boolean registrarExamen(Examen ex) {
        Connection con = getConexion();
        String sql = "INSERT INTO examen (id_expediente, id_medico, tipo_examen, resultados, fecha) VALUES (?, ?, ?, ?, NOW())";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, ex.getIdExpediente());
            ps.setInt(2, ex.getIdMedico());
            ps.setString(3, ex.getTipoExamen());
            ps.setString(4, ex.getResultados());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }
    }
    
    // Buscar Paciente por Cédula o Nombre (para facilitar la búsqueda en la vista)
    // Retorna una lista de Strings "ID - Nombre Apellido"
    public ArrayList<String> buscarPacientes(String criterio) {
        ArrayList<String> lista = new ArrayList<>();
        Connection con = getConexion();
        String sql = "SELECT u.id, u.nombre, u.apellido FROM usuario u " +
                     "JOIN paciente p ON u.id = p.id " +
                     "WHERE u.nombre LIKE ? OR u.apellido LIKE ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "%" + criterio + "%");
            ps.setString(2, "%" + criterio + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(rs.getInt("id") + " - " + rs.getString("nombre") + " " + rs.getString("apellido"));
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
        return lista;
    }

    // Listar Historial de Diagnósticos
    public ArrayList<Diagnostico> listarDiagnosticos(int idExpediente) {
        ArrayList<Diagnostico> lista = new ArrayList<>();
        Connection con = getConexion();
        String sql = "SELECT * FROM diagnostico WHERE id_expediente = ? ORDER BY fecha DESC";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idExpediente);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Diagnostico d = new Diagnostico();
                d.setId(rs.getInt("id_diagnostico"));
                d.setFecha(rs.getDate("fecha"));
                d.setPadecimientos(rs.getString("padecimientos"));
                d.setDiagnostico(rs.getString("diagnostico"));
                
                // Intento robusto de leer notas o examen_fisico
                try { 
                    d.setNotas(rs.getString("notas")); 
                } catch(SQLException e) { 
                    try { 
                        d.setNotas(rs.getString("examen_fisico")); 
                    } catch(SQLException ex) {} 
                }
                
                lista.add(d);
            }
        } catch (SQLException e) {
            System.err.println("Error listarDiagnosticos: " + e);
        }
        return lista;
    }

    // Listar Historial de Recetas
    public ArrayList<Receta> listarRecetas(int idExpediente) {
        ArrayList<Receta> lista = new ArrayList<>();
        Connection con = getConexion();
        String sql = "SELECT * FROM receta WHERE id_expediente = ? ORDER BY fecha DESC";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idExpediente);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Receta r = new Receta();
                r.setFecha(rs.getDate("fecha"));
                r.setMedicamentos(rs.getString("medicamentos"));
                r.setInstrucciones(rs.getString("instrucciones"));
                lista.add(r);
            }
        } catch (SQLException e) {
            System.err.println("Error listarRecetas: " + e);
        }
        return lista;
    }

    // Listar Historial de Exámenes
    public ArrayList<Examen> listarExamenes(int idExpediente) {
        ArrayList<Examen> lista = new ArrayList<>();
        Connection con = getConexion();
        String sql = "SELECT * FROM examen WHERE id_expediente = ? ORDER BY fecha DESC";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idExpediente);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Examen ex = new Examen();
                ex.setFecha(rs.getDate("fecha"));
                ex.setTipoExamen(rs.getString("tipo_examen"));
                ex.setResultados(rs.getString("resultados"));
                lista.add(ex);
            }
        } catch (SQLException e) {
            System.err.println("Error listarExamenes: " + e);
        }
        return lista;
    }
}
