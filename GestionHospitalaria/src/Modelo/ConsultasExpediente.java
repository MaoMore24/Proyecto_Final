package Modelo;

import java.sql.*;
import java.util.ArrayList;

public class ConsultasExpediente extends Conexion {

    // Obtener ID de Expediente por ID de Paciente
    public int obtenerIdExpediente(int idPaciente) {
        Connection con = getConexion();
        String sql = "SELECT id FROM expediente WHERE id_paciente = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idPaciente);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
        return -1; // No encontrado
    }

    // Registrar Diagnóstico
    public boolean registrarDiagnostico(Diagnostico diag) {
        Connection con = getConexion();
        String sql = "INSERT INTO diagnostico (id_expediente, id_medico, padecimientos, diagnostico, notas, fecha) VALUES (?, ?, ?, ?, ?, NOW())";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, diag.getIdExpediente());
            ps.setInt(2, diag.getIdMedico());
            ps.setString(3, diag.getPadecimientos());
            ps.setString(4, diag.getDiagnostico());
            ps.setString(5, diag.getNotas());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
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
}
