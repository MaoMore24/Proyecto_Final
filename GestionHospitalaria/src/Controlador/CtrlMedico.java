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
    private int idMedicoSeleccionado = -1; // ID temporal para manejar ediciones sin txtId
    
    public CtrlMedico(Medico mod, ConsultasMedico modC, frmMedicos frm) {
        this.mod = mod;
        this.modC = modC;
        this.frm = frm;
        
        this.frm.btnGuardar.addActionListener(this);
        this.frm.btnModificar.addActionListener(this);
        this.frm.btnEliminar.addActionListener(this);
        this.frm.btnLimpiar.addActionListener(this);
        this.frm.btnAgregarHorario.addActionListener(this);
        this.frm.btnBuscar.addActionListener(this);
    }
    
    public void iniciar() {
        frm.setTitle("Gestión de Médicos");
        frm.setLocationRelativeTo(null);
        frm.setVisible(true);
        
        modC.cargarEspecialidades(frm.cmbEspecialidad);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        // --- BUSCAR MÉDICO ---
        if (e.getSource() == frm.btnBuscar) {
            String cedula = frm.txtCedula.getText().trim();
            if (cedula.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Ingrese el Código Profesional (Cédula) para buscar.");
                return;
            }
            
            Medico encontrado = modC.buscarPorCedula(cedula);
            if (encontrado != null) {
                idMedicoSeleccionado = encontrado.getId();
                frm.txtUsuario.setText(encontrado.getUsername());
                frm.txtPassword.setText(encontrado.getPassword()); // Cuidado: passwords en texto plano
                frm.txtNombre.setText(encontrado.getNombre());
                frm.txtApellido.setText(encontrado.getApellido());
                frm.txtEmail.setText(encontrado.getEmail());
                frm.txtCedula.setText(encontrado.getCedula());
                
                // Seleccionar especialidad en el combo
                // Asumimos que el combo tiene items "ID - Nombre"
                for (int i = 0; i < frm.cmbEspecialidad.getItemCount(); i++) {
                    String item = frm.cmbEspecialidad.getItemAt(i);
                    if (item.startsWith(encontrado.getIdEspecialidad() + " -")) {
                        frm.cmbEspecialidad.setSelectedIndex(i);
                        break;
                    }
                }
                JOptionPane.showMessageDialog(null, "Médico encontrado.");
                listarHorarios(); // Cargar horarios
            } else {
                JOptionPane.showMessageDialog(null, "Médico no encontrado.");
                limpiar();
            }
        }
        
        // --- GUARDAR ---
        if (e.getSource() == frm.btnGuardar) {
            mod.setUsername(frm.txtUsuario.getText());
            mod.setPassword(new String(frm.txtPassword.getPassword()));
            mod.setNombre(frm.txtNombre.getText());
            mod.setApellido(frm.txtApellido.getText());
            mod.setEmail(frm.txtEmail.getText());
            mod.setCedula(frm.txtCedula.getText());
            
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
                limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "Error al guardar");
            }
        }
        
        // --- MODIFICAR ---
        if (e.getSource() == frm.btnModificar) {
            if (idMedicoSeleccionado == -1) {
                JOptionPane.showMessageDialog(null, "Primero busque un médico para modificar.");
                return;
            }
            
            mod.setId(idMedicoSeleccionado);
            mod.setUsername(frm.txtUsuario.getText());
            mod.setNombre(frm.txtNombre.getText()); // Posiblemente falta setPassword si se permite cambiar
            mod.setApellido(frm.txtApellido.getText());
            mod.setEmail(frm.txtEmail.getText());
            mod.setCedula(frm.txtCedula.getText());
            // Si desea permitir cambio de password:
            // mod.setPassword(new String(frm.txtPassword.getPassword()));
            
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
                limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "Error al modificar");
            }
        }
        
        // --- ELIMINAR ---
        if (e.getSource() == frm.btnEliminar) {
            if (idMedicoSeleccionado == -1) {
                JOptionPane.showMessageDialog(null, "Primero busque un médico para eliminar");
                return;
            }
            mod.setId(idMedicoSeleccionado);
            
            int confirm = JOptionPane.showConfirmDialog(null, "¿Seguro que desea eliminar a este médico?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (modC.eliminar(mod)) {
                    JOptionPane.showMessageDialog(null, "Médico Eliminado");
                    limpiar();
                } else {
                    JOptionPane.showMessageDialog(null, "Error al eliminar");
                }
            }
        }
        
        // --- LIMPIAR ---
        if (e.getSource() == frm.btnLimpiar) {
            limpiar();
        }
        
        // --- AGREGAR HORARIO ---
        if (e.getSource() == frm.btnAgregarHorario) {
            if (idMedicoSeleccionado == -1) {
                JOptionPane.showMessageDialog(null, "Busque y seleccione un médico primero.");
                return;
            }
            String dia = frm.cmbDia.getSelectedItem().toString();
            String inicio = frm.txtHoraInicio.getText();
            String fin = frm.txtHoraFin.getText();
            
            if (inicio.isEmpty() || fin.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Ingrese hora inicio y fin.");
                return;
            }
            
            if (modC.agregarHorario(idMedicoSeleccionado, dia, inicio, fin)) {
                JOptionPane.showMessageDialog(null, "Horario Agregado");
                listarHorarios(); // Refrescar tabla
                frm.txtHoraInicio.setText("");
                frm.txtHoraFin.setText("");
            } else {
                JOptionPane.showMessageDialog(null, "Error al agregar horario");
            }
        }
    }
    
    // Método para llenar la tabla de horarios
    private void listarHorarios() {
        if (idMedicoSeleccionado == -1) return;
        
        ArrayList<String[]> lista = modC.listarHorarios(idMedicoSeleccionado);
        DefaultTableModel modelo = (DefaultTableModel) frm.jtHorarios.getModel();
        modelo.setRowCount(0);
        
        // Configurar columnas si no existen
        if (modelo.getColumnCount() == 0) {
            modelo.addColumn("ID");
            modelo.addColumn("Día");
            modelo.addColumn("Inicio");
            modelo.addColumn("Fin");
        }
        
        for (String[] fila : lista) {
            modelo.addRow(fila);
        }
    }
    
    public void limpiar() {
        idMedicoSeleccionado = -1;
        // frm.txtId.setText(null); // Eliminado
        frm.txtUsuario.setText(null);
        frm.txtPassword.setText(null);
        frm.txtNombre.setText(null);
        frm.txtApellido.setText(null);
        frm.txtEmail.setText(null);
        frm.txtCedula.setText(null);
        frm.txtHoraInicio.setText(null);
        frm.txtHoraFin.setText(null);
        
        // Limpiar tabla horarios
        try {
            DefaultTableModel modelo = (DefaultTableModel) frm.jtHorarios.getModel();
            modelo.setRowCount(0);
        } catch (Exception e) {}
    }
}
