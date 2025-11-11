package pucp.edu.pe.tikea.tikeabackend.model.usuarios;

public enum TipoEstadoProductor {
    PENDIENTE_VALIDACION("Pendiente de validacion","La persona responsable de la autorizacion aun lo esta evaluando"),
    HABILITADO("Habilitado","La persona responsable ha autorizado la accion"),
    RECHAZADO("Rechazado","La persona responsable ha rechazado la accion");

    private final String descripcion;
    private final String nombre;

    TipoEstadoProductor(String nombre,String descripcion) {
        this.descripcion = descripcion;
        this.nombre = nombre;
    }

    public String getDescripcion() {return descripcion;}

    public String getNombre() {return nombre;}
}
