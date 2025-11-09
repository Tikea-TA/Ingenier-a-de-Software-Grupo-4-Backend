package pucp.edu.pe.tikea.tikeabackend.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

public class LoginRequest {
    @Email @NotBlank private String correo;
    @NotBlank private String password;
}
