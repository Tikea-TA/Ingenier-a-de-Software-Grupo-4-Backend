package pucp.edu.pe.tikea.tikeabackend.DTO.venta;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pucp.edu.pe.tikea.tikeabackend.model.infraestructura.TipoEstadoTicket;
import pucp.edu.pe.tikea.tikeabackend.model.infraestructura.TipoTicket;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketRequest {
    private Integer reservaId;
    private Integer clienteId;
    private Integer eventoId;
    private Integer zonaId;
    private Integer asientoId;
    private TipoTicket tipo;
    private Double precioCompra;
    private Double descuentoAplicado;
    private TipoEstadoTicket estado;
    private Integer puntosGanados;
    private Double comision;

    private byte[] codigo;
}
