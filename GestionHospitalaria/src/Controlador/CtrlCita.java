package Controlador;

import Modelo.Cita;
import Modelo.ConsultasCita;
import Vista.frmCita;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class CtrlCita implements ActionListener {

    private final Cita modelo;
    private final ConsultasCita consultas;
    private final frmCita vista;

    public CtrlCita(Cita modelo, ConsultasCita consultas, frmCita vista) {
        this.modelo = modelo;
        this.consultas = consultas;
        this.vista = vista;
        this.vista.btnGuardar.addActionListener(this);
        this.vista.btnModificar.addActionListener(this);
        this.vista.btnEliminar.addActionListener(this);
        this.vista.btnLimpiar.addActionListener(this);
        this.vista.btnBuscar.addActionListener(this);
    }

    public void iniciar() {
        vista.setTitle("Gestión de Citas");
        vista.setLocationRelativeTo(null);
        // vista.txtId.setVisible(false); // Dejamos visible para buscar por ID
    }

    public void limpiar() {
        vista.txtId.setText("");
        vista.txtIdPaciente.setText("");
        vista.txtIdMedico.setText("");
        vista.txtFechaHora.setText("");
        vista.txtMotivo.setText("");
        vista.cbxEstado.setSelectedIndex(0);
        vista.txtIdPaciente.requestFocus();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Botón Guardar
        if (e.getSource() == vista.btnGuardar) {
            try {
                modelo.setIdPaciente(Integer.parseInt(vista.txtIdPaciente.getText()));
                modelo.setIdMedico(Integer.parseInt(vista.txtIdMedico.getText()));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Los IDs deben ser numéricos");
                return;
            }
            
            modelo.setFechaHora(vista.txtFechaHora.getText());
            modelo.setMotivo(vista.txtMotivo.getText());
            modelo.setEstado(vista.cbxEstado.getSelectedItem().toString());

            if (consultas.registrar(modelo)) {
                JOptionPane.showMessageDialog(null, "Cita agendada");
                limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "Error al agendar cita");
                limpiar();
            }
        }

        // Botón Modificar
        if (e.getSource() == vista.btnModificar) {
            try {
                modelo.setId(Integer.parseInt(vista.txtId.getText()));
                modelo.setIdPaciente(Integer.parseInt(vista.txtIdPaciente.getText()));
                modelo.setIdMedico(Integer.parseInt(vista.txtIdMedico.getText()));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Los IDs deben ser numéricos");
                return;
            }

            modelo.setFechaHora(vista.txtFechaHora.getText());
            modelo.setMotivo(vista.txtMotivo.getText());
            modelo.setEstado(vista.cbxEstado.getSelectedItem().toString());

            if (consultas.modificar(modelo)) {
                JOptionPane.showMessageDialog(null, "Cita modificada");
                limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "Error al modificar cita");
                limpiar();
            }
        }

        // Botón Eliminar
        if (e.getSource() == vista.btnEliminar) {
            try {
                modelo.setId(Integer.parseInt(vista.txtId.getText()));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "El ID debe ser numérico");
                return;
            }

            if (consultas.eliminar(modelo)) {
                JOptionPane.showMessageDialog(null, "Cita eliminada");
                limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "Error al eliminar cita");
                limpiar();
            }
        }

        // Botón Buscar
        if (e.getSource() == vista.btnBuscar) {
            try {
                modelo.setId(Integer.parseInt(vista.txtId.getText()));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "El ID debe ser numérico");
                return;
            }

            if (consultas.buscar(modelo)) {
                vista.txtId.setText(String.valueOf(modelo.getId()));
                vista.txtIdPaciente.setText(String.valueOf(modelo.getIdPaciente()));
                vista.txtIdMedico.setText(String.valueOf(modelo.getIdMedico()));
                vista.txtFechaHora.setText(modelo.getFechaHora());
                vista.txtMotivo.setText(modelo.getMotivo());
                vista.cbxEstado.setSelectedItem(modelo.getEstado());
                JOptionPane.showMessageDialog(null, "Cita encontrada");
            } else {
                JOptionPane.showMessageDialog(null, "Error al buscar cita");
                limpiar();
            }
        }

        // Botón Limpiar
        if (e.getSource() == vista.btnLimpiar) {
            limpiar();
        }
    }
}
