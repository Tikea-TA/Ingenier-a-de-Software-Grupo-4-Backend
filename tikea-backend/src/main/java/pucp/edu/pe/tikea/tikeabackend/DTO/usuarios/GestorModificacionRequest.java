package pucp.edu.pe.tikea.tikeabackend.DTO.usuarios;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pucp.edu.pe.tikea.tikeabackend.model.usuarios.TipoArea;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GestorModificacionRequest {
    private String correo;
    private String password;
    private String nombreUsuario;
    private String telefono;
    private TipoArea tipoArea;
}
