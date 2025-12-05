package Controlador;

import Modelo.Usuario;
import Vista.frmLogin;
import Vista.frmSistema;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class CtrlSistema implements ActionListener {
    private frmSistema frm;
    private Usuario usuario;

    public CtrlSistema(frmSistema frm, Usuario usuario) {
        this.frm = frm;
        this.usuario = usuario;
        
        // Listeners
        this.frm.btnSalir.addActionListener(this);
        this.frm.btnInicio.addActionListener(this);
        this.frm.btnPacientes.addActionListener(this);
        this.frm.btnMedicos.addActionListener(this);
        this.frm.btnCitas.addActionListener(this);
        this.frm.btnExpedientes.addActionListener(this);
    }

    public void iniciar() {
        frm.setTitle("Sistema Hospitalario - Usuario: " + usuario.getUsername());
        frm.setLocationRelativeTo(null);
        frm.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH); // Pantalla completa
        
        // Control de Roles
        aplicarPermisos();
        
        frm.setVisible(true);
    }
    
    private void aplicarPermisos() {
        String rol = usuario.getNombre_rol();
        
        // Si NO es Administrador, ocultar gestión de médicos
        if (!"Administrador".equalsIgnoreCase(rol)) {
            frm.btnMedicos.setVisible(false);
        }
        
        // Si es Paciente, ocultar gestión de Pacientes (solo ve sus citas)
        if ("Paciente".equalsIgnoreCase(rol)) {
            frm.btnPacientes.setVisible(false);
            frm.btnExpedientes.setVisible(false); // Quizás solo pueda ver SU expediente, pero no gestionar todos
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == frm.btnSalir) {
            // Cerrar sesión y volver al login
            frm.dispose();
            // Aquí deberíamos reiniciar el Login, pero por ahora solo cerramos
            System.exit(0); 
        }
        
        if (e.getSource() == frm.btnMedicos) {
            // Abrir gestión de médicos
            System.out.println("Abriendo gestión de médicos...");
        }
        
        // Agregar lógica para los otros botones aquí
    }
}
    