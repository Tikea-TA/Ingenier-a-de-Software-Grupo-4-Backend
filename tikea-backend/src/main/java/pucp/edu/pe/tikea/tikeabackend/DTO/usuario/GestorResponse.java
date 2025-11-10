package pucp.edu.pe.tikea.tikeabackend.DTO.usuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pucp.edu.pe.tikea.tikeabackend.model.TipoEstado;
import pucp.edu.pe.tikea.tikeabackend.model.usuarios.TipoArea;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GestorResponse {
    //Datos de heredados de Usuario
    private Integer idUsuario;
    private String nombre;
    private String apellido;
    private String correo;
    private String telefono;
    private String nombreUser;
    private String dni;
    private TipoEstado estado;

    //Datos Gestor
    private TipoArea areaGestion;
}
