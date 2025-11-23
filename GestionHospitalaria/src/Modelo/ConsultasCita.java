package Modelo;

import java.sql.*;

public class ConsultasCita extends Conexion {

    public boolean registrar(Cita cita) {
        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "INSERT INTO cita (id_paciente, id_medico, fecha_hora, motivo, estado) VALUES (?,?,?,?,?)";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, cita.getIdPaciente());
            ps.setInt(2, cita.getIdMedico());
            ps.setString(3, cita.getFechaHora());
            ps.setString(4, cita.getMotivo());
            ps.setString(5, cita.getEstado());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
    }

    public boolean modificar(Cita cita) {
        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "UPDATE cita SET id_paciente=?, id_medico=?, fecha_hora=?, motivo=?, estado=? WHERE id=?";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, cita.getIdPaciente());
            ps.setInt(2, cita.getIdMedico());
            ps.setString(3, cita.getFechaHora());
            ps.setString(4, cita.getMotivo());
            ps.setString(5, cita.getEstado());
            ps.setInt(6, cita.getId());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
    }

    public boolean eliminar(Cita cita) {
        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "DELETE FROM cita WHERE id=?";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, cita.getId());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
    }

    public boolean buscar(Cita cita) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();

        String sql = "SELECT * FROM cita WHERE id=?";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, cita.getId());
            rs = ps.executeQuery();

            if (rs.next()) {
                cita.setId(rs.getInt("id"));
                cita.setIdPaciente(rs.getInt("id_paciente"));
                cita.setIdMedico(rs.getInt("id_medico"));
                cita.setFechaHora(rs.getString("fecha_hora"));
                cita.setMotivo(rs.getString("motivo"));
                cita.setEstado(rs.getString("estado"));
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
    }

    public java.util.ArrayList<java.util.Map<String, String>> listarAgenda(int idMedico, String fecha) {
        java.util.ArrayList<java.util.Map<String, String>> lista = new java.util.ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();

        // Query joining with Paciente/Usuario to get names
        String sql = "SELECT c.fecha_hora, u.nombre, u.apellido, c.motivo, c.estado " +
                     "FROM cita c " +
                     "INNER JOIN paciente p ON c.id_paciente = p.id " +
                     "INNER JOIN usuario u ON p.id = u.id " +
                     "WHERE c.id_medico = ? AND DATE(c.fecha_hora) = ? " +
                     "ORDER BY c.fecha_hora ASC";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, idMedico);
            ps.setString(2, fecha);
            rs = ps.executeQuery();

            while (rs.next()) {
                java.util.Map<String, String> fila = new java.util.HashMap<>();
                fila.put("hora", rs.getString("fecha_hora").split(" ")[1]); // Solo la hora
                fila.put("paciente", rs.getString("nombre") + " " + rs.getString("apellido"));
                fila.put("motivo", rs.getString("motivo"));
                fila.put("estado", rs.getString("estado"));
                lista.add(fila);
            }
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
        return lista;
    }
}
