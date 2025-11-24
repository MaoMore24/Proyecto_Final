package Modelo;

import Modelo.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ConsultasUsuario extends Conexion {

    // Método para encriptar contraseña con SHA-256
    protected String sha256(String base) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public boolean registrar(Usuario usr) {
        PreparedStatement ps = null;
        Connection con = getConexion();
        
        // Encriptar contraseña antes de guardar
        String passEncriptada = sha256(usr.getPassword());

        String sql = "INSERT INTO usuario (username, password, nombre, apellido, email, id_rol) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, usr.getUsername());
            ps.setString(2, passEncriptada);
            ps.setString(3, usr.getNombre());
            ps.setString(4, usr.getApellido());
            ps.setString(5, usr.getEmail());
            ps.setInt(6, usr.getId_rol());
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

    public Usuario login(String username, String password) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();
        Usuario usr = null;

        // Encriptar la contraseña recibida para compararla
        String passEncriptada = sha256(password);

        String sql = "SELECT u.id, u.username, u.nombre, u.apellido, u.email, u.id_rol, r.nombre as nombre_rol " +
                     "FROM usuario u INNER JOIN rol r ON u.id_rol = r.id " +
                     "WHERE u.username = ? AND u.password = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, passEncriptada); // Comparar con la encriptada
            rs = ps.executeQuery();

            if (rs.next()) {
                usr = new Usuario();
                usr.setId(rs.getInt("id"));
                usr.setUsername(rs.getString("username"));
                usr.setNombre(rs.getString("nombre"));
                usr.setApellido(rs.getString("apellido"));
                usr.setEmail(rs.getString("email"));
                usr.setId_rol(rs.getInt("id_rol"));
                usr.setNombre_rol(rs.getString("nombre_rol"));
            }
            return usr;
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
    }
}
