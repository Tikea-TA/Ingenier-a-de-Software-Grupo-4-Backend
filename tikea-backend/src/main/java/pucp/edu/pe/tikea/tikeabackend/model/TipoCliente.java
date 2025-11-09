package pucp.edu.pe.tikea.tikeabackend.model;

public enum TipoCliente {
    REGISTRADO("Registrado","CLiente esta registrado en el sistema"),
    NO_REGISTRADO("NoRegistrado","Cliente no registrado");

    private final String descripcion;
    private final String Nombre;

    TipoCliente(String nombre, String descripcion) {
        this.Nombre = nombre;
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return this.Nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
