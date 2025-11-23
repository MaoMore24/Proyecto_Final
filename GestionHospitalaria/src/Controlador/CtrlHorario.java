package Controlador;

import Modelo.ConsultasHorario;
import Modelo.HorarioMedico;
import Vista.frmHorario;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;
import javax.swing.JOptionPane;

public class CtrlHorario implements ActionListener {

    private final HorarioMedico modelo;
    private final ConsultasHorario consultas;
    private final frmHorario vista;

    public CtrlHorario(HorarioMedico modelo, ConsultasHorario consultas, frmHorario vista) {
        this.modelo = modelo;
        this.consultas = consultas;
        this.vista = vista;
        this.vista.btnGuardar.addActionListener(this);
        this.vista.btnModificar.addActionListener(this);
        this.vista.btnEliminar.addActionListener(this);
        this.vista.btnLimpiar.addActionListener(this);
        this.vista.btnBuscar.addActionListener(this);
    }

    public void iniciar() {
        vista.setTitle("Gestión de Horarios");
        vista.setLocationRelativeTo(null);
    }

    public void limpiar() {
        vista.txtId.setText("");
        vista.txtIdMedico.setText("");
        vista.cbxDia.setSelectedIndex(0);
        vista.txtHoraInicio.setText("");
        vista.txtHoraFin.setText("");
        vista.txtIdMedico.requestFocus();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Botón Guardar
        if (e.getSource() == vista.btnGuardar) {
            try {
                modelo.setIdMedico(Integer.parseInt(vista.txtIdMedico.getText()));
                modelo.setDiaSemana(vista.cbxDia.getSelectedItem().toString());
                modelo.setHoraInicio(Time.valueOf(vista.txtHoraInicio.getText()));
                modelo.setHoraFin(Time.valueOf(vista.txtHoraFin.getText()));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error en los datos. Verifique ID y formato de hora (HH:MM:SS)");
                return;
            }

            if (consultas.registrar(modelo)) {
                JOptionPane.showMessageDialog(null, "Horario guardado");
                limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "Error al guardar horario (posible conflicto)");
                limpiar();
            }
        }

        // Botón Modificar
        if (e.getSource() == vista.btnModificar) {
            try {
                modelo.setId(Integer.parseInt(vista.txtId.getText()));
                modelo.setIdMedico(Integer.parseInt(vista.txtIdMedico.getText()));
                modelo.setDiaSemana(vista.cbxDia.getSelectedItem().toString());
                modelo.setHoraInicio(Time.valueOf(vista.txtHoraInicio.getText()));
                modelo.setHoraFin(Time.valueOf(vista.txtHoraFin.getText()));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error en los datos");
                return;
            }

            if (consultas.modificar(modelo)) {
                JOptionPane.showMessageDialog(null, "Horario modificado");
                limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "Error al modificar horario");
                limpiar();
            }
        }

        // Botón Eliminar
        if (e.getSource() == vista.btnEliminar) {
            try {
                modelo.setId(Integer.parseInt(vista.txtId.getText()));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "El ID debe ser numérico");
                return;
            }

            if (consultas.eliminar(modelo)) {
                JOptionPane.showMessageDialog(null, "Horario eliminado");
                limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "Error al eliminar horario");
                limpiar();
            }
        }

        // Botón Buscar
        if (e.getSource() == vista.btnBuscar) {
            try {
                modelo.setId(Integer.parseInt(vista.txtId.getText()));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "El ID debe ser numérico");
                return;
            }

            if (consultas.buscar(modelo)) {
                vista.txtId.setText(String.valueOf(modelo.getId()));
                vista.txtIdMedico.setText(String.valueOf(modelo.getIdMedico()));
                vista.cbxDia.setSelectedItem(modelo.getDiaSemana());
                vista.txtHoraInicio.setText(modelo.getHoraInicio().toString());
                vista.txtHoraFin.setText(modelo.getHoraFin().toString());
                JOptionPane.showMessageDialog(null, "Horario encontrado");
            } else {
                JOptionPane.showMessageDialog(null, "Error al buscar horario");
                limpiar();
            }
        }

        // Botón Limpiar
        if (e.getSource() == vista.btnLimpiar) {
            limpiar();
        }
    }
}
