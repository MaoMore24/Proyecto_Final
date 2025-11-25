package Modelo;

import java.sql.Date;

public class Receta {
    private int id;
    private int idExpediente;
    private int idMedico;
    private String medicamentos; // Podría ser una lista, pero usaremos String simple por ahora
    private String instrucciones;
    private Date fecha;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getIdExpediente() { return idExpediente; }
    public void setIdExpediente(int idExpediente) { this.idExpediente = idExpediente; }
    public int getIdMedico() { return idMedico; }
    public void setIdMedico(int idMedico) { this.idMedico = idMedico; }
    public String getMedicamentos() { return medicamentos; }
    public void setMedicamentos(String medicamentos) { this.medicamentos = medicamentos; }
    public String getInstrucciones() { return instrucciones; }
    public void setInstrucciones(String instrucciones) { this.instrucciones = instrucciones; }
    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }
}
