package pucp.edu.pe.tikea.tikeabackend.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

public class RegistroClienteRequest {
    // Atributos usuario
    @NotBlank private String nombre;
    @NotBlank private String apellido;
    @Email @NotBlank private String correo;
    @NotBlank private String password;
    private String telefono;
    private String nombreUser;
    private String dni;

    //cliente
    private String direccion;
    private Integer puntosPromociones = 0;
    private String tipoCliente;
}
