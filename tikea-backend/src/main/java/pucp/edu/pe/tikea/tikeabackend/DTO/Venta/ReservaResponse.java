package pucp.edu.pe.tikea.tikeabackend.DTO.Venta;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import pucp.edu.pe.tikea.tikeabackend.model.venta.TipoEstado;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservaResponse {

    private Integer idReserva;
    private Integer idCliente;
    private Integer idTaquillero;
    private LocalDateTime fechaHoraCreacion;
    private LocalDateTime fechaHoraExpiracion;
    private TipoEstado estado;
    private Integer activo;
}
