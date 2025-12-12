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
        
        // Menú contextual para cambiar estado (Clic derecho)
        javax.swing.JPopupMenu popup = new javax.swing.JPopupMenu();
        javax.swing.JMenuItem itemRealizada = new javax.swing.JMenuItem("Marcar como Realizada");
        javax.swing.JMenuItem itemCancelada = new javax.swing.JMenuItem("Marcar como Cancelada");
        javax.swing.JMenuItem itemAusente = new javax.swing.JMenuItem("Marcar como Ausente");
        
        popup.add(itemRealizada);
        popup.add(itemCancelada);
        popup.add(itemAusente);
        
        // Listeners para el menú
        itemRealizada.addActionListener(e -> cambiarEstado("Realizada"));
        itemCancelada.addActionListener(e -> cambiarEstado("Cancelada"));
        itemAusente.addActionListener(e -> cambiarEstado("Ausente"));
        
        vista.tblAgenda.setComponentPopupMenu(popup);
    }
    
    private void cambiarEstado(String nuevoEstado) {
        int fila = vista.tblAgenda.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione una cita.");
            return;
        }
        
        // Asumimos que la columna 0 es el ID (agregada en cargarAgenda)
        int idCita = Integer.parseInt(vista.tblAgenda.getValueAt(fila, 0).toString());
        
        if (consultas.actualizarEstadoCita(idCita, nuevoEstado)) {
            JOptionPane.showMessageDialog(null, "Estado actualizado a: " + nuevoEstado);
            cargarAgenda(); // Recargar tabla
        } else {
            JOptionPane.showMessageDialog(null, "Error al actualizar estado.");
        }
    }

    public void iniciar() {
        vista.setTitle("Agenda - Dr. " + medico.getNombre() + " " + medico.getApellido());
        vista.setLocationRelativeTo(null);
        
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
        
        // Definir columnas siempre para asegurar concordancia con los datos
        String[] columnas = {"ID", "Hora", "Paciente", "Motivo", "Estado"};
        model.setColumnIdentifiers(columnas);
            
        // Ocultar ID visualmente pero mantenerlo en el modelo
        vista.tblAgenda.getColumnModel().getColumn(0).setMinWidth(0);
        vista.tblAgenda.getColumnModel().getColumn(0).setMaxWidth(0);
        vista.tblAgenda.getColumnModel().getColumn(0).setWidth(0);
        
        ArrayList<Cita> citas = consultas.listarAgenda(medico.getId(), fechaDate);
        
        for (Cita c : citas) {
            model.addRow(new Object[]{
                c.getId(),
                c.getHora(),
                c.getNombreMedico(), 
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
