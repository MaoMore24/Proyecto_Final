package Controlador;

import Modelo.ConsultasCita;
import Modelo.Usuario;
import Vista.frmAgenda;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
        
        // Set current date by default
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        vista.txtFecha.setText(sdf.format(new Date()));
        
        cargarAgenda();
    }
    
    private void cargarAgenda() {
        String fecha = vista.txtFecha.getText();
        if (fecha.isEmpty()) return;
        
        DefaultTableModel model = (DefaultTableModel) vista.tblAgenda.getModel();
        model.setRowCount(0); // Clear table
        
        List<Map<String, String>> citas = consultas.listarAgenda(medico.getId(), fecha);
        
        for (Map<String, String> cita : citas) {
            model.addRow(new Object[]{
                cita.get("hora"),
                cita.get("paciente"),
                cita.get("motivo"),
                cita.get("estado")
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
