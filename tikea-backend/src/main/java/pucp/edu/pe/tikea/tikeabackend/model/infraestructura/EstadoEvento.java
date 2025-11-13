package pucp.edu.pe.tikea.tikeabackend.model.infraestructura;

public enum EstadoEvento {
    CANCELADO("Cancelado", "Evento cancelado"),
    FINALIZADO("Finalizado", "Evento finalizado"),
    AGOTADO("Agotado", "Evento con entradas agotadas"),
    PUBLICADO("Publicado", "Evento publicado");

    private final String nombre;
    private final String descripcion;

    EstadoEvento(String nombre, String descripcion) {
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
