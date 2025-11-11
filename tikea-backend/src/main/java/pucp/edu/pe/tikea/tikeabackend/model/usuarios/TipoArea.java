package pucp.edu.pe.tikea.tikeabackend.model.usuarios;

public enum TipoArea {
    EVENTOS("Eventos","Responsable de supervisar las acitivades relacionadas con los eventos"),
    FINANZAS("Finanzas","Responsable de supervisar las actividades relacionadas con la economia de las operaciones"),
    MARKETING("Marketing","Responsable de supervisar las actividades que involucran la publicidad y derivados"),
    RRHH("rrhh","Responsable de supervisar las actividades del personal asi como los clientes"),
    ADMINISTRADOR("Administrador","Responsable del control maestro del sistema"),;

    private final String descripcion;
    private final String nombre;

    TipoArea(String nombre,String descripcion) {
        this.descripcion = descripcion;
        this.nombre = nombre;
    }

    public String getDescripcion() {return descripcion;}

    public String getNombre() {return nombre;}
}
