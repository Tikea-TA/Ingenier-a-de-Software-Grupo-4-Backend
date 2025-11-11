package pucp.edu.pe.tikea.tikeabackend.DTO;

import java.sql.Timestamp;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pucp.edu.pe.tikea.tikeabackend.model.TipoPromocion;
import pucp.edu.pe.tikea.tikeabackend.model.TipoRestriccionPromocion;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PromocionRegistroRequest {

    @NotBlank
    private String nombre;
    private String descripcion;

    @NotBlank
    private TipoPromocion tipo;

    @Positive
    private double valorDescuento;

    @NotNull
    private Timestamp fechaInicio;

    @NotNull
    private Timestamp fechaFin;

    @PositiveOrZero
    private int stockDisponible;

    @NotBlank
    private TipoRestriccionPromocion condicionesCanal;

    private String condicionesSector;

    @NotNull
    private Integer idEvento; 
}