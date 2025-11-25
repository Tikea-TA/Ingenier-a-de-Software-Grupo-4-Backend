package pucp.edu.pe.tikea.tikeabackend.DTO.infraestructura;

import  lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pucp.edu.pe.tikea.tikeabackend.model.infraestructura.TipoZona;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZonaRequest {

    private Integer idEstablecimiento;
    private String nombreZona;
    private int aforo;
    private TipoZona tipo;
    private String distribucionAsientos;
    private String restriccionesSeguridad;
    private int asientosDisponibles;
    private int asientosOcupados;
    private Double precio;
}
