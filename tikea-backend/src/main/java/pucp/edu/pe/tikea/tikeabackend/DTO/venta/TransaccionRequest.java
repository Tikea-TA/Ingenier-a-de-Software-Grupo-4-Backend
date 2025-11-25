package pucp.edu.pe.tikea.tikeabackend.DTO.venta;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pucp.edu.pe.tikea.tikeabackend.model.venta.TipoTransferencia;
import pucp.edu.pe.tikea.tikeabackend.model.venta.TipoMoneda;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransaccionRequest {

    private Integer idMedioDePago;
    private Integer idReserva;

    private String numeroTransaccion;
    private Double monto;

    private TipoTransferencia estado;
    private String resultado;
    private String ipOrigen;
    private TipoMoneda moneda;
}
