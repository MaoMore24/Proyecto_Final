package Controlador;

import Modelo.ConsultasUsuario;
import Modelo.Usuario;
import Vista.frmLogin;
import Vista.frmSistema;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class CtrlLogin implements ActionListener {
    private Usuario mod;
    private ConsultasUsuario modC;
    private frmLogin frm;

    //Constructor
    public CtrlLogin(Usuario mod, ConsultasUsuario modC, frmLogin frm) {
        this.mod = mod;
        this.modC = modC;
        this.frm = frm;
        this.frm.btnIngresar.addActionListener(this);
        this.frm.btnRegistro.addActionListener(this); 
    }

    public void iniciar() {
        frm.setTitle("Login - Sistema Hospitalario");
        frm.setLocationRelativeTo(null);
        frm.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == frm.btnIngresar) {
            String usuario = frm.txtUsuario.getText();
            String password = new String(frm.txtPassword.getPassword());

            if (usuario.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Por favor ingrese usuario y contraseña");
                return;
            }

            Usuario usuarioLogueado = modC.login(usuario, password);

            if (usuarioLogueado != null) {
                frmSistema frmMenu = new frmSistema();
                CtrlSistema ctrlSistema = new CtrlSistema(frmMenu, usuarioLogueado);
                ctrlSistema.iniciar();
                frm.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos");
            }
        }
        
        if (e.getSource() == frm.btnRegistro) {
            Modelo.Paciente modPac = new Modelo.Paciente();
            Modelo.ConsultasPaciente modCPac = new Modelo.ConsultasPaciente();
            Vista.frmRegistroPacientes frmReg = new Vista.frmRegistroPacientes();
            
            Controlador.CtrlRegistroPaciente ctrlReg = new Controlador.CtrlRegistroPaciente(modPac, modCPac, frmReg);
            ctrlReg.iniciar();
        }
    }
}
