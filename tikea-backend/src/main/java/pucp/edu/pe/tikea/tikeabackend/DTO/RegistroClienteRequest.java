package pucp.edu.pe.tikea.tikeabackend.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pucp.edu.pe.tikea.tikeabackend.model.TipoPromocion;
import pucp.edu.pe.tikea.tikeabackend.model.TipoRestriccionPromocion;

import java.sql.Timestamp;

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

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PromocionModificacionRequest {

        private String nombre;
        private String descripcion;
        private TipoPromocion tipo;
        private Double valorDescuento;
        private Timestamp fechaInicio;
        private Timestamp fechaFin;
        private Integer stockDisponible;
        private TipoRestriccionPromocion condicionesCanal;
        private String condicionesSector;
    }
}
