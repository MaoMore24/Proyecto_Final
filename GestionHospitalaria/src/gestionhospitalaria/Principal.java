
package gestionhospitalaria;

import Controlador.CtrlLogin;
import Modelo.ConsultasUsuario;
import Modelo.Usuario;
import Vista.frmLogin;

public class Principal {

    
    public static void main(String[] args) {
        Usuario mod = new Usuario();
        ConsultasUsuario modC = new ConsultasUsuario();
        frmLogin frm = new frmLogin();
        
        CtrlLogin ctrl = new CtrlLogin(mod, modC, frm);
        ctrl.iniciar();
    }
    
}
