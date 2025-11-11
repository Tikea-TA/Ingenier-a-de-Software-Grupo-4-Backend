package pucp.edu.pe.tikea.tikeabackend.DTO.usuarios;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pucp.edu.pe.tikea.tikeabackend.model.usuarios.TipoArea;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GestorRegistroRequest {
    @NotBlank
    private String nombre;
    @NotBlank
    private String apellidos;
    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String telefono;
    @NotBlank
    private String nombreUsuario;
    @NotBlank
    private String dni;

    @NotNull
    private TipoArea tipoArea;
}
