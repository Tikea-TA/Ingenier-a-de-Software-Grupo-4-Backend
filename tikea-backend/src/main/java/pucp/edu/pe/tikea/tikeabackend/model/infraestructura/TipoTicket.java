package pucp.edu.pe.tikea.tikeabackend.model.infraestructura;

public enum TipoTicket {
    QR("QR", "El codigo que tendrá el ticket será un codigo QR"),
    ALFANUMERICO("Alfanumerico", "El codigo que tendrá el ticket será un codigo alfanumerico");

    private final String nombre;
    private final String descripcion;

    TipoTicket(String nombre, String descripcion) {
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
