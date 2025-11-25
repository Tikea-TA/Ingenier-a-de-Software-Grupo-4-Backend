package pucp.edu.pe.tikea.tikeabackend.DTO.infraestructura;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import pucp.edu.pe.tikea.tikeabackend.model.infraestructura.TipoEstadoAsiento;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AsientoResponse {

    private Integer idAsiento;
    private Integer idZona;
    private String columnaAsiento;
    private String filaAsiento;
    private TipoEstadoAsiento estado;
    private Double costo;
    private Integer activo;
}
