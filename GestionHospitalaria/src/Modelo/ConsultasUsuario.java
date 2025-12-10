package Modelo;

import Modelo.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConsultasUsuario extends Conexion {

    public boolean registrar(Usuario usr) {
        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "INSERT INTO usuario (username, password, nombre, apellido, email, id_rol) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, usr.getUsername());
            ps.setString(2, usr.getPassword()); // Sin encriptación
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

        String sql = "SELECT u.id, u.username, u.nombre, u.apellido, u.email, u.id_rol, r.nombre as nombre_rol " +
                     "FROM usuario u INNER JOIN rol r ON u.id_rol = r.id " +
                     "WHERE u.username = ? AND u.password = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password); // Sin encriptación
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
    /**
     * Carga los roles en un ComboBox
     * @param cmb ComboBox donde se cargarán los datos
     */
    public void cargarRoles(javax.swing.JComboBox cmb) {
        java.sql.Statement st = null;
        ResultSet rs = null;
        Connection con = getConexion();
        // Excluimos rol 3 (Paciente) porque requieren datos adicionales (fecha nac, etc)
        // que este formulario genérico no maneja.
        String sql = "SELECT id, nombre FROM rol WHERE id <> 3 ORDER BY id";
        
        try {
            st = con.createStatement();
            rs = st.executeQuery(sql);
            cmb.removeAllItems();
            while (rs.next()) {
                cmb.addItem(rs.getInt("id") + " - " + rs.getString("nombre"));
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
    }
}
