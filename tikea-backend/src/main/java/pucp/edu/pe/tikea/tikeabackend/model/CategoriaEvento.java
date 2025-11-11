package pucp.edu.pe.tikea.tikeabackend.model;

public enum CategoriaEvento {

    CONCIERTO("Concierto", "El evento pertenece a la categoría de conciertos"),
    DEPORTES("Deportes", "El evento pertenece a la categoría de deportes"),
    TEATRO("Teatro", "El evento pertenece a la categoría de teatro"),
    ENTRETENIMIENTO("Entretenimiento", "El evento pertenece a la categoría de entretenimiento"),
    OTROS("Otros", "El evento pertenece a una categoría distinta");

    private final String nombre;
    private final String descripcion;

    CategoriaEvento(String nombre, String descripcion) {
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
