package pucp.edu.pe.tikea.tikeabackend.DTO.solicitud;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SolicitudDevolucionResponse {

    private Integer idSolicitud;

    private Integer idCliente;
    private Integer idTicketEspecifico;

    private String motivo;
    private Double montoSolicitado;

    private Boolean politicaDevolucion;
    private Boolean reincorporacionStock;

    private Integer activo;
}
