package pucp.edu.pe.tikea.tikeabackend.model.promocion;

public enum TipoPromocion {
    DESCUENTO_PREVENTA("Descuento para preventa", "Este descuento solo aplica en preventa"),
    DESCUENTO_PORCENTAJE("Descuento con porcentaje", "Este descuento resta un porcentaje del precio base"),
    DESCUENTO_FIJO("Descuento con valor fijo", "Este descuento resta un valor fijo del precio base"),
    CUPON("Descuento por cupón", "Este descuento utiliza un cupón para reducir el precio base");

    private final String nombre;
    private final String descripcion;

    TipoPromocion(String nombre, String descripcion) {
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
