package pucp.edu.pe.tikea.tikeabackend.DTO.usuarios;

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
    // Datos heredados de Usuario
    @NotBlank(message = "El nombre es requerido")
    private String nombre;

    @NotBlank(message = "El apellido es requerido")
    private String apellido;

    @Email(message = "El email debe ser válido")
    @NotBlank(message = "El email es requerido")
    private String email;

    @NotBlank(message = "El teléfono es requerido")
    private String telefono;

    @NotBlank(message = "El nombre de usuario es requerido")
    private String nombreUsuario;

    @NotBlank(message = "La contraseña es requerida")
    private String password;

    @NotBlank(message = "El DNI es requerido")
    private String DNI;

    // Datos de Productor
    @NotBlank(message = "El ID del Gestor es requerido")
    private Integer idGestor;

    @NotBlank(message = "La razón social es requerida")
    private String razonSocial;

    @NotBlank(message = "El RUC es requerido")
    private String RUC;

    @NotBlank(message = "La dirección física es requerida")
    private String direccionFisica;

    @Builder.Default
    private TipoEstadoProductor tipoEstadoProductor = TipoEstadoProductor.PENDIENTE_VALIDACION;

    private byte[] documentacionFisica;
}
