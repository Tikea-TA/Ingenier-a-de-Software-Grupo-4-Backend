package pucp.edu.pe.tikea.tikeabackend.DTO.venta;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ComprobanteDePagoResponse {

    private Integer idComprobante;
    private Integer idTransaccion;
    private String numeroComprobante;
    private String tipoComprobante;
    private String serie;
    private LocalDateTime fechaEmision;
    private Double montoTotal;
    private Double montoBruto;
    private Double descuentosAplicados;
    private Double canjesAplicados;
    private Double totalDeReembolsos;
    private Double ingresosNetoEfectivo;
    private String validacionSunat;
    private String estado;
    private LocalDateTime fechaCreacion;
    private Integer activo;
}
