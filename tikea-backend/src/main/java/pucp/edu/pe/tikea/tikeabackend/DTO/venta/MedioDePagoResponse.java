package pucp.edu.pe.tikea.tikeabackend.DTO.venta;

import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MedioDePagoResponse {

    private Integer idMedioDePago;
    private String tipoPago;
    private String estado;
    private Double comision;
    private String pasarelaIntegracion;
    private Boolean validacionSunat;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaUltimaCreacion;
    private Integer activo;
}
