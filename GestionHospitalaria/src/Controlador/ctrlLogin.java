
package Controlador;

import Modelo.ConsultasUsuario;
import Modelo.Usuario;
import Vista.frmLogin;
import Vista.frmSistema;
import Controlador.ctrlSistema;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class ctrlLogin implements ActionListener {
    private Usuario mod;
    private ConsultasUsuario modC;
    private frmLogin frm;

    public ctrlLogin(Usuario mod, ConsultasUsuario modC, frmLogin frm) {
        this.mod = mod;
        this.modC = modC;
        this.frm = frm;
        this.frm.btnIngresar.addActionListener(this);
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
                // Abrir menú principal con el controlador
                frmSistema frmMenu = new frmSistema();
                ctrlSistema ctrlSistema = new ctrlSistema(frmMenu, usuarioLogueado);
                ctrlSistema.iniciar();
                
                // Cerrar login
                frm.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos");
            }
        }
    }
}
