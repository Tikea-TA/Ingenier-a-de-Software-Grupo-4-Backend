package pucp.edu.pe.tikea.tikeabackend.DTO.venta;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MedioDePagoRequest {

    private String tipoPago;
    private String estado;
    private Double comision;
    private String pasarelaIntegracion;
    private Boolean validacionSunat;
}
