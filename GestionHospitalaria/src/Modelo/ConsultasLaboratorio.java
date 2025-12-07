package Modelo;

import java.sql.*;
import java.util.ArrayList;

/**
 * Clase que maneja las consultas de laboratorio a la base de datos
 * @author mms24
 */
public class ConsultasLaboratorio extends Conexion {
    
    /**
     * Busca pacientes por nombre
     * @param criterio criterio de búsqueda
     * @return lista con formato "ID - Nombre Apellido"
     */
    public ArrayList<String> buscarPacientes(String criterio) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();
        ArrayList<String> pacientes = new ArrayList<>();
        
        String sql = "SELECT p.id_paciente, u.nombre, u.apellido " +
                     "FROM paciente p " +
                     "INNER JOIN usuario u ON p.id_usuario = u.id_usuario " +
                     "WHERE u.nombre LIKE ? OR u.apellido LIKE ?";
        
        try {
            ps = con.prepareStatement(sql);
            String busqueda = "%" + criterio + "%";
            ps.setString(1, busqueda);
            ps.setString(2, busqueda);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                int id = rs.getInt("id_paciente");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                pacientes.add(id + " - " + nombre + " " + apellido);
            }
            
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
        
        return pacientes;
    }
    
    /**
     * Obtiene el ID del expediente de un paciente
     * @param idPaciente ID del paciente
     * @return ID del expediente o -1 si no existe
     */
    public int obtenerIdExpediente(int idPaciente) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();
        
        String sql = "SELECT id_expediente FROM expediente WHERE id_paciente = ?";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, idPaciente);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("id_expediente");
            }
            
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
        
        return -1;
    }
    
    /**
     * Registra un nuevo resultado de laboratorio
     * @param laboratorio objeto con los datos del resultado
     * @return true si se guardó correctamente
     */
    public boolean registrar(ResultadoLaboratorio laboratorio) {
        PreparedStatement ps = null;
        Connection con = getConexion();
        
        String sql = "INSERT INTO laboratorio (id_expediente, id_tecnico, expediente_laboratorio, procedimientos, resultados) " +
                     "VALUES (?, ?, ?, ?, ?)";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, laboratorio.getIdExpediente());
            ps.setInt(2, laboratorio.getIdTecnico());
            ps.setString(3, laboratorio.getExpedienteLaboratorio());
            ps.setString(4, laboratorio.getProcedimientos());
            ps.setString(5, laboratorio.getResultados());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
    }
}
