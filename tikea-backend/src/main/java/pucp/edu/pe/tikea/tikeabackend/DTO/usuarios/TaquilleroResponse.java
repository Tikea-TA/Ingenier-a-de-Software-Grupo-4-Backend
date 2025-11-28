package pucp.edu.pe.tikea.tikeabackend.DTO.usuarios;

import lombok.Data;
import pucp.edu.pe.tikea.tikeabackend.model.usuarios.TipoEstado;

import java.time.LocalDateTime;

@Data
public class TaquilleroResponse {
    private Integer idTaquillero;
    private String nombre;
    private String apellidos;
    private String correo;
    private String telefono;
    private String nombreUsuario;
    private String DNI;
    private TipoEstado estado;

    // Campos específicos de Taquillero
    private String rol;
    private Integer puntoDeVentaId;
    private Integer localesAsignados;
    private LocalDateTime fechaInicioAsignacion;
    private LocalDateTime fechaFinAsignacion;
}
