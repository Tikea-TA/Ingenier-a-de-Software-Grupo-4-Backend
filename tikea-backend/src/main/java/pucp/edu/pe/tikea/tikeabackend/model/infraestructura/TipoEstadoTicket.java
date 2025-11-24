package pucp.edu.pe.tikea.tikeabackend.model.infraestructura;

public enum TipoEstadoTicket {
    DISPONIBLE("Disponible", "El ticket este libre para ser comprada"),
    ENTREGADA("Entregada", "El ticket ha sido entregado al personal para el ingreso"),
    UTILIZADA("Utilizada", "El ticket ya ha sido utilizado para el ingreso"),
    ANULADA("Analizada", "El ticket ya ha sido cancelado"),
    CANJEADA("Canjeada", "El ticket ya sido canjeado por medio de puntos o promociones");

    private final String nombre;
    private final String descripcion;

    TipoEstadoTicket(String nombre, String descripcion) {
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
