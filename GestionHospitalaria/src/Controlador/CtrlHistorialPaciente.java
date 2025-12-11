package Controlador;

import Modelo.ConsultasExpediente;
import Modelo.Diagnostico;
import Modelo.Examen;
import Modelo.Receta;
import Vista.frmHistorialPaciente;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class CtrlHistorialPaciente implements ActionListener {

    private final frmHistorialPaciente vista;
    private final ConsultasExpediente consultas;
    
    private int idPacienteSeleccionado = -1;
    private int idExpedienteSeleccionado = -1;

    public CtrlHistorialPaciente(frmHistorialPaciente vista, ConsultasExpediente consultas) {
        this.vista = vista;
        this.consultas = consultas;
        
        // Listeners
        this.vista.btnBuscarPaciente.addActionListener(this);
        this.vista.cmbPacientes.addActionListener(this);
        this.vista.btnCerrar.addActionListener(this);
    }
    
    public void iniciar() {
        vista.setVisible(true);
        // Limpiar campos
        limpiarTablas();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnBuscarPaciente) {
            buscarPacientes();
        } 
        else if (e.getSource() == vista.cmbPacientes) {
            seleccionarPaciente();
        }
        else if (e.getSource() == vista.btnCerrar) {
            vista.dispose();
        }
    }
    
    private void buscarPacientes() {
        String criterio = vista.txtBuscarPaciente.getText().trim();
        if(criterio.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese nombre a buscar");
            return;
        }
        
        ArrayList<String> pacientes = consultas.buscarPacientes(criterio);
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        for(String p : pacientes) {
            model.addElement(p);
        }
        vista.cmbPacientes.setModel(model);
        
        if(pacientes.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No se encontraron resultados");
        }
    }
    
    private void seleccionarPaciente() {
        if(vista.cmbPacientes.getSelectedItem() == null) return;
        
        try {
            String seleccion = vista.cmbPacientes.getSelectedItem().toString();
            String[] partes = seleccion.split(" - ");
            idPacienteSeleccionado = Integer.parseInt(partes[0]);
            vista.lblPacienteSeleccionado.setText("Paciente: " + partes[1]);
            
            // Buscar Expediente
            idExpedienteSeleccionado = consultas.obtenerIdExpediente(idPacienteSeleccionado);
            
            if(idExpedienteSeleccionado != -1) {
                cargarHistorial(idExpedienteSeleccionado);
            } else {
                JOptionPane.showMessageDialog(null, "El paciente no tiene expediente registrado.");
                limpiarTablas();
            }
            
        } catch (Exception e) {
            System.err.println("Error seleccionando paciente: " + e);
        }
    }
    
    private void cargarHistorial(int idExp) {
        // Cargar Diagnósticos
        ArrayList<Diagnostico> diags = consultas.listarDiagnosticos(idExp);
        DefaultTableModel modelDiag = (DefaultTableModel) vista.tblDiagnosticos.getModel();
        modelDiag.setRowCount(0);
        for(Diagnostico d : diags) {
            modelDiag.addRow(new Object[]{ d.getFecha(), d.getPadecimientos(), d.getDiagnostico(), d.getNotas() });
        }
        
        // Cargar Recetas
        ArrayList<Receta> recs = consultas.listarRecetas(idExp);
        DefaultTableModel modelRec = (DefaultTableModel) vista.tblRecetas.getModel();
        modelRec.setRowCount(0);
        for(Receta r : recs) {
            modelRec.addRow(new Object[]{ r.getFecha(), r.getMedicamentos(), r.getInstrucciones() });
        }
        
        // Cargar Exámenes
        ArrayList<Examen> exams = consultas.listarExamenes(idExp);
        DefaultTableModel modelEx = (DefaultTableModel) vista.tblExamenes.getModel();
        modelEx.setRowCount(0);
        for(Examen x : exams) {
            modelEx.addRow(new Object[]{ x.getFecha(), x.getTipoExamen(), x.getResultados() });
        }
    }
    
    private void limpiarTablas() {
        ((DefaultTableModel) vista.tblDiagnosticos.getModel()).setRowCount(0);
        ((DefaultTableModel) vista.tblRecetas.getModel()).setRowCount(0);
        ((DefaultTableModel) vista.tblExamenes.getModel()).setRowCount(0);
    }
    
}
