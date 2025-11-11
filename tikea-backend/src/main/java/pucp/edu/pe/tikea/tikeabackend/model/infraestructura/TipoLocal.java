package pucp.edu.pe.tikea.tikeabackend.model.infraestructura;

public enum TipoLocal {
    ESTADIO("Estadio","El local es un estadio"),
    AUDITORIO("Auditorio","El local es un auditorio"),
    TEATRO("Teatro","El local es un teatro"),;

    private final String Descripcion;
    private final String Nombre;

    TipoLocal(String Nombre, String Descripcion) {
        this.Descripcion = Descripcion;
        this.Nombre = Nombre;
    }

    public String getNombre() {
        return this.Nombre;
    }

    public String getDescripcion() {
        return this.Descripcion;
    }
}
