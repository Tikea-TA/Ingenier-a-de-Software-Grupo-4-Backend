package pucp.edu.pe.tikea.tikeabackend.DTO;

import lombok.AllArgsConstructor;
import  lombok.Data ;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
@AllArgsConstructor
public class ClienteModficiacionRequest {
    private String correo;
    private String password;
    private String nombreUser;
    private String direccion;
    private Integer puntosPromociones;
    private String tipoCliente;
    private String telefono;
}
