package pucp.edu.pe.tikea.tikeabackend.model.usuarios;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

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
    private Integer idUsuario;

    private String nombre;

    @Column(name = "apellidos")
    private String apellidos;

    private String correo;
    private String telefono;

    @Column(name = "nombreUsuario")
    private String nombreUser;

    @Column(name = "password")
    private String password;

    private TipoEstado estado;

    @Column(name = "DNI")
    private String DNI;

    @Column(name = "fechaRegistro")
    @CreationTimestamp
    private LocalDateTime fechaRegistro;

    @Column(name = "fechaUltimaModificacion")
    private LocalDateTime fechaUltimaModificacion;

    @Column(name = "requiereCambioContraseña")
    private Boolean RequiereCambioPassword;

    @Column(name = "fechaUltimoAcceso")
    private LocalDateTime fechaUltimoAcceso;

    @Column(name = "ipUltimoAcceso")
    private String ipUltimoAcceso;

    @Column(name = "activo", insertable = false)
    @Generated(GenerationTime.INSERT)
    private Integer activo;

    public  boolean estActivo() {
        return  this.estado == TipoEstado.ACTIVO;
    }

}
