package Modelo;

/**
 * Clase que representa las estadísticas de reportes de citas
 * @author mms24
 */
public class Reporte {
    
    private int atendidas;
    private int canceladas;
    private int ausentes;
    private String fechaInicio;
    private String fechaFin;

    public Reporte() {
        this.atendidas = 0;
        this.canceladas = 0;
        this.ausentes = 0;
        this.fechaInicio = "";
        this.fechaFin = "";
    }

    public Reporte(int atendidas, int canceladas, int ausentes, String fechaInicio, String fechaFin) {
        this.atendidas = atendidas;
        this.canceladas = canceladas;
        this.ausentes = ausentes;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public int getAtendidas() {
        return atendidas;
    }

    public void setAtendidas(int atendidas) {
        this.atendidas = atendidas;
    }

    public int getCanceladas() {
        return canceladas;
    }

    public void setCanceladas(int canceladas) {
        this.canceladas = canceladas;
    }

    public int getAusentes() {
        return ausentes;
    }

    public void setAusentes(int ausentes) {
        this.ausentes = ausentes;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }
}
