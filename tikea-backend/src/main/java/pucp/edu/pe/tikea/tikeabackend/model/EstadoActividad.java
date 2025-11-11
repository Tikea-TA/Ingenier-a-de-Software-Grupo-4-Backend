package pucp.edu.pe.tikea.tikeabackend.model;

public enum EstadoActividad {
    ACTIVA("Activa", "Promoción activa"),
    INACTIVA("Inactiva", "Promoción inactiva"),
    FINALIZADA("Finalizada", "Promoción finalizada");

    private final String nombre;
    private final String descripcion;

    EstadoActividad(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }
    
}
