package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JComboBox;

public class ConsultasMedico extends ConsultasUsuario {
    
    // Método para registrar médico (Usuario + Médico)
    public boolean registrar(Medico med) {
        PreparedStatement ps = null;
        Connection con = getConexion();
        
        try {
            con.setAutoCommit(false);
            
            // 1. Insertar Usuario
            String sqlUsuario = "INSERT INTO usuario (username, password, nombre, apellido, email, id_rol) VALUES (?, ?, ?, ?, ?, ?)";
            ps = con.prepareStatement(sqlUsuario, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, med.getUsername());
            ps.setString(2, med.getPassword());
            ps.setString(3, med.getNombre());
            ps.setString(4, med.getApellido());
            ps.setString(5, med.getEmail());
            ps.setInt(6, 2); // Rol 2 = Medico
            
            if (ps.executeUpdate() == 0) throw new SQLException("No se pudo crear usuario");
            
            ResultSet rs = ps.getGeneratedKeys();
            int idUsuario = -1;
            if (rs.next()) idUsuario = rs.getInt(1);
            
            // 2. Insertar Médico
            String sqlMedico = "INSERT INTO medico (id, id_especialidad, cedula_profesional) VALUES (?, ?, ?)";
            ps = con.prepareStatement(sqlMedico);
            ps.setInt(1, idUsuario);
            ps.setInt(2, med.getIdEspecialidad());
            ps.setString(3, med.getCedula());
            ps.execute();
            
            con.commit();
            return true;
            
        } catch (SQLException e) {
            System.err.println("Error Registrar Medico: " + e);
            try { con.rollback(); } catch (SQLException ex) {}
            return false;
        } finally {
            try { if(con!=null) { con.setAutoCommit(true); con.close(); } } catch (SQLException e) {}
        }
    }
    
    // Método para modificar médico
    public boolean modificar(Medico med) {
        PreparedStatement ps = null;
        Connection con = getConexion();
        
        try {
            con.setAutoCommit(false);
            
            // Actualizar Usuario
            String sqlUsuario = "UPDATE usuario SET username=?, nombre=?, apellido=?, email=? WHERE id=?";
            ps = con.prepareStatement(sqlUsuario);
            ps.setString(1, med.getUsername());
            ps.setString(2, med.getNombre());
            ps.setString(3, med.getApellido());
            ps.setString(4, med.getEmail());
            ps.setInt(5, med.getId());
            ps.executeUpdate();
            
            // Actualizar Médico
            String sqlMedico = "UPDATE medico SET id_especialidad=?, cedula_profesional=? WHERE id=?";
            ps = con.prepareStatement(sqlMedico);
            ps.setInt(1, med.getIdEspecialidad());
            ps.setString(2, med.getCedula());
            ps.setInt(3, med.getId());
            ps.executeUpdate();
            
            con.commit();
            return true;
            
        } catch (SQLException e) {
            System.err.println("Error Modificar: " + e);
            try { con.rollback(); } catch (SQLException ex) {}
            return false;
        } finally {
            try { if(con!=null) { con.setAutoCommit(true); con.close(); } } catch (SQLException e) {}
        }
    }
    
    // Método para eliminar médico
    public boolean eliminar(Medico med) {
        PreparedStatement ps = null;
        Connection con = getConexion();
        
        try {
            // Eliminar de tabla medico primero (por FK)
            String sql = "DELETE FROM medico WHERE id=?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, med.getId());
            ps.execute();
            
            // Eliminar de tabla usuario
            sql = "DELETE FROM usuario WHERE id=?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, med.getId());
            ps.execute();
            
            return true;
        } catch (SQLException e) {
            System.err.println("Error Eliminar: " + e);
            return false;
        } finally {
            try { if(con!=null) con.close(); } catch (SQLException e) {}
        }
    }
    
    // Método para llenar ComboBox de Especialidades
    public void cargarEspecialidades(JComboBox cmb) {
        Connection con = getConexion();
        String sql = "SELECT * FROM especialidad";
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            cmb.removeAllItems();
            while(rs.next()) {
                // Guardamos el objeto o un string formateado "ID - Nombre"
                // Para simplificar, guardaremos el nombre y manejaremos el ID buscando o usando una clase auxiliar Item
                // Aquí asumiremos que el combo guarda Strings y buscaremos el ID luego, o mejor:
                // Creamos una clase interna o usamos un truco de String
                cmb.addItem(rs.getInt("id") + " - " + rs.getString("nombre"));
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
    }
    
    // Método para listar médicos en la tabla
    public ArrayList<Medico> listarMedicos() {
        ArrayList<Medico> lista = new ArrayList<>();
        Connection con = getConexion();
        String sql = "SELECT u.id, u.username, u.nombre, u.apellido, u.email, m.cedula_profesional, e.nombre as nombre_esp, e.id as id_esp " +
                     "FROM usuario u " +
                     "JOIN medico m ON u.id = m.id " +
                     "JOIN especialidad e ON m.id_especialidad = e.id";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Medico m = new Medico();
                m.setId(rs.getInt("id"));
                m.setUsername(rs.getString("username"));
                m.setNombre(rs.getString("nombre"));
                m.setApellido(rs.getString("apellido"));
                m.setEmail(rs.getString("email"));
                m.setCedula(rs.getString("cedula_profesional"));
                m.setNombreEspecialidad(rs.getString("nombre_esp"));
                m.setIdEspecialidad(rs.getInt("id_esp"));
                lista.add(m);
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
        return lista;
    }
    
    // Método para agregar horario
    public boolean agregarHorario(int idMedico, String dia, String inicio, String fin) {
        Connection con = getConexion();
        String sql = "INSERT INTO horario_medico (id_medico, dia_semana, hora_inicio, hora_fin) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idMedico);
            ps.setString(2, dia);
            ps.setString(3, inicio);
            ps.setString(4, fin);
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.err.println("Error Horario: " + e);
            return false;
        }
    }
}
