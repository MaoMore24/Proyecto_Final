package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConsultasPaciente extends ConsultasUsuario {
    
    public boolean registrarPaciente(Paciente pac) {
        PreparedStatement ps = null;
        Connection con = getConexion();
        
        try {
            con.setAutoCommit(false); // Iniciar transacción
            
            // 1. Insertar en tabla USUARIO
            // Campos: username, password, nombre, apellido, email, id_rol
            String sqlUsuario = "INSERT INTO usuario (username, password, nombre, apellido, email, id_rol) VALUES (?, ?, ?, ?, ?, ?)";
            
            ps = con.prepareStatement(sqlUsuario, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, pac.getUsername());
            ps.setString(2, pac.getPassword()); // Sin encriptación
            ps.setString(3, pac.getNombre());
            ps.setString(4, pac.getApellido());
            ps.setString(5, pac.getEmail());
            ps.setInt(6, 3); // Rol 3 = Paciente
            
            int filas = ps.executeUpdate();
            if (filas == 0) {
                throw new SQLException("No se pudo crear el usuario.");
            }
            
            // Obtener el ID generado automáticamente
            ResultSet rs = ps.getGeneratedKeys();
            int idUsuario = -1;
            if (rs.next()) {
                idUsuario = rs.getInt(1);
            } else {
                throw new SQLException("Error al obtener el ID del usuario.");
            }
            
            // 2. Insertar en tabla PACIENTE
            // Campos: id, fecha_nacimiento, telefono, direccion
            String sqlPaciente = "INSERT INTO paciente (id, fecha_nacimiento, telefono, direccion) VALUES (?, ?, ?, ?)";
            ps = con.prepareStatement(sqlPaciente);
            ps.setInt(1, idUsuario);
            ps.setDate(2, pac.getFechaNacimiento());
            ps.setString(3, pac.getTelefono());
            ps.setString(4, pac.getDireccion());
            ps.execute();
            
            // 3. Crear Expediente Médico inicial
            String sqlExpediente = "INSERT INTO expediente (id_paciente, fecha_creacion) VALUES (?, NOW())";
            ps = con.prepareStatement(sqlExpediente);
            ps.setInt(1, idUsuario);
            ps.execute();
            
            con.commit(); // Confirmar cambios
            return true;
            
        } catch (SQLException e) {
            System.err.println("Error SQL: " + e.getMessage());
            try {
                con.rollback(); // Deshacer cambios si algo falla
            } catch (SQLException ex) {
                System.err.println("Error Rollback: " + ex.getMessage());
            }
            return false;
        } finally {
            try {
                if (con != null) {
                    con.setAutoCommit(true);
                    con.close();
                }
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
    }
}
