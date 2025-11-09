package pucp.edu.pe.tikea.tikeabackend.DTO;

import  lombok.Builder;
import  lombok.Data;
import pucp.edu.pe.tikea.tikeabackend.model.TipoCliente;
import pucp.edu.pe.tikea.tikeabackend.model.TipoEstado;
@Data
@Builder
public class ClienteResponse {
    private Integer idCliente;
    private Integer idUsuario;

    // Usuario
    private String nombre;
    private String apellido;
    private String correo;
    private String telefono;
    private String nombreUser;
    private String dni;
    private TipoEstado estado;

    // Cliente
    private String direccion;
    private int puntosPromociones;
    private TipoCliente tipoCliente;
}
