package pucp.edu.pe.tikea.tikeabackend.DTO.usuarios;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pucp.edu.pe.tikea.tikeabackend.model.TipoEstado;
import pucp.edu.pe.tikea.tikeabackend.model.usuarios.TipoEstadoProductor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductorResponse {
    // Datos heredados de Usuario
    private Integer idProductor;
    private String nombre;
    private String apellidos;
    private String correo;
    private String telefono;
    private String nombreUsuario;
    private String DNI;
    private TipoEstado estado;

    // Datos del Productor
    private GestorResponse gestor;
    private String razonSocial;
    private String RUC;
    private String direccionFisica;
    private TipoEstadoProductor tipoEstadoProductor;
    private int localesRegistrados;
    private int eventosRegistrados;
    private int promocionesCreadas;
    private LocalDateTime fechaVerificacion;
}