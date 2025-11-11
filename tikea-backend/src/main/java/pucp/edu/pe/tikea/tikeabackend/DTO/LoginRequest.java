package pucp.edu.pe.tikea.tikeabackend.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @Email(message = "El email debe ser válido")
    @NotBlank(message = "El email es requerido")
    private String correo;

    @NotBlank(message = "La contraseña es requerida")
    private String password;
}
