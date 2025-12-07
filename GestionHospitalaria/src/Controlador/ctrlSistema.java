package Controlador;

import Modelo.Usuario;
import Modelo.Cita;
import Modelo.ConsultasCita;
import Modelo.ConsultasUsuario;
import Vista.frmSistema;
import Vista.frmAgendarCitas;
import Vista.frmAgregarUsuarios;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ctrlSistema implements ActionListener {
    private frmSistema frm;
    private Usuario usuario;

    public ctrlSistema(frmSistema frm, Usuario usuario) {
        this.frm = frm;
        this.usuario = usuario;
        
        // Listeners
        this.frm.btnSalir.addActionListener(this);
        this.frm.btnInicio.addActionListener(this);
        this.frm.btnPacientes.addActionListener(this);
        this.frm.btnMedicos.addActionListener(this);
        this.frm.btnCitas.addActionListener(this);
        this.frm.btnExpedientes.addActionListener(this);
        this.frm.btnAgenda.addActionListener(this);
    }

    public void iniciar() {
        frm.setTitle("Sistema Hospitalario - Usuario: " + usuario.getUsername());
        frm.setLocationRelativeTo(null);
        frm.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH); // Pantalla completa
        
        // Control de Roles
        aplicarPermisos();
        
        frm.setVisible(true);
    }
    
    /**
     * Método que controla qué botones ve cada perfil/rol.
     * AQUÍ ES DONDE SE CONFIGURAN LOS PERMISOS DE CADA ROL
     */
    private void aplicarPermisos() {
        String rol = usuario.getNombre_rol();
        
        // ====== Ocultar TODOS los botones ======
        frm.btnPacientes.setVisible(false);
        frm.btnMedicos.setVisible(false);
        frm.btnCitas.setVisible(false);
        frm.btnExpedientes.setVisible(false);
        frm.btnAgenda.setVisible(false);
        
        // ====== Mostrar solo los botones según el rol ======
        
        if ("Administrador".equalsIgnoreCase(rol)) {
            
            frm.btnPacientes.setVisible(true);  // Gestión de usuarios (médicos, enfermeros, etc.)
            frm.btnMedicos.setVisible(true);    // Gestión de médicos
            frm.btnExpedientes.setVisible(true); // Ver todos los expedientes
            // btnCitas NO visible (el admin no agenda citas)
            // btnAgenda NO visible (no tiene agenda propia)
        } 
        else if ("Paciente".equalsIgnoreCase(rol)) {
            // ✅ PACIENTE - Solo puede agendar citas
            frm.btnCitas.setVisible(true);       // Agendar citas
            // Todos los demás están ocultos
        } 
        else if ("Medico".equalsIgnoreCase(rol)) {
            // ✅ MÉDICO - Ve su agenda y expedientes
            frm.btnAgenda.setVisible(true);      // Ver su agenda de citas
            frm.btnExpedientes.setVisible(true); // Ver expedientes de pacientes
            // btnCitas NO visible (no agendan, solo ven su agenda)
        }
        else if ("Enfermero".equalsIgnoreCase(rol)) {
            // ✅ ENFERMERO - Ve expedientes
            frm.btnExpedientes.setVisible(true); // Ver expedientes
            // Puedes agregar más botones después si lo necesitas
        }
        else if ("Laboratorio".equalsIgnoreCase(rol)) {
            // ✅ LABORATORIO - Ve expedientes para agregar resultados
            frm.btnExpedientes.setVisible(true); // Ver expedientes
            // Puedes agregar más botones después si lo necesitas
        }
        
        // El botón "Inicio" siempre está visible (botón principal del toolbar)
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == frm.btnSalir) {
            // Cerrar sesión y volver al login
            frm.dispose();
            System.exit(0); 
        }
        
        if (e.getSource() == frm.btnCitas) {
            // Abrir formulario de agendar citas (para pacientes principalmente)
            Cita cita = new Cita();
            ConsultasCita consCita = new ConsultasCita();
            frmAgendarCitas frmCitas = new frmAgendarCitas();
            CtrlAgendarCita ctrlCitas = new CtrlAgendarCita(cita, consCita, frmCitas, usuario);
            ctrlCitas.iniciar();
        }
        
        if (e.getSource() == frm.btnPacientes) {
            // Abrir formulario de registrar usuarios (para Administrador)
            Usuario usr = new Usuario();
            ConsultasUsuario consUsr = new ConsultasUsuario();
            frmAgregarUsuarios frmUsuarios = new frmAgregarUsuarios();
            CtrlRegistrarUsuario ctrlUsuarios = new CtrlRegistrarUsuario(usr, consUsr, frmUsuarios);
            ctrlUsuarios.iniciar();
        }
        
        if (e.getSource() == frm.btnMedicos) {
            // Abrir gestión de médicos
            System.out.println("Abriendo gestión de médicos...");
            // TODO: Implementar cuando sea necesario
        }
        
        if (e.getSource() == frm.btnAgenda) {
            // Abrir agenda del médico
            System.out.println("Abriendo agenda del médico...");
            // TODO: Implementar cuando sea necesario
        }
        
        if (e.getSource() == frm.btnExpedientes) {
            // Abrir expedientes
            System.out.println("Abriendo expedientes...");
            // TODO: Implementar cuando sea necesario
        }
    }
}