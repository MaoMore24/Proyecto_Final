
package gestionhospitalaria;

import Controlador.ctrlLogin;
import Modelo.ConsultasUsuario;
import Modelo.Usuario;
import Vista.frmLogin;

public class Principal {

    
    public static void main(String[] args) {
        Usuario mod = new Usuario();
        ConsultasUsuario modC = new ConsultasUsuario();
        frmLogin frm = new frmLogin();
        
        ctrlLogin ctrl = new ctrlLogin(mod, modC, frm);
        ctrl.iniciar();
    }
    
}
