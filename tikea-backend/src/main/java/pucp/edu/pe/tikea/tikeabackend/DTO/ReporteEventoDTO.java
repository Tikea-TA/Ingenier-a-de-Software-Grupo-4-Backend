// src/main/java/pucp/edu/pe/tikea/tikeabackend/dto/ReporteEventoDTO.java
package pucp.edu.pe.tikea.tikeabackend.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReporteEventoDTO {

    private Integer idEvento;
    private String nombreEvento;
    private Long totalTicketsVendidos; // El conteo de tickets
    private Double totalIngresos; // La suma de los precios
    // (Opcional) private List<String> promocionesActivas;

    // Constructor especial para la consulta JPQL (Paso 3)
    public ReporteEventoDTO(Integer idEvento, String nombreEvento, Long totalTicketsVendidos, Double totalIngresos) {
        this.idEvento = idEvento;
        this.nombreEvento = nombreEvento;
        this.totalTicketsVendidos = totalTicketsVendidos;
        // Manejar el caso de que no haya ventas (SUM(null) es null)
        this.totalIngresos = (totalIngresos == null) ? 0.0 : totalIngresos;
    }
}