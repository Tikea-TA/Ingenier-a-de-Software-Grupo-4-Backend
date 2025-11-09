package pucp.edu.pe.tikea.tikeabackend.model;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter

public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nombre;
    private String apellido;
    private String correo;
    private String telefono;
    private String nombreUser;
    private String password;
    private TipoEstado estado;
    private String DNI;
    private LocalDateTime fechaRegistro;
    private LocalDateTime fechaUltimaModificacion;
    private Boolean RequiereCambioPassword;
    private LocalDateTime fechaUltimoAcceso;

    public  boolean estActivo() {
        return  this.estado == TipoEstado.ACTIVO;
    }

}
