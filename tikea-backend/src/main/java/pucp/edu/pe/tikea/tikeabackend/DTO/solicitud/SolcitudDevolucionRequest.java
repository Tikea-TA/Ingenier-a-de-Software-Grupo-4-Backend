package pucp.edu.pe.tikea.tikeabackend.DTO.solicitud;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SolcitudDevolucionRequest {

    @NotNull(message = "El ID del cliente es requerido")
    private Integer idCliente;

    @NotNull(message = "El ID del ticket específico es requerido")
    private Integer idTicketEspecifico;

    @NotBlank(message = "El motivo es requerido")
    private String motivo;

    @NotNull(message = "El monto solicitado es requerido")
    private Double montoSolicitado;

    private Boolean politicaDevolucion;

    private Boolean reincorporacionStock;
}
