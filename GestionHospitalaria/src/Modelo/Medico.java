package Modelo;

public class Medico extends Usuario {
    private int idEspecialidad;
    private String nombreEspecialidad;
    private String cedula;

    public int getIdEspecialidad() {
        return idEspecialidad;
    }

    public void setIdEspecialidad(int idEspecialidad) {
        this.idEspecialidad = idEspecialidad;
    }

    public String getNombreEspecialidad() {
        return nombreEspecialidad;
    }

    public void setNombreEspecialidad(String nombreEspecialidad) {
        this.nombreEspecialidad = nombreEspecialidad;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }
    
    // Sobreescribimos toString para que al llenar el ComboBox se vea el nombre si llenamos con objetos
    @Override
    public String toString() {
        return getNombre() + " " + getApellido();
    }
}
