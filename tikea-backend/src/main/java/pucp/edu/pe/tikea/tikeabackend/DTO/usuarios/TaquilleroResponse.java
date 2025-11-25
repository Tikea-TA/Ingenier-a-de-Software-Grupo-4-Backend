package pucp.edu.pe.tikea.tikeabackend.DTO.usuarios;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaquilleroResponse {

    private Integer idTaquillero;

    // Datos heredados de Usuario
    private String nombre;
    private String apellidos;
    private String email;
    private String telefono;
    private String nombreUsuario;
    private String DNI;

    // Datos propios de Taquillero
    private Integer idPuntoDeVenta;

    private String rol;

    private Integer localesAsignados;

    private LocalDateTime fechaInicioAsignacion;
    private LocalDateTime fechaFinAsignacion;
}
