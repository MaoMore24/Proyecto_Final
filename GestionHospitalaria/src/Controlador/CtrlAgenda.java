package Controlador;

import Modelo.Cita;
import Modelo.ConsultasCita;
import Modelo.Usuario;
import Vista.frmAgenda;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class CtrlAgenda implements ActionListener {

    private final ConsultasCita consultas;
    private final frmAgenda vista;
    private final Usuario medico;

    public CtrlAgenda(ConsultasCita consultas, frmAgenda vista, Usuario medico) {
        this.consultas = consultas;
        this.vista = vista;
        this.medico = medico;
        this.vista.btnFiltrar.addActionListener(this);
    }

    public void iniciar() {
        vista.setTitle("Agenda - Dr. " + medico.getNombre() + " " + medico.getApellido());
        vista.setLocationRelativeTo(null);
        
        // Set current date by default (dd-MM-yyyy para ser consistente con el resto)
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        vista.txtFecha.setText(sdf.format(new Date()));
        
        cargarAgenda();
    }
    
    private void cargarAgenda() {
        String fechaStr = vista.txtFecha.getText();
        if (fechaStr.isEmpty()) return;
        
        Date fechaDate = null;
        try {
            fechaDate = new SimpleDateFormat("dd-MM-yyyy").parse(fechaStr);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Formato de fecha inválido. Use dd-MM-yyyy");
            return;
        }
        
        DefaultTableModel model = (DefaultTableModel) vista.tblAgenda.getModel();
        model.setRowCount(0); // Clear table
        
        // Asegurar columnas
        if (model.getColumnCount() == 0) {
            model.addColumn("Hora");
            model.addColumn("Paciente");
            model.addColumn("Motivo");
            model.addColumn("Estado");
        }
        
        ArrayList<Cita> citas = consultas.listarAgenda(medico.getId(), fechaDate);
        
        for (Cita c : citas) {
            model.addRow(new Object[]{
                c.getHora(),
                c.getNombreMedico(), // Aquí guardamos el nombre del paciente temporalmente en el modelo
                c.getMotivo(),
                c.getEstado()
            });
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnFiltrar) {
            cargarAgenda();
        }
    }
}
