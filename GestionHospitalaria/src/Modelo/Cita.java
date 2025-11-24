package Modelo;

import java.sql.Date;
import java.sql.Time;

public class Cita {
    private int id;
    private int idPaciente;
    private int idMedico;
    private Date fecha;
    private Time hora;
    private String motivo;
    private String estado; // 'Pendiente', 'Atendida', 'Cancelada'
    
    // Campos auxiliares para mostrar en tabla
    private String nombreMedico;
    private String especialidad;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getIdPaciente() { return idPaciente; }
    public void setIdPaciente(int idPaciente) { this.idPaciente = idPaciente; }
    public int getIdMedico() { return idMedico; }
    public void setIdMedico(int idMedico) { this.idMedico = idMedico; }
    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }
    public Time getHora() { return hora; }
    public void setHora(Time hora) { this.hora = hora; }
    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getNombreMedico() { return nombreMedico; }
    public void setNombreMedico(String nombreMedico) { this.nombreMedico = nombreMedico; }
    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }
}
