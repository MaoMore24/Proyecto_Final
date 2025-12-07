package Controlador;

import Modelo.ConsultasEnfermeria;
import Modelo.Enfermeria;
import Vista.frmEnfermeria;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 * Controlador para el formulario de enfermería
 * @author mms24
 */
public class CtrlEnfermeria implements ActionListener {

    private final Enfermeria modelo;
    private final ConsultasEnfermeria consultas;
    private final frmEnfermeria vista;
    private int idPacienteSeleccionado = -1;
    private int idExpedienteSeleccionado = -1;
    private int idEnfermero;

    public CtrlEnfermeria(Enfermeria modelo, ConsultasEnfermeria consultas, frmEnfermeria vista, int idEnfermero) {
        this.modelo = modelo;
        this.consultas = consultas;
        this.vista = vista;
        this.idEnfermero = idEnfermero;
        this.vista.btnBuscarPaciente.addActionListener(this);
        this.vista.btnGuardar.addActionListener(this);
        this.vista.cmbPacientes.addActionListener(this);
    }

    public void iniciar() {
        vista.setTitle("Enfermería");
        vista.setLocationRelativeTo(null);
        vista.cmbPacientes.removeAllItems();
    }

    public void limpiar() {
        vista.txtPadecimientos.setText("");
        vista.txtExamenFisico.setText("");
        vista.txtMedicamentos.setText("");
        vista.txtBuscarPaciente.setText("");
        vista.cmbPacientes.removeAllItems();
        vista.lblPacienteSeleccionado.setText("Paciente: ");
        idPacienteSeleccionado = -1;
        idExpedienteSeleccionado = -1;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnBuscarPaciente) {
            buscarPacientes();
        } else if (e.getSource() == vista.cmbPacientes) {
            seleccionarPaciente();
        } else if (e.getSource() == vista.btnGuardar) {
            guardarRegistroEnfermeria();
        }
    }

    private void buscarPacientes() {
        String criterio = vista.txtBuscarPaciente.getText().trim();
        if (criterio.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese un nombre para buscar.");
            return;
        }

        ArrayList<String> pacientes = consultas.buscarPacientes(criterio);
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        for (String p : pacientes) {
            model.addElement(p);
        }
        vista.cmbPacientes.setModel(model);
        
        if (pacientes.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No se encontraron pacientes.");
        }
    }

    private void seleccionarPaciente() {
        if (vista.cmbPacientes.getSelectedItem() == null) return;
        
        String seleccion = vista.cmbPacientes.getSelectedItem().toString();
        try {
            String[] partes = seleccion.split(" - ");
            idPacienteSeleccionado = Integer.parseInt(partes[0]);
            vista.lblPacienteSeleccionado.setText("Paciente: " + partes[1]);
            
            idExpedienteSeleccionado = consultas.obtenerIdExpediente(idPacienteSeleccionado);
            if (idExpedienteSeleccionado == -1) {
                JOptionPane.showMessageDialog(null, "Error: El paciente no tiene un expediente creado.");
            }
            
        } catch (Exception ex) {
            System.err.println("Error al parsear paciente: " + ex.getMessage());
        }
    }

    private void guardarRegistroEnfermeria() {
        if (idExpedienteSeleccionado == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione un paciente válido primero.");
            return;
        }

        if (vista.txtPadecimientos.getText().trim().isEmpty() &&
            vista.txtExamenFisico.getText().trim().isEmpty() &&
            vista.txtMedicamentos.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar al menos un dato.");
            return;
        }

        modelo.setIdExpediente(idExpedienteSeleccionado);
        modelo.setIdEnfermero(idEnfermero);
        modelo.setExpedienteEnfermeria(vista.txtPadecimientos.getText());
        modelo.setProcedimientos(vista.txtExamenFisico.getText());
        modelo.setMedicamentos(vista.txtMedicamentos.getText());

        if (consultas.registrar(modelo)) {
            JOptionPane.showMessageDialog(null, "Registro guardado");
            limpiar();
        } else {
            JOptionPane.showMessageDialog(null, "Error al guardar registro");
            limpiar();
        }
    }
}
