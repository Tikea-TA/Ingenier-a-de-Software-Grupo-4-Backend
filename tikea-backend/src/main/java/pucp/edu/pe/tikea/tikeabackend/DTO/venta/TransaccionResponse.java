package pucp.edu.pe.tikea.tikeabackend.DTO.venta;

import lombok.*;
import pucp.edu.pe.tikea.tikeabackend.model.venta.TipoMoneda;
import pucp.edu.pe.tikea.tikeabackend.model.venta.TipoTransferencia;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransaccionResponse {

    private Integer idTransaccion;

    private Integer idMedioDePago;
    private Integer idReserva;

    private String numeroTransaccion;
    private Double monto;

    private TipoTransferencia estado;
    private LocalDateTime fechaTransaccion;

    private String resultado;
    private String ipOrigen;
    private TipoMoneda moneda;

    private Integer activo;
}
