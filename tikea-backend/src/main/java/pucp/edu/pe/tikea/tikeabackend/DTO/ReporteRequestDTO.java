// src/main/java/pucp/edu/pe/tikea/tikeabackend/dto/ReporteRequestDTO.java
package pucp.edu.pe.tikea.tikeabackend.DTO;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ReporteRequestDTO {
    // El frontend enviará las fechas en este formato (ej: "2025-11-01")
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
}