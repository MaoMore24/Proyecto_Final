package Controlador;

import Modelo.Usuario;
import Modelo.Cita;
import Modelo.Medico;
import Modelo.ConsultasCita;
import Modelo.ConsultasUsuario;
import Modelo.ConsultasMedico;
import Modelo.ConsultasEnfermeria;
import Modelo.ConsultasLaboratorio;
import Modelo.Enfermeria;
import Modelo.ResultadoLaboratorio;
import Modelo.Reporte;
import Modelo.ConsultasReporte;
import Vista.frmSistema;
import Vista.frmAgendarCitas;
import Vista.frmAgregarUsuarios;
import Vista.frmEnfermeria;
import Vista.frmLaboratorio;
import Vista.frmReporte;
import Vista.frmMedicos;
import Vista.frmAgenda;
import Vista.frmRegistroPacientes;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Modelo.Paciente;
import Modelo.ConsultasPaciente;
import Modelo.ConsultasExpediente;
import Vista.frmExpediente;


public class ctrlSistema implements ActionListener {
    private frmSistema frm;
    private Usuario usuario;

    public ctrlSistema(frmSistema frm, Usuario usuario) {
        this.frm = frm;
        this.usuario = usuario;
        
        // Listeners
        this.frm.btnSalir.addActionListener(this);
        // btnInicio eliminado de la vista
        this.frm.btnPacientes.addActionListener(this);
        this.frm.btnMedicos.addActionListener(this);
        this.frm.btnCitas.addActionListener(this);
        this.frm.btnExpedientes.addActionListener(this);
        this.frm.btnAgenda.addActionListener(this);
        this.frm.btnReportes.addActionListener(this);
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
        frm.btnReportes.setVisible(false);
        
        // ====== Mostrar solo los botones según el rol ======
        
        if ("Administrador (a)".equalsIgnoreCase(rol)) {
            
            // "btnPacientes" se reutiliza para el formulario de Agregar Usuarios (Médicos, Enf, etc.)
            frm.btnPacientes.setVisible(true);  
            frm.btnPacientes.setText("Registro"); // Opcional: Cambiar texto para claridad
            
            frm.btnReportes.setVisible(true);   // Ver reportes y estadísticas
            
            // Ocultar explícitamente otros que podrían haber estado visibles
            frm.btnMedicos.setVisible(false);
            frm.btnExpedientes.setVisible(false);
        } 
        else if ("Persona Paciente".equalsIgnoreCase(rol)) {
            // ✅ PACIENTE - Solo puede agendar citas
            frm.btnCitas.setVisible(true);       // Agendar citas
            // Todos los demás están ocultos
        } 
        else if ("Médico (a)".equalsIgnoreCase(rol)) {
            // ✅ MÉDICO - Ve su agenda y expedientes
            frm.btnAgenda.setVisible(true);      // Ver su agenda de citas
            frm.btnExpedientes.setVisible(true); // Ver expedientes de pacientes
            frm.btnReportes.setVisible(true);    // Ver Historial
            frm.btnReportes.setText("Historial");
            // btnCitas NO visible (no agendan, solo ven su agenda)
        }
        else if ("Enfermero (a)".equalsIgnoreCase(rol)) {
            // ✅ ENFERMERO - Accede a su módulo específico
            frm.btnExpedientes.setVisible(true); // Abre frmEnfermeria
        }
        else if ("Personal de Laboratorio".equalsIgnoreCase(rol)) {
            // ✅ LABORATORIO - Accede a su módulo específico
            frm.btnExpedientes.setVisible(true); // Abre frmLaboratorio
        }
        else if ("Auxiliar Administrativo".equalsIgnoreCase(rol)) {
            // ✅ AUXILIAR - Gestión administrativa básica
            frm.btnPacientes.setVisible(true);
            frm.btnPacientes.setText("Registro"); // Cambiado a Registro general
            frm.btnMedicos.setVisible(true);      // Gestión de médicos
            frm.btnReportes.setVisible(true);     // Reportes
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
            String rol = usuario.getNombre_rol();
            
            if ("Auxiliar Administrativo".equalsIgnoreCase(rol)) {
                // Auxiliar: Registra Pacientes
                Paciente mod = new Paciente();
                ConsultasPaciente modC = new ConsultasPaciente();
                frmRegistroPacientes vista = new frmRegistroPacientes();
                CtrlRegistroPaciente ctrl = new CtrlRegistroPaciente(mod, modC, vista);
                ctrl.iniciar();
            }
            else if ("Administrador (a)".equalsIgnoreCase(rol)) {
                 // Admin: Gestiona Usuarios del sistema (Médicos, Enfermeros, etc.)
                 Usuario usr = new Usuario();
                 ConsultasUsuario consUsr = new ConsultasUsuario();
                 frmAgregarUsuarios frmUsuarios = new frmAgregarUsuarios();
                 CtrlRegistrarUsuario ctrlUsuarios = new CtrlRegistrarUsuario(usr, consUsr, frmUsuarios);
                 ctrlUsuarios.iniciar();
            }
        }
        
        if (e.getSource() == frm.btnMedicos) {
            // Abrir gestión de médicos
            Medico mod = new Medico();
            ConsultasMedico modC = new ConsultasMedico();
            frmMedicos vista = new frmMedicos();
            CtrlMedico ctrl = new CtrlMedico(mod, modC, vista);
            ctrl.iniciar();
        }
        
        if (e.getSource() == frm.btnAgenda) {
            
            ConsultasCita modC = new ConsultasCita();
            frmAgenda vista = new frmAgenda();
            
            // Constructor correcto: (ConsultasCita, frmAgenda, Usuario)
            CtrlAgenda ctrl = new CtrlAgenda(modC, vista, usuario);
            ctrl.iniciar();
            vista.setVisible(true); // Faltaba hacer visible la vista
        }
        
        if (e.getSource() == frm.btnExpedientes) {
            // Abrir formulario según el rol
            String rol = usuario.getNombre_rol();
            
            if ("Enfermero (a)".equalsIgnoreCase(rol)) {
                
                Enfermeria modelo = new Enfermeria();
                ConsultasEnfermeria consultas = new ConsultasEnfermeria();
                frmEnfermeria vista = new frmEnfermeria();
                CtrlEnfermeria ctrl = new CtrlEnfermeria(modelo, consultas, vista, usuario.getId());
                ctrl.iniciar();
                vista.setVisible(true);
            } 
            else if ("Personal de Laboratorio".equalsIgnoreCase(rol)) {
                
                ResultadoLaboratorio modelo = new ResultadoLaboratorio();
                ConsultasLaboratorio consultas = new ConsultasLaboratorio();
                frmLaboratorio vista = new frmLaboratorio();
                CtrlLaboratorio ctrl = new CtrlLaboratorio(modelo, consultas, vista, usuario.getId());
                ctrl.iniciar();
                vista.setVisible(true);
            }
            else if ("Médico (a)".equalsIgnoreCase(rol)) {
                // ✅ MÉDICO: Puede ver y editar expedientes
                ConsultasExpediente consExp = new ConsultasExpediente();
                frmExpediente frmExp = new frmExpediente();
                // Pass current user (doctor) to controller
                CtrlExpediente ctrlExp = new CtrlExpediente(consExp, frmExp, usuario);
                ctrlExp.iniciar();
                frmExp.setVisible(true);
            }
            else if ("Administrador (a)".equalsIgnoreCase(rol)) {
                // ✅ ADMIN: Puede ver expedientes (modo lectura o edición completa según se defina)
                
                ConsultasExpediente consExp = new ConsultasExpediente();
                frmExpediente frmExp = new frmExpediente();
                CtrlExpediente ctrlExp = new CtrlExpediente(consExp, frmExp, usuario);
                ctrlExp.iniciar();
                frmExp.setVisible(true);
            }
        }
        
        if (e.getSource() == frm.btnReportes) {
            
            Reporte modelo = new Reporte();
            ConsultasReporte consultas = new ConsultasReporte();
            frmReporte vista = new frmReporte();
            CtrlReporte ctrl = new CtrlReporte(modelo, consultas, vista);
            ctrl.iniciar();
            vista.setVisible(true);
        }
    }
}