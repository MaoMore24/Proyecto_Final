package Controlador;

import Modelo.ConsultasExpediente;
import Modelo.Diagnostico;
import Modelo.Examen;
import Modelo.Receta;
import Modelo.Usuario;
import Vista.frmExpediente;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

public class CtrlExpediente implements ActionListener {

    private final ConsultasExpediente consultas;
    private final frmExpediente vista;
    private final Usuario medicoActual;
    private int idPacienteSeleccionado = -1;
    private int idExpedienteSeleccionado = -1;

    public CtrlExpediente(ConsultasExpediente consultas, frmExpediente vista, Usuario medicoActual) {
        this.consultas = consultas;
        this.vista = vista;
        this.medicoActual = medicoActual;

        this.vista.btnBuscarPaciente.addActionListener(this);
        this.vista.btnGuardar.addActionListener(this);
        this.vista.cmbPacientes.addActionListener(this);
    }

    public void iniciar() {
        vista.setTitle("Expediente Médico - Dr. " + medicoActual.getNombre() + " " + medicoActual.getApellido());
        vista.setLocationRelativeTo(null);
        vista.cmbPacientes.removeAllItems();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnBuscarPaciente) {
            buscarPacientes();
        } else if (e.getSource() == vista.cmbPacientes) {
            seleccionarPaciente();
        } else if (e.getSource() == vista.btnGuardar) {
            guardarTodo();
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
            
            // Obtener ID Expediente
            idExpedienteSeleccionado = consultas.obtenerIdExpediente(idPacienteSeleccionado);
            if (idExpedienteSeleccionado == -1) {
                // Intentar crear expediente automáticamente
                int nuevoId = consultas.crearExpediente(idPacienteSeleccionado);
                if (nuevoId != -1) {
                    idExpedienteSeleccionado = nuevoId;
                    JOptionPane.showMessageDialog(null, "Se ha inicializado un nuevo expediente para el paciente.");
                } else {
                    JOptionPane.showMessageDialog(null, "Error crítico: No se pudo obtener ni crear el expediente del paciente.");
                }
            }
            
        } catch (Exception ex) {
            System.err.println("Error al parsear paciente: " + ex.getMessage());
        }
    }

    private void guardarTodo() {
        if (idExpedienteSeleccionado == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione un paciente válido primero.");
            return;
        }

        boolean guardadoAlgo = false;

        // 1. Guardar Diagnóstico (Historia Clínica)
        // Se guarda si hay al menos un dato en cualquiera de los campos clínicos
        if (!vista.txtDiagnostico.getText().trim().isEmpty() || 
            !vista.txtPadecimientos.getText().trim().isEmpty() || 
            !vista.txtExamenFisico.getText().trim().isEmpty()) {
            
            Diagnostico diag = new Diagnostico();
            diag.setIdExpediente(idExpedienteSeleccionado);
            diag.setIdMedico(medicoActual.getId());
            diag.setIdPaciente(idPacienteSeleccionado); // Nuevo campo requerido
            diag.setPadecimientos(vista.txtPadecimientos.getText());
            diag.setDiagnostico(vista.txtDiagnostico.getText());
            diag.setNotas(vista.txtExamenFisico.getText()); // Usamos Examen Físico como notas
            
            if (consultas.registrarDiagnostico(diag)) {
                guardadoAlgo = true;
            }
        }

        // 2. Guardar Receta
        if (!vista.txtMedicamentos.getText().trim().isEmpty()) {
            Receta rec = new Receta();
            rec.setIdExpediente(idExpedienteSeleccionado);
            rec.setIdMedico(medicoActual.getId());
            rec.setMedicamentos(vista.txtMedicamentos.getText());
            rec.setInstrucciones(vista.txtInstrucciones.getText());
            
            if (consultas.registrarReceta(rec)) {
                guardadoAlgo = true;
            }
        }

        // 3. Guardar Examen
        if (!vista.txtTipoExamen.getText().trim().isEmpty()) {
            Examen ex = new Examen();
            ex.setIdExpediente(idExpedienteSeleccionado);
            ex.setIdMedico(medicoActual.getId());
            ex.setTipoExamen(vista.txtTipoExamen.getText());
            ex.setResultados(vista.txtResultados.getText());
            
            if (consultas.registrarExamen(ex)) {
                guardadoAlgo = true;
            }
        }

        if (guardadoAlgo) {
            JOptionPane.showMessageDialog(null, "Información guardada correctamente.");
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(null, "No hay información nueva para guardar.");
        }
    }
    
    private void limpiarCampos() {
        vista.txtPadecimientos.setText("");
        vista.txtDiagnostico.setText("");
        vista.txtExamenFisico.setText("");
        vista.txtMedicamentos.setText("");
        vista.txtInstrucciones.setText("");
        vista.txtTipoExamen.setText("");
        vista.txtResultados.setText("");
    }
}
