package pucp.edu.pe.tikea.tikeabackend.DTO.infraestructura;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ReporteRequestEvento {
    // El frontend enviará las fechas en este formato (ej: "2025-11-01")
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Integer idProductor;
}
