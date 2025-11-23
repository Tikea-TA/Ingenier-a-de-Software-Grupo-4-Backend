package pucp.edu.pe.tikea.tikeabackend.DTO.Venta;

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

    private  Integer clienteId;
    private  Integer taquilleroId;
    private LocalDateTime fehcaHoraExpiracion;
    private TipoEstado estado;
}
