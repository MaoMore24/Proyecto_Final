package Controlador;

import Modelo.ConsultasMedico;
import Modelo.Medico;
import Vista.frmMedicos;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class CtrlMedico implements ActionListener {
    
    private Medico mod;
    private ConsultasMedico modC;
    private frmMedicos frm;
    
    public CtrlMedico(Medico mod, ConsultasMedico modC, frmMedicos frm) {
        this.mod = mod;
        this.modC = modC;
        this.frm = frm;
        
        this.frm.btnGuardar.addActionListener(this);
        this.frm.btnModificar.addActionListener(this);
        this.frm.btnEliminar.addActionListener(this);
        this.frm.btnLimpiar.addActionListener(this);
        this.frm.btnAgregarHorario.addActionListener(this);
        
        // Listener para la tabla (seleccionar fila)
        this.frm.jtMedicos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                seleccionarMedico();
            }
        });
    }
    
    public void iniciar() {
        frm.setTitle("Gestión de Médicos");
        frm.setLocationRelativeTo(null);
        frm.setVisible(true);
        
        modC.cargarEspecialidades(frm.cmbEspecialidad);
        listar();
    }
    
    private void listar() {
        ArrayList<Medico> lista = modC.listarMedicos();
        DefaultTableModel modelo = (DefaultTableModel) frm.jtMedicos.getModel();
        modelo.setRowCount(0); // Limpiar tabla
        
        // Asegúrate de que tu JTable tenga las columnas: ID, Nombre, Apellido, Especialidad, Cédula
        if (modelo.getColumnCount() == 0) {
            modelo.addColumn("ID");
            modelo.addColumn("Nombre");
            modelo.addColumn("Apellido");
            modelo.addColumn("Especialidad");
            modelo.addColumn("Cédula");
        }
        
        for (Medico m : lista) {
            Object[] fila = {
                m.getId(),
                m.getNombre(),
                m.getApellido(),
                m.getNombreEspecialidad(),
                m.getCedula()
            };
            modelo.addRow(fila);
        }
    }
    
    private void seleccionarMedico() {
        int fila = frm.jtMedicos.getSelectedRow();
        if (fila >= 0) {
            frm.txtId.setText(frm.jtMedicos.getValueAt(fila, 0).toString());
            frm.txtNombre.setText(frm.jtMedicos.getValueAt(fila, 1).toString());
            frm.txtApellido.setText(frm.jtMedicos.getValueAt(fila, 2).toString());
            // Nota: Usuario y Password no se muestran en tabla por seguridad
            // Cédula está en la columna 4
            frm.txtCedula.setText(frm.jtMedicos.getValueAt(fila, 4).toString());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == frm.btnGuardar) {
            mod.setUsername(frm.txtUsuario.getText());
            // Usamos getPassword() por seguridad (requiere que el componente sea JPasswordField)
            mod.setPassword(new String(frm.txtPassword.getPassword()));
            mod.setNombre(frm.txtNombre.getText());
            mod.setApellido(frm.txtApellido.getText());
            mod.setEmail(frm.txtEmail.getText());
            mod.setCedula(frm.txtCedula.getText());
            
            // Obtener ID especialidad del string "1 - Cardiologia"
            try {
                String esp = frm.cmbEspecialidad.getSelectedItem().toString();
                int idEsp = Integer.parseInt(esp.split(" - ")[0]);
                mod.setIdEspecialidad(idEsp);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error al leer especialidad. Asegúrese de que haya especialidades cargadas.");
                return;
            }
            
            if (modC.registrar(mod)) {
                JOptionPane.showMessageDialog(null, "Médico Guardado");
                listar();
                limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "Error al guardar");
            }
        }
        
        if (e.getSource() == frm.btnModificar) {
            mod.setId(Integer.parseInt(frm.txtId.getText()));
            mod.setUsername(frm.txtUsuario.getText());
            mod.setNombre(frm.txtNombre.getText());
            mod.setApellido(frm.txtApellido.getText());
            mod.setEmail(frm.txtEmail.getText());
            mod.setCedula(frm.txtCedula.getText());
             // Obtener ID especialidad
            try {
                String esp = frm.cmbEspecialidad.getSelectedItem().toString();
                int idEsp = Integer.parseInt(esp.split(" - ")[0]);
                mod.setIdEspecialidad(idEsp);
            } catch (Exception ex) {
                 JOptionPane.showMessageDialog(null, "Error en especialidad");
                 return;
            }
            
            if (modC.modificar(mod)) {
                JOptionPane.showMessageDialog(null, "Médico Modificado");
                listar();
                limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "Error al modificar");
            }
        }
        
        if (e.getSource() == frm.btnEliminar) {
            if (frm.txtId.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Seleccione un médico para eliminar");
                return;
            }
            mod.setId(Integer.parseInt(frm.txtId.getText()));
            if (modC.eliminar(mod)) {
                JOptionPane.showMessageDialog(null, "Médico Eliminado");
                listar();
                limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "Error al eliminar");
            }
        }
        
        if (e.getSource() == frm.btnLimpiar) {
            limpiar();
        }
        
        if (e.getSource() == frm.btnAgregarHorario) {
            if (frm.txtId.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Seleccione un médico primero (desde la tabla)");
                return;
            }
            int idMedico = Integer.parseInt(frm.txtId.getText());
            String dia = frm.cmbDia.getSelectedItem().toString();
            String inicio = frm.txtHoraInicio.getText();
            String fin = frm.txtHoraFin.getText();
            
            if (modC.agregarHorario(idMedico, dia, inicio, fin)) {
                JOptionPane.showMessageDialog(null, "Horario Agregado");
            } else {
                JOptionPane.showMessageDialog(null, "Error al agregar horario");
            }
        }
    }
    
    public void limpiar() {
        frm.txtId.setText(null);
        frm.txtUsuario.setText(null);
        frm.txtPassword.setText(null);
        frm.txtNombre.setText(null);
        frm.txtApellido.setText(null);
        frm.txtEmail.setText(null);
        frm.txtCedula.setText(null);
        frm.txtHoraInicio.setText(null);
        frm.txtHoraFin.setText(null);
    }
}
