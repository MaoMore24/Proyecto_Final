package Controlador;

import Modelo.Usuario;
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
    
    private void aplicarPermisos() {
        String rol = usuario.getNombre_rol();
        
        // Si NO es Administrador, ocultar gestión de médicos
        if (!"Administrador".equalsIgnoreCase(rol)) {
            frm.btnMedicos.setVisible(false);
        }
        
        // Si es Paciente, ocultar gestión de Pacientes (solo ve sus citas)
        if ("Paciente".equalsIgnoreCase(rol)) {
            frm.btnPacientes.setVisible(false);
            frm.btnExpedientes.setVisible(false); 
            frm.btnAgenda.setVisible(false); 
        }
        
        if (!"Medico".equalsIgnoreCase(rol)) {
            frm.btnAgenda.setVisible(false); 
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == frm.btnSalir) {
            frm.dispose();
            System.exit(0); 
        }
        
        if (e.getSource() == frm.btnMedicos) {
            Modelo.Medico modMed = new Modelo.Medico();
            Modelo.ConsultasMedico modCMed = new Modelo.ConsultasMedico();
            Vista.frmMedicos frmMed = new Vista.frmMedicos();
            
            CtrlMedico ctrlMed = new CtrlMedico(modMed, modCMed, frmMed);
            ctrlMed.iniciar();
            frmMed.setVisible(true);
        }
        
        // Aquí puedes agregar la lógica para los otros botones cuando tengamos sus controladores listos
    }
}
