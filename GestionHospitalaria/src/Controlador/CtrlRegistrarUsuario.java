package Controlador;

import Modelo.ConsultasUsuario;
import Modelo.Usuario;
import Vista.frmAgregarUsuarios;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class CtrlRegistrarUsuario implements ActionListener {
    
    private Usuario mod;
    private ConsultasUsuario modC;
    private frmAgregarUsuarios frm;
    
    public CtrlRegistrarUsuario(Usuario mod, ConsultasUsuario modC, frmAgregarUsuarios frm) {
        this.mod = mod;
        this.modC = modC;
        this.frm = frm;
        
        // Registrar listeners para los botones
        this.frm.btnRegistrar.addActionListener(this);
        this.frm.btnCancelar.addActionListener(this);
        
        // Cargar los roles en el ComboBox
        cargarRoles();
    }
    
    public void iniciar() {
        frm.setTitle("Registrar Nuevo Usuario");
        frm.setLocationRelativeTo(null);
        frm.setVisible(true);
    }
    
    private void cargarRoles() {
        // Cargar roles desde la base de datos (excepto Pacientes, id 3, si así se desea filtrar)
        modC.cargarRoles(frm.cmbTipoUsuario);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == frm.btnRegistrar) {
            // Validaciones
            if (frm.txtUsuario.getText().trim().isEmpty() || 
                new String(frm.txtPassword.getPassword()).trim().isEmpty() ||
                frm.txtNombre.getText().trim().isEmpty() ||
                frm.txtApellido.getText().trim().isEmpty()) {
                
                JOptionPane.showMessageDialog(null, "Por favor llene todos los campos obligatorios:\n- Usuario\n- Contraseña\n- Nombre\n- Apellido");
                return;
            }
            
            if (frm.cmbTipoUsuario.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(null, "Por favor seleccione un tipo de usuario");
                return;
            }
            
            // Obtener los datos del formulario
            mod.setUsername(frm.txtUsuario.getText().trim());
            mod.setPassword(new String(frm.txtPassword.getPassword()));
            mod.setNombre(frm.txtNombre.getText().trim());
            mod.setApellido(frm.txtApellido.getText().trim());
            mod.setEmail(frm.txtEmail.getText().trim());
            
            // Obtener el id_rol del ComboBox
            String rolSeleccionado = frm.cmbTipoUsuario.getSelectedItem().toString();
            int idRol = Integer.parseInt(rolSeleccionado.split(" - ")[0]); // Extraer el ID (ej: "2 - Medico" -> 2)
            mod.setId_rol(idRol);
            
            // Registrar en la base de datos
            if (modC.registrar(mod)) {
                JOptionPane.showMessageDialog(null, "Usuario registrado exitosamente");
                limpiarFormulario();
                frm.dispose(); // Cerrar el formulario después de registrar
            } else {
                JOptionPane.showMessageDialog(null, "Error al registrar el usuario.\nVerifique que el nombre de usuario no esté duplicado.");
            }
        }
        
        if (e.getSource() == frm.btnCancelar) {
            int confirmar = JOptionPane.showConfirmDialog(null, 
                "¿Está seguro de que desea cancelar?", 
                "Confirmar", 
                JOptionPane.YES_NO_OPTION);
            
            if (confirmar == JOptionPane.YES_OPTION) {
                frm.dispose();
            }
        }
    }
    
    private void limpiarFormulario() {
        frm.txtUsuario.setText("");
        frm.txtPassword.setText("");
        frm.txtNombre.setText("");
        frm.txtApellido.setText("");
        frm.txtEmail.setText("");
        frm.cmbTipoUsuario.setSelectedIndex(0);
    }
}
