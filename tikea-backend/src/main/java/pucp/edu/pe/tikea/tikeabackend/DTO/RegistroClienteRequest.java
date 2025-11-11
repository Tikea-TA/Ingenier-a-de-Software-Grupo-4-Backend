package pucp.edu.pe.tikea.tikeabackend.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistroClienteRequest {
    // Atributos usuarios
    @NotBlank private String nombre;
    @NotBlank private String apellidos;
    @Email @NotBlank private String correo;
    @NotBlank private String password;
    private String telefono;
    private String nombreUser;
    private String dni;

    //cliente
    private String direccion;
    @Builder.Default
    private Integer puntosPromociones = 0;
    private String tipoCliente;
}
