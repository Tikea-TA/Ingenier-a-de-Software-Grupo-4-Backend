package pucp.edu.pe.tikea.tikeabackend.DTO.infraestructura;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pucp.edu.pe.tikea.tikeabackend.DTO.usuarios.ProductorResponse;
import pucp.edu.pe.tikea.tikeabackend.DTO.usuarios.GestorResponse;
import pucp.edu.pe.tikea.tikeabackend.model.infraestructura.CategoriaEvento;
import pucp.edu.pe.tikea.tikeabackend.model.infraestructura.EstadoEvento;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventoResponse {

    private Integer idEvento;
    private EstablecimientoResponse establecimiento;
    private ProductorResponse productor;
    private GestorResponse gestor;
    private String nombreEvento;
    private CategoriaEvento categoria;
    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private EstadoEvento estado;
    private int aforoTotal;
    private Integer activo;
    private LocalDateTime fechaVerificacion;
}
