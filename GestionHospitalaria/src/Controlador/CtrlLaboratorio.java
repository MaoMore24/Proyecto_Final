package Controlador;

import Modelo.ConsultasLaboratorio;
import Modelo.ResultadoLaboratorio;
import Vista.frmLaboratorio;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 * Controlador para el formulario de laboratorio
 * @author mms24
 */
public class CtrlLaboratorio implements ActionListener {

    private final ResultadoLaboratorio modelo;
    private final ConsultasLaboratorio consultas;
    private final frmLaboratorio vista;
    private int idPacienteSeleccionado = -1;
    private int idExpedienteSeleccionado = -1;
    private int idTecnico;

    public CtrlLaboratorio(ResultadoLaboratorio modelo, ConsultasLaboratorio consultas, frmLaboratorio vista, int idTecnico) {
        this.modelo = modelo;
        this.consultas = consultas;
        this.vista = vista;
        this.idTecnico = idTecnico;
        this.vista.btnBuscarPaciente.addActionListener(this);
        this.vista.btnGuardar.addActionListener(this);
        this.vista.cmbPacientes.addActionListener(this);
    }

    public void iniciar() {
        vista.setTitle("Laboratorio");
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
            guardarResultadoLaboratorio();
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

    private void guardarResultadoLaboratorio() {
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
        modelo.setIdTecnico(idTecnico);
        modelo.setExpedienteLaboratorio(vista.txtPadecimientos.getText());
        modelo.setProcedimientos(vista.txtExamenFisico.getText());
        modelo.setResultados(vista.txtMedicamentos.getText());

        if (consultas.registrar(modelo)) {
            JOptionPane.showMessageDialog(null, "Registro guardado");
            limpiar();
        } else {
            JOptionPane.showMessageDialog(null, "Error al guardar registro");
            limpiar();
        }
    }
}
