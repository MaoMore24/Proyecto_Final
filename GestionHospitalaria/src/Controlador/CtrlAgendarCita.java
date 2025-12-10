package Controlador;

import Modelo.Cita;
import Modelo.ConsultasCita;
import Modelo.Usuario;
import Vista.frmAgendarCitas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class CtrlAgendarCita implements ActionListener {
    
    private Cita mod;
    private ConsultasCita modC;
    private frmAgendarCitas frm;
    private Usuario usuarioActual; // Necesitamos saber quién es el paciente
    
    public CtrlAgendarCita(Cita mod, ConsultasCita modC, frmAgendarCitas frm, Usuario usuarioActual) {
        this.mod = mod;
        this.modC = modC;
        this.frm = frm;
        this.usuarioActual = usuarioActual;
        
        this.frm.btnAgendar.addActionListener(this);
        this.frm.btnCancelar.addActionListener(this);
        this.frm.cmbEspecialidad.addActionListener(this);
        
        // Cargar especialidades al inicio
        modC.cargarEspecialidades(frm.cmbEspecialidad);
    }
    
    public void iniciar() {
        frm.setTitle("Agendar Cita Médica");
        frm.setLocationRelativeTo(null);
        frm.setVisible(true);
        
    }
    
    

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == frm.cmbEspecialidad) {
            // Cargar médicos cuando cambia la especialidad
            if (frm.cmbEspecialidad.getItemCount() > 0 && frm.cmbEspecialidad.getSelectedItem() != null) {
                String esp = frm.cmbEspecialidad.getSelectedItem().toString();
                int idEsp = Integer.parseInt(esp.split(" - ")[0]);
                modC.cargarMedicosPorEspecialidad(idEsp, frm.cmbMedico);
            }
        }
        
        if (e.getSource() == frm.btnAgendar) {
            // Validaciones
            if (frm.cmbMedico.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(null, "Seleccione un médico");
                return;
            }
            
            // Obtener datos
            String med = frm.cmbMedico.getSelectedItem().toString();
            int idMedico = Integer.parseInt(med.split(" - ")[0]);
            
            // Fecha
            java.util.Date fechaUtil = null;
            try {
                 
                 String fechaStr = frm.txtFecha.getText();
                 fechaUtil = new java.text.SimpleDateFormat("dd-MM-yyyy").parse(fechaStr);
                 
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Fecha inválida");
                return;
            }
            
            // Hora desde la lista (JList btnHora)
            String horaSeleccionada = frm.btnHora.getSelectedValue();
            
            if (horaSeleccionada == null) {
                JOptionPane.showMessageDialog(null, "Por favor seleccione una hora de la lista.");
                return;
            }
            
            try {
                // Formato simple directo (HH:mm:ss) como en el ejemplo de la profesora
                mod.setHora(java.sql.Time.valueOf(horaSeleccionada));
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(null, "Error en formato de hora: " + horaSeleccionada);
                return;
            }
            
            mod.setIdPaciente(usuarioActual.getId());
            mod.setIdMedico(idMedico); // FIX: Establecer el médico seleccionado
            mod.setFecha(new java.sql.Date(fechaUtil.getTime())); // FIX: Establecer la fecha
            mod.setMotivo(frm.txtMotivo.getText());
            
            if (modC.agendarCita(mod)) {
                JOptionPane.showMessageDialog(null, "Cita Agendada");
                // listarCitas(); // Eliminado
                frm.dispose(); // Opcional: cerrar ventana al agendar
            } else {
                JOptionPane.showMessageDialog(null, "Error al agendar");
            }
        }
        
        if (e.getSource() == frm.btnCancelar) {
            frm.dispose();
        }
    }
}
