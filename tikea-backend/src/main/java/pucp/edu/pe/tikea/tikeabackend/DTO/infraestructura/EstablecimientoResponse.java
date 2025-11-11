package pucp.edu.pe.tikea.tikeabackend.DTO.infraestructura;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pucp.edu.pe.tikea.tikeabackend.DTO.usuarios.GestorResponse;
import pucp.edu.pe.tikea.tikeabackend.model.infraestructura.TipoLocal;
import pucp.edu.pe.tikea.tikeabackend.model.infraestructura.TipoEstadoLocal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstablecimientoResponse {

    private Integer idEstablecimiento;
    private GestorResponse gestor;
    private String nombreEstablecimiento;
    private String direccionEstablecimiento;
    private TipoLocal tipo;
    private int capacidadMaxima;
    private TipoEstadoLocal estado;
    private LocalDateTime fechaVerificacion;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaUltimaModificacion;
    private Integer activo;
}
