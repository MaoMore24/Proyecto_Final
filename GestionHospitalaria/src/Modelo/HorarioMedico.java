package Modelo;

import java.sql.Time;

public class HorarioMedico {
    private int id;
    private int idMedico;
    private String diaSemana;
    private Time horaInicio;
    private Time horaFin;

    public HorarioMedico() {
        this.id = 0;
        this.idMedico = 0;
        this.diaSemana = "";
        this.horaInicio = null;
        this.horaFin = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(int idMedico) {
        this.idMedico = idMedico;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public Time getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Time horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Time getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(Time horaFin) {
        this.horaFin = horaFin;
    }
}
