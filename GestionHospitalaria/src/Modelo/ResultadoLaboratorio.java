package Modelo;

/**
 * Clase que representa un resultado de laboratorio
 * @author mms24
 */
public class ResultadoLaboratorio {
    private int id;
    private int idExpediente;
    private int idTecnico;
    private String expedienteLaboratorio;
    private String procedimientos;
    private String resultados;
    private java.sql.Timestamp fechaRegistro;

    // Constructor vacío
    public ResultadoLaboratorio() {
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

    public int getIdTecnico() {
        return idTecnico;
    }

    public void setIdTecnico(int idTecnico) {
        this.idTecnico = idTecnico;
    }

    public String getExpedienteLaboratorio() {
        return expedienteLaboratorio;
    }

    public void setExpedienteLaboratorio(String expedienteLaboratorio) {
        this.expedienteLaboratorio = expedienteLaboratorio;
    }

    public String getProcedimientos() {
        return procedimientos;
    }

    public void setProcedimientos(String procedimientos) {
        this.procedimientos = procedimientos;
    }

    public String getResultados() {
        return resultados;
    }

    public void setResultados(String resultados) {
        this.resultados = resultados;
    }

    public java.sql.Timestamp getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(java.sql.Timestamp fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}
