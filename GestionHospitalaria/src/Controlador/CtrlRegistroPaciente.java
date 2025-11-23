package Controlador;

import Modelo.ConsultasPaciente;
import Modelo.Paciente;
import Vista.frmRegistroPacientes;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import javax.swing.JOptionPane;

public class CtrlRegistroPaciente implements ActionListener {
    
    private Paciente mod;
    private ConsultasPaciente modC;
    private frmRegistroPacientes frm;
    
    public CtrlRegistroPaciente(Paciente mod, ConsultasPaciente modC, frmRegistroPacientes frm) {
        this.mod = mod;
        this.modC = modC;
        this.frm = frm;
        
        this.frm.btnRegistrar.addActionListener(this);
        this.frm.btnCancelar.addActionListener(this);
    }
    
    public void iniciar() {
        frm.setTitle("Registro de Nuevo Paciente");
        frm.setLocationRelativeTo(null);
        frm.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == frm.btnRegistrar) {
            // Validaciones básicas
            if (frm.txtUsuario.getText().isEmpty() || new String(frm.txtPassword.getPassword()).isEmpty() ||
                frm.txtNombre.getText().isEmpty() || frm.txtApellido.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Por favor llene los campos obligatorios");
                return;
            }
            
            mod.setUsername(frm.txtUsuario.getText());
            mod.setPassword(new String(frm.txtPassword.getPassword()));
            mod.setNombre(frm.txtNombre.getText());
            mod.setApellido(frm.txtApellido.getText());
            mod.setEmail(frm.txtEmail.getText());
            mod.setTelefono(frm.txtFechaNacimiento.getText()); // Aseguramos que este sea el teléfono
            mod.setDireccion(frm.txtDireccion.getText());
            
            // Manejo de fecha (formato dd-MM-yyyy o dd/MM/yyyy)
            String fechaStr = "";
            try {
                // LEER DEL CAMPO DE FECHA, NO DEL TELEFONO
                fechaStr = frm.txtTelefono.getText().trim(); 
                
                // Reemplazar barras por guiones para estandarizar
                fechaStr = fechaStr.replace("/", "-");
                
                java.text.SimpleDateFormat formatoEntrada = new java.text.SimpleDateFormat("dd-MM-yyyy");
                formatoEntrada.setLenient(false); 
                java.util.Date fechaUtil = formatoEntrada.parse(fechaStr);
                
                // Convertir a java.sql.Date
                mod.setFechaNacimiento(new java.sql.Date(fechaUtil.getTime()));
                
            } catch (java.text.ParseException ex) {
                JOptionPane.showMessageDialog(null, "Error en la fecha. El sistema recibió: '" + fechaStr + "'\nFormato requerido: día-mes-año (ej: 25-12-1990)");
                return;
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(null, "Fecha inválida");
                return;
            }
            
            if (modC.registrarPaciente(mod)) {
                JOptionPane.showMessageDialog(null, "Registro Exitoso");
                limpiar();
                frm.dispose(); // Cerrar ventana tras registro
            } else {
                JOptionPane.showMessageDialog(null, "Error al guardar. Revise la consola (Output) para ver el detalle del error SQL.");
            }
        }
        
        if (e.getSource() == frm.btnCancelar) {
            frm.dispose();
        }
    }
    
    public void limpiar() {
        frm.txtUsuario.setText(null);
        frm.txtPassword.setText(null);
        frm.txtNombre.setText(null);
        frm.txtApellido.setText(null);
        frm.txtEmail.setText(null);
        frm.txtFechaNacimiento.setText(null);
        frm.txtDireccion.setText(null);
        frm.txtTelefono.setText(null);
    }
    
}
