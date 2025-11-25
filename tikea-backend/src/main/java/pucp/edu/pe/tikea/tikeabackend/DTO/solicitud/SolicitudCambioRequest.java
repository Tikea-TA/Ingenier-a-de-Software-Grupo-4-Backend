package pucp.edu.pe.tikea.tikeabackend.DTO.solicitud;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pucp.edu.pe.tikea.tikeabackend.model.solicitud.TipoOperacionCambio;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SolicitudCambioRequest {

    @NotNull(message = "El ID del cliente es requerido")
    private Integer idCliente;

    @NotNull(message = "El ID de la reserva es requerido")
    private Integer idReserva;

    @NotNull(message = "La cantidad es requerida")
    private Integer cantidad;

    private Boolean disponibilidadValidada;

    private Double diferenciaPrecio;

    @NotNull(message = "El tipo de operación es requerido")
    private TipoOperacionCambio tipoOperacion;
}
