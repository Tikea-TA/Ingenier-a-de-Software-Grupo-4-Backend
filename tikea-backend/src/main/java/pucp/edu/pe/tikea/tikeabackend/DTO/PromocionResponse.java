package pucp.edu.pe.tikea.tikeabackend.DTO;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pucp.edu.pe.tikea.tikeabackend.model.EstadoActividad;
import pucp.edu.pe.tikea.tikeabackend.model.TipoPromocion;
import pucp.edu.pe.tikea.tikeabackend.model.TipoRestriccionPromocion;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromocionResponse {

    private Integer idPromocion;
    private String nombre;
    private String descripcion;
    private TipoPromocion tipo;
    private double valorDescuento;
    private Timestamp fechaInicio;
    private Timestamp fechaFin;
    private int stockDisponible;
    private int stockUtilizado;
    private TipoRestriccionPromocion condicionesCanal;
    private String condicionesSector;
    private EstadoActividad estado;
    private Timestamp fechaCreacion;
    private Timestamp fechaUltimaModificacion;
    private Integer idEvento;
}