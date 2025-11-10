package pucp.edu.pe.tikea.tikeabackend.DTO.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pucp.edu.pe.tikea.tikeabackend.model.TipoEstado;
import pucp.edu.pe.tikea.tikeabackend.model.usuarios.TipoEstadoProductor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductorModificacionRequest {
    //Datos heredados de Usuario
    private String nombre;
    private String apellido;
    private String correo;
    private String telefono;
    private TipoEstado tipoEstadoUsuario;
    private String DNI;

    //Datos del propio Productor
    private int idGestor;
    private String razonSocial;
    private String RUC;
    private String direccionFisica;

    private TipoEstadoProductor tipoEstadoProductor;

    private byte[] documentacionFisica;
}
