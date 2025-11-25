package pucp.edu.pe.tikea.tikeabackend.DTO.infraestructura;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import pucp.edu.pe.tikea.tikeabackend.model.infraestructura.TipoEstadoTicket;
import pucp.edu.pe.tikea.tikeabackend.model.infraestructura.TipoTicket;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketEspecificoResponse {

    private Integer idTicketEspecifico;

    private Integer idReserva;
    private Integer idCliente;
    private Integer idEvento;
    private Integer idZona;
    private Integer idAsiento;

    private byte[] codigo;

    private TipoTicket tipo;

    private Double precioCompra;
    private Double descuentoAplicado;

    private TipoEstadoTicket estado;

    private LocalDateTime fechaEmision;
    private LocalDateTime fechaUso;

    private Integer puntosGanados;
    private Double comision;

    private Integer activo;
}
