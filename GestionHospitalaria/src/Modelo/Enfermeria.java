package Modelo;

/**
 * Clase que representa un registro de enfermería
 * @author mms24
 */
public class Enfermeria {
    private int id;
    private int idExpediente;
    private int idEnfermero;
    private String expedienteEnfermeria;
    private String procedimientos;
    private String medicamentos;
    private java.sql.Timestamp fechaRegistro;

    // Constructor vacío
    public Enfermeria() {
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdExpediente() {
        return idExpediente;
    }

    public void setIdExpediente(int idExpediente) {
        this.idExpediente = idExpediente;
    }

    public int getIdEnfermero() {
        return idEnfermero;
    }

    public void setIdEnfermero(int idEnfermero) {
        this.idEnfermero = idEnfermero;
    }

    public String getExpedienteEnfermeria() {
        return expedienteEnfermeria;
    }

    public void setExpedienteEnfermeria(String expedienteEnfermeria) {
        this.expedienteEnfermeria = expedienteEnfermeria;
    }

    public String getProcedimientos() {
        return procedimientos;
    }

    public void setProcedimientos(String procedimientos) {
        this.procedimientos = procedimientos;
    }

    public String getMedicamentos() {
        return medicamentos;
    }

    public void setMedicamentos(String medicamentos) {
        this.medicamentos = medicamentos;
    }

    public java.sql.Timestamp getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(java.sql.Timestamp fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}
