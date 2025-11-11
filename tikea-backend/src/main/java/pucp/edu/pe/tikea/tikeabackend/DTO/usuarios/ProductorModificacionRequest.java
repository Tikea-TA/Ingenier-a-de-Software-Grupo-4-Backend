package pucp.edu.pe.tikea.tikeabackend.DTO.usuarios;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pucp.edu.pe.tikea.tikeabackend.model.TipoEstado;
import pucp.edu.pe.tikea.tikeabackend.model.usuarios.TipoEstadoProductor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductorModificacionRequest {
    // Datos que se pueden modificar de Usuario
    private String correo;
    private String telefono;
    private String nombreUsuario;
    private String password;

    // Datos del Productor que se pueden modificar
    private String razonSocial;
    private String RUC;
    private String direccionFisica;
    private TipoEstadoProductor tipoEstadoProductor;
    private byte[] documentacionFisica;
}