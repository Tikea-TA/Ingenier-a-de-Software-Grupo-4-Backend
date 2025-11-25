package pucp.edu.pe.tikea.tikeabackend.DTO.infraestructura;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import pucp.edu.pe.tikea.tikeabackend.model.infraestructura.TipoEstadoTicket;
import pucp.edu.pe.tikea.tikeabackend.model.infraestructura.TipoTicket;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketEspecificoRequest {

    @NotNull(message = "El ID de la reserva es requerido")
    private Integer idReserva;
    @NotNull(message = "El ID del cliente es requerido")
    private Integer idCliente;
    @NotNull(message = "El ID del evento es requerido")
    private Integer idEvento;
    @NotNull(message = "El ID de la zona es requerido")
    private Integer idZona;
    @NotNull(message = "El ID del asiento es requerido")
    private Integer idAsiento;

    private byte[] codigo;

    @NotNull(message = "El tipo de ticket es requerido")
    private TipoTicket tipo;

    @NotNull(message = "El precio de compra es requerido")
    private Double precioCompra;
    private Double descuentoAplicado;

    @NotNull(message = "El estado del ticket es requerido")
    private TipoEstadoTicket estado;

    private LocalDateTime fechaEmision;
    private LocalDateTime fechaUso;

    private Integer puntosGanados;
    private Double comision;
}
