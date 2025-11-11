package pucp.edu.pe.tikea.tikeabackend.DTO;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pucp.edu.pe.tikea.tikeabackend.model.TipoPromocion;
import pucp.edu.pe.tikea.tikeabackend.model.TipoRestriccionPromocion;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromocionModificacionRequest {
    
    private String nombre;
    private String descripcion;
    private TipoPromocion tipo;
    private Double valorDescuento;
    private Timestamp fechaInicio;
    private Timestamp fechaFin;
    private Integer stockDisponible;
    private TipoRestriccionPromocion condicionesCanal;
    private String condicionesSector;
}