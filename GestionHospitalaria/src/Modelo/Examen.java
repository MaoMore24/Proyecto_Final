package Modelo;

import java.sql.Date;

public class Examen {
    private int id;
    private int idExpediente;
    private int idMedico;
    private String tipoExamen;
    private String resultados;
    private Date fecha;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getIdExpediente() { return idExpediente; }
    public void setIdExpediente(int idExpediente) { this.idExpediente = idExpediente; }
    public int getIdMedico() { return idMedico; }
    public void setIdMedico(int idMedico) { this.idMedico = idMedico; }
    public String getTipoExamen() { return tipoExamen; }
    public void setTipoExamen(String tipoExamen) { this.tipoExamen = tipoExamen; }
    public String getResultados() { return resultados; }
    public void setResultados(String resultados) { this.resultados = resultados; }
    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }
}
