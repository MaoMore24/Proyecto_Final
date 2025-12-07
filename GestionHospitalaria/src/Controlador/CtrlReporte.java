package Controlador;

import Modelo.ConsultasReporte;
import Modelo.Reporte;
import Vista.frmReporte;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 * Controlador para el formulario de reportes
 * @author mms24
 */
public class CtrlReporte implements ActionListener {

    private final Reporte modelo;
    private final ConsultasReporte consultas;
    private final frmReporte vista;

    public CtrlReporte(Reporte modelo, ConsultasReporte consultas, frmReporte vista) {
        this.modelo = modelo;
        this.consultas = consultas;
        this.vista = vista;
        this.vista.btnActualizar.addActionListener(this);
        this.vista.btnFiltrar.addActionListener(this);
        this.vista.btnCerrar.addActionListener(this);
    }

    public void iniciar() {
        vista.setTitle("Reporte de Citas");
        vista.setLocationRelativeTo(null);
        cargarEstadisticas();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnActualizar) {
            cargarEstadisticas();
        } else if (e.getSource() == vista.btnFiltrar) {
            filtrarEstadisticas();
        } else if (e.getSource() == vista.btnCerrar) {
            vista.dispose();
        }
    }

    /**
     * Carga las estadísticas sin filtro de fechas
     */
    private void cargarEstadisticas() {
        if (consultas.obtenerEstadisticas(modelo)) {
            actualizarVista();
        } else {
            JOptionPane.showMessageDialog(null, "Error al cargar estadísticas");
        }
    }

    /**
     * Filtra las estadísticas por rango de fechas
     */
    private void filtrarEstadisticas() {
        String fechaInicio = vista.txtFechaInicio.getText().trim();
        String fechaFin = vista.txtFechaFin.getText().trim();

        if (fechaInicio.isEmpty() || fechaFin.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese ambas fechas (formato: DD-MM-YYYY)");
            return;
        }

        // Validar formato DD-MM-YYYY
        if (!validarFormatoFecha(fechaInicio) || !validarFormatoFecha(fechaFin)) {
            JOptionPane.showMessageDialog(null, "Formato de fecha incorrecto. Use: DD-MM-YYYY");
            return;
        }

        // Convertir de DD-MM-YYYY a YYYY-MM-DD para MySQL
        String fechaInicioMySQL = convertirFecha(fechaInicio);
        String fechaFinMySQL = convertirFecha(fechaFin);

        modelo.setFechaInicio(fechaInicioMySQL);
        modelo.setFechaFin(fechaFinMySQL);

        if (consultas.obtenerEstadisticasConFiltro(modelo)) {
            actualizarVista();
            JOptionPane.showMessageDialog(null, "Filtro aplicado correctamente");
        } else {
            JOptionPane.showMessageDialog(null, "Error al aplicar filtro");
        }
    }

    /**
     * Actualiza los labels de la vista con los valores del modelo
     */
    private void actualizarVista() {
        vista.lblAtendidas.setText(String.valueOf(modelo.getAtendidas()));
        vista.lblCanceladas.setText(String.valueOf(modelo.getCanceladas()));
        vista.lblAusentes.setText(String.valueOf(modelo.getAusentes()));
    }

    /**
     * Valida que la fecha tenga formato DD-MM-YYYY
     */
    private boolean validarFormatoFecha(String fecha) {
        return fecha.matches("\\d{2}-\\d{2}-\\d{4}");
    }

    /**
     * Convierte fecha de DD-MM-YYYY a YYYY-MM-DD para MySQL
     */
    private String convertirFecha(String fecha) {
        String[] partes = fecha.split("-");
        return partes[2] + "-" + partes[1] + "-" + partes[0];
    }
}
