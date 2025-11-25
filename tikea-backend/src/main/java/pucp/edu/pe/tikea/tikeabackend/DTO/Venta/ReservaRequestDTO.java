package pucp.edu.pe.tikea.tikeabackend.DTO.venta;

import  lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pucp.edu.pe.tikea.tikeabackend.model.venta.TipoEstado;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservaRequestDTO {

    private  Integer idCliente;
    private  Integer idTaquillero;
    private LocalDateTime fehcaHoraExpiracion;
    private TipoEstado estado;
}
