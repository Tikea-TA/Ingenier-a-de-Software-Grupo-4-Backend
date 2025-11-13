package pucp.edu.pe.tikea.tikeabackend.DTO.usuarios.cliente;

import lombok.AllArgsConstructor;
import  lombok.Builder;
import  lombok.Data;
import lombok.NoArgsConstructor;
import pucp.edu.pe.tikea.tikeabackend.model.usuarios.TipoCliente;
import pucp.edu.pe.tikea.tikeabackend.model.usuarios.TipoEstado;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteResponse {
    //ID que hereda de Usuario, asi que es el mismo
    private Integer idCliente;

    // Usuario
    private String nombre;
    private String apellidos;
    private String correo;
    private String telefono;
    private String nombreUsuario;
    private String DNI;
    private TipoEstado estado;

    // cliente
    private String direccion;
    private int puntosPromociones;
    private TipoCliente tipoCliente;
}
