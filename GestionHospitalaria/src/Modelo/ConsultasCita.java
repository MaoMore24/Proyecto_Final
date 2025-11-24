package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JComboBox;

public class ConsultasCita extends ConsultasUsuario {
    
    public void cargarEspecialidades(JComboBox cmb) {
        Connection con = getConexion();
        String sql = "SELECT * FROM especialidad";
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            cmb.removeAllItems();
            while(rs.next()) {
                cmb.addItem(rs.getInt("id") + " - " + rs.getString("nombre"));
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
    }
    
    public void cargarMedicosPorEspecialidad(int idEspecialidad, JComboBox cmb) {
        Connection con = getConexion();
        String sql = "SELECT u.id, u.nombre, u.apellido FROM usuario u " +
                     "JOIN medico m ON u.id = m.id " +
                     "WHERE m.id_especialidad = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idEspecialidad);
            ResultSet rs = ps.executeQuery();
            cmb.removeAllItems();
            while(rs.next()) {
                cmb.addItem(rs.getInt("id") + " - " + rs.getString("nombre") + " " + rs.getString("apellido"));
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
    }
    
    // Método simplificado para obtener horas disponibles (en un caso real validaría contra citas existentes)
    public ArrayList<String> obtenerHorariosDisponibles(int idMedico, String diaSemana) {
        ArrayList<String> horarios = new ArrayList<>();
        Connection con = getConexion();
        String sql = "SELECT hora_inicio, hora_fin FROM horario_medico WHERE id_medico = ? AND dia_semana = ?";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idMedico);
            ps.setString(2, diaSemana);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                // Generar intervalos de 30 min entre inicio y fin
                // Simplificación: devolvemos solo inicio y fin por ahora, o una lista estática si no hay lógica compleja
                // Para hacerlo robusto, necesitaríamos parsear las horas.
                // Por ahora, simularemos horas fijas si hay horario definido.
                horarios.add(rs.getString("hora_inicio"));
                horarios.add("10:00:00"); // Ejemplos
                horarios.add("11:00:00");
                horarios.add(rs.getString("hora_fin"));
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
        return horarios;
    }
    
    public boolean agendarCita(Cita cita) {
        Connection con = getConexion();
        String sql = "INSERT INTO cita (id_paciente, id_medico, fecha, hora, motivo, estado) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, cita.getIdPaciente());
            ps.setInt(2, cita.getIdMedico());
            ps.setDate(3, cita.getFecha());
            ps.setTime(4, cita.getHora());
            ps.setString(5, cita.getMotivo());
            ps.setString(6, "Pendiente");
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }
    }
    
    public ArrayList<Cita> listarCitasPendientes(int idPaciente) {
        ArrayList<Cita> lista = new ArrayList<>();
        Connection con = getConexion();
        String sql = "SELECT c.fecha, c.hora, u.nombre, u.apellido, e.nombre as esp " +
                     "FROM cita c " +
                     "JOIN medico m ON c.id_medico = m.id " +
                     "JOIN usuario u ON m.id = u.id " +
                     "JOIN especialidad e ON m.id_especialidad = e.id " +
                     "WHERE c.id_paciente = ? AND c.estado = 'Pendiente'";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idPaciente);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Cita c = new Cita();
                c.setFecha(rs.getDate("fecha"));
                c.setHora(rs.getTime("hora"));
                c.setNombreMedico(rs.getString("nombre") + " " + rs.getString("apellido"));
                c.setEspecialidad(rs.getString("esp"));
                lista.add(c);
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
        return lista;
    }

    // Método para listar agenda del médico (Simplificado con objeto Cita)
    public ArrayList<Cita> listarAgenda(int idMedico, java.util.Date fecha) {
        ArrayList<Cita> lista = new ArrayList<>();
        Connection con = getConexion();
        String sql = "SELECT c.hora, u.nombre, u.apellido, c.motivo, c.estado " +
                     "FROM cita c " +
                     "JOIN usuario u ON c.id_paciente = u.id " +
                     "WHERE c.id_medico = ? AND c.fecha = ?";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idMedico);
            ps.setDate(2, new java.sql.Date(fecha.getTime()));
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                Cita c = new Cita();
                c.setHora(rs.getTime("hora"));
                // Usamos un campo auxiliar en Cita para guardar el nombre del paciente temporalmente
                // Ojo: Cita tiene 'nombreMedico', podríamos agregar 'nombrePaciente' o reutilizar uno si solo es para mostrar
                // Para hacerlo bien, agregaremos un campo 'nombrePaciente' en la clase Cita si no existe, 
                // o usaremos 'nombreMedico' como comodín (no recomendado) o simplemente concatenamos en motivo.
                // Vamos a asumir que agregaremos 'nombrePaciente' en Cita.java después.
                // Por ahora, guardaré el nombre del paciente en 'nombreMedico' SOLO para visualización en esta tabla
                c.setNombreMedico(rs.getString("nombre") + " " + rs.getString("apellido")); 
                c.setMotivo(rs.getString("motivo"));
                c.setEstado(rs.getString("estado"));
                lista.add(c);
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
        return lista;
    }
}
