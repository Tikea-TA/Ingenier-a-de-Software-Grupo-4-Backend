package pucp.edu.pe.tikea.tikeabackend.DTO.usuarios;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaquilleroRequest {

    @NotBlank(message = "El nombre es requerido")
    private String nombre;

    @NotBlank(message = "El apellido es requerido")
    private String apellidos;

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

    // Campos propios de Taquillero
    @NotNull(message = "El ID del punto de venta es requerido")
    private Integer idPuntoDeVenta;

    @NotBlank(message = "El rol es requerido")
    private String rol;

    @NotNull(message = "El número de locales asignados es requerido")
    private Integer localesAsignados;
}
