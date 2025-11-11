package pucp.edu.pe.tikea.tikeabackend.model.infraestructura;

public enum TipoEstadoLocal {
    PENDIENTE_VALIDACION("Pendiente de una validacion","El local está siendo evaluado por el gestor encargado para su uso en la plataforma"),
    HABILITADO("Habilitado","El local ha sido aprobado para el uso de los productores"),
    RECHAZADO("Rechazado","El local no fue aprobado, por lo tanto no se puede usar hasta una nueva aprobacion");

    private final String Descripcion;
    private final String Nombre;

    TipoEstadoLocal(String Nombre,String Descripcion) {
        this.Nombre = Nombre;
        this.Descripcion = Descripcion;
    }

    public String getDescripcion() {
        return this.Descripcion;
    }

    public String getNombre() {
        return this.Nombre;
    }
}
