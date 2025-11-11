package pucp.edu.pe.tikea.tikeabackend.DTO.infraestructura;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pucp.edu.pe.tikea.tikeabackend.model.infraestructura.TipoLocal;
import pucp.edu.pe.tikea.tikeabackend.model.infraestructura.TipoEstadoLocal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstablecimientoRegistroRequest {

    @NotNull
    private Integer idGestor;

    @NotBlank
    private String nombreEstablecimiento;

    @NotBlank
    private String direccionEstablecimiento;

    @NotNull
    private TipoLocal tipo;

    @Positive
    private int capacidadMaxima;

    @NotNull
    private TipoEstadoLocal estado;

    private byte[] documentacionAdjunta;
}
