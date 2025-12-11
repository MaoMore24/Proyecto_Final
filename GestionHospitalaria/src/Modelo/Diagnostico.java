package Modelo;

import java.sql.Date;

public class Diagnostico {
    private int id;
    private int idExpediente;
    private int idMedico;
    private int idPaciente;
    private String padecimientos;
    private String diagnostico;
    private String notas;
    private Date fecha;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getIdExpediente() { return idExpediente; }
    public void setIdExpediente(int idExpediente) { this.idExpediente = idExpediente; }
    public int getIdMedico() { return idMedico; }
    public void setIdMedico(int idMedico) { this.idMedico = idMedico; }
    public int getIdPaciente() { return idPaciente; }
    public void setIdPaciente(int idPaciente) { this.idPaciente = idPaciente; }
    public String getPadecimientos() { return padecimientos; }
    public void setPadecimientos(String padecimientos) { this.padecimientos = padecimientos; }
    public String getDiagnostico() { return diagnostico; }
    public void setDiagnostico(String diagnostico) { this.diagnostico = diagnostico; }
    public String getNotas() { return notas; }
    public void setNotas(String notas) { this.notas = notas; }
    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }
}
