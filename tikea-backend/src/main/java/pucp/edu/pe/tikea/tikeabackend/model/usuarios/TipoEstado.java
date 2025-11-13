package pucp.edu.pe.tikea.tikeabackend.model.usuarios;

public enum TipoEstado {
    ACTIVO("Activo", "Usuario puede acceder"),
    INACTIVO("Inactivo", "Usuario temporalmente deshabilitado"),
    BLOQUEADO("Bloqueado", "Usuario bloqueado por seguridad");

    private final String nombre;
    private final String descripcion;

    TipoEstado(String nombre, String descripcion) {
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
