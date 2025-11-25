package pucp.edu.pe.tikea.tikeabackend.DTO.venta;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pucp.edu.pe.tikea.tikeabackend.model.infraestructura.TipoEstadoTicket;
import pucp.edu.pe.tikea.tikeabackend.model.infraestructura.TipoTicket;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketResponse {
    private Integer idTicketEspecifico;
    private Integer reservaId;
    private Integer clienteId;
    private String nombreCliente;
    private Integer eventoId;
    private String nombreEvento;
    private Integer zonaId;
    private String nombreZona;
    private Integer asientoId;
    private String codigoAsiento;
    private TipoTicket tipo;
    private Double precioCompra;
    private Double descuentoAplicado;
    private TipoEstadoTicket estado;
    private LocalDateTime fechaEmision;
    private LocalDateTime fechaUso;
    private Integer puntosGanados;
    private Double comision;
    private String codigoBase64;
    private Integer activo;
}
