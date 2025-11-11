package pucp.edu.pe.tikea.tikeabackend.model;

public enum TipoRestriccionPromocion {
    TAQUILLA("Promoción por taquilla", "Promoción solo válida para venta en taquilla"),
    WEB("Promoción por web", "Promoción sólo válida para venta por web");

    private final String nombre;
    private final String descripcion;

    TipoRestriccionPromocion(String nombre, String descripcion) {
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
