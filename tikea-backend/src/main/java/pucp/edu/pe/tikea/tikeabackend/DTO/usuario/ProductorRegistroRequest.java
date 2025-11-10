package pucp.edu.pe.tikea.tikeabackend.DTO.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pucp.edu.pe.tikea.tikeabackend.model.usuarios.TipoEstadoProductor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductorRegistroRequest {
    //Datos hereados de Usuario
    @NotBlank
    private String nombre;
    @NotBlank
    private String apellido;
    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String telefono;
    @NotBlank
    private String nombreUsuario;
    @NotBlank
    private String password;
    @NotBlank
    private String DNI;

    //Datos de Productor
    @NotBlank
    private int idGestor;
    @NotBlank
    private String razonSocial;
    @NotBlank
    private String RUC;
    @NotBlank
    private String direccionFisica;
    @NotBlank
    @Builder.Default
    private TipoEstadoProductor tipoEstadoProductor = TipoEstadoProductor.PENDIENTE_VALIDACION;

    private byte[] documentacionFisica;
}
