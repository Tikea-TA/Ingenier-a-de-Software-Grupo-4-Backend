package pucp.edu.pe.tikea.tikeabackend.model;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "Usuario")
@Data
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter

public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="idUsuario")
    private int id;
    private String nombre;
    @Column(name = "apellidos")
    private String apellido;
    private String correo;
    private String telefono;
    @Column(name = "nombre_user")
    private String nombreUser;
    private String password;
    private TipoEstado estado;
    @Column(name = "dni")
    private String DNI;
    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;
    @Column(name = "fecha_ultima_modificacion")
    private LocalDateTime fechaUltimaModificacion;
    @Column(name = "requiere_cambio_password")
    private Boolean RequiereCambioPassword;
    @Column(name = "fecha_ultimo_acceso")
    private LocalDateTime fechaUltimoAcceso;
    @Column(name = "ip_ultimo_acceso")
    private String ipUltimoAcceso;
    public  boolean estActivo() {
        return  this.estado == TipoEstado.ACTIVO;
    }

}
