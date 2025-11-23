package pucp.edu.pe.tikea.tikeabackend.model.infraestructura;

public enum TipoEstadoAproDes {
    ACTIVO("Activo", "El Punto de venta o el Medio de pago seleccionado esta activo en esta ocacion"),
    INACTIVO("Inactivo", "El Punto de venta o el Medio de pago seleccionado no esta disponible por el momento, pero si existe");

    private final String nombre;
    private final String descripcion;

    TipoEstadoAproDes(String nombre, String descripcion) {
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
