package pucp.edu.pe.tikea.tikeabackend.DTO.infraestructura;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import pucp.edu.pe.tikea.tikeabackend.model.infraestructura.TipoEstadoAproDes;
import pucp.edu.pe.tikea.tikeabackend.model.infraestructura.TipoPuntoVenta;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PuntoDeVentaResponse {

    private Integer idPuntoDeVenta;
    private String nombrePuntoVenta;
    private String direccion;
    private TipoEstadoAproDes estado;
    private TipoPuntoVenta tipo;
    private LocalDateTime fechaInicioOperacion;
    private LocalDateTime fechaFinOperacion;
    private LocalDateTime fechaCreacion;
    private Integer activo;
}
