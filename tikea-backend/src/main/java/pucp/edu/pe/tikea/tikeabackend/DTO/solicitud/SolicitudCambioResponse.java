package pucp.edu.pe.tikea.tikeabackend.DTO.solicitud;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pucp.edu.pe.tikea.tikeabackend.model.solicitud.TipoOperacionCambio;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SolicitudCambioResponse {

    private Integer idSolicitud;

    private Integer idCliente;
    private Integer idReserva;

    private Integer cantidad;
    private Boolean disponibilidadValidada;
    private Double diferenciaPrecio;

    private TipoOperacionCambio tipoOperacion;

    private LocalDateTime fecha;

    private Integer activo;
}
