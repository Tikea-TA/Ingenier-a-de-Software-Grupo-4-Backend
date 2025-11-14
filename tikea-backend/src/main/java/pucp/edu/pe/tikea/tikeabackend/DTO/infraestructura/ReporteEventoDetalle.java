package pucp.edu.pe.tikea.tikeabackend.DTO.infraestructura;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class ReporteEventoDetalle {

    private String nombreEvento; // Nombre del evento para identificar la fila

    //  Fecha (del evento)
    private LocalDate fechaEvento;

    //  Aforo
    private int aforo;

    //  Tickets Vendidos
    private Long ticketsVendidos;

    // Ganancias Esperadas (tickets x precio)
    private Double gananciasEsperadas;

    // Ganancias Reales (ganancias esperadas - total descuentado)
    private Double gananciasReales;


    public ReporteEventoDetalle(String nombreEvento, LocalDate fechaEvento, int aforo,
                                Long ticketsVendidos, Double gananciasEsperadas, Double gananciasReales) {
        this.nombreEvento = nombreEvento;
        this.fechaEvento = fechaEvento;
        this.aforo = aforo;
        this.ticketsVendidos = ticketsVendidos;
        this.gananciasEsperadas = (gananciasEsperadas == null) ? 0.0 : gananciasEsperadas;
        this.gananciasReales = (gananciasReales == null) ? 0.0 : gananciasReales;
    }
}