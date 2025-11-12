package pucp.edu.pe.tikea.tikeabackend.DTO.Cliente;

import lombok.AllArgsConstructor;
import  lombok.Data ;
import lombok.NoArgsConstructor;
@Data @NoArgsConstructor
@AllArgsConstructor
public class ClienteModficiacionRequest {

    private String correo;
    private String password;
    private String nombreUser;
    private String telefono;

    private String direccion;
    private Integer puntosPromocionales;
    private String tipoCliente;

}
