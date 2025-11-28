package pucp.edu.pe.tikea.tikeabackend.DTO.usuarios;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaquilleroRequest {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "Los apellidos son obligatorios")
    private String apellidos;

    @Email(message = "El correo debe ser válido")
    @NotBlank(message = "El correo es obligatorio")
    private String correo;

    private String telefono;

    @NotBlank(message = "El nombre de usuario es obligatorio")
    private String nombreUsuario;

    private String dni;

    private String password;

    // Campos específicos de Taquillero
    private String rol;
    private Integer puntoDeVentaId;
    private Integer localesAsignados;
    private LocalDateTime fechaInicioAsignacion;
    private LocalDateTime fechaFinAsignacion;
}
