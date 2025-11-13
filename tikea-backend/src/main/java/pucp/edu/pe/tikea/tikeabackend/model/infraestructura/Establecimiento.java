package pucp.edu.pe.tikea.tikeabackend.model.infraestructura;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Generated;
import pucp.edu.pe.tikea.tikeabackend.model.usuarios.Gestor;

import java.time.LocalDateTime;

@Entity
@Table(name = "Establecimiento")
@Data
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@DynamicInsert
public class Establecimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="idEstablecimiento")
    private Integer idEstablecimiento;

    @ManyToOne
    @JoinColumn(name = "idGestor")
    private Gestor gestor;

    @Column(name ="nombreEstablecimiento")
    private String nombreEstablecimiento;

    @Column(name ="direccionEstablecimiento")
    private String direccionEstablecimiento;

    @Enumerated(EnumType.STRING)
    @Column(name ="tipo")
    private TipoLocal tipo;

    @Column(name ="capacidadMaxima")
    private int capacidadMaxima;

    @Enumerated(EnumType.STRING)
    @Column(name ="estado")
    private TipoEstadoLocal estado;

    @Column(name ="documentacionAdjunta")
    @Lob
    private byte[] documentacionAdjunta;

    @Column(name ="fechaVerificacion")
    private LocalDateTime fechaVerificacion;

    @CreationTimestamp
    @Column(name ="fechaCreacion")
    private LocalDateTime fechaCreacion;

    @Column(name ="fechaUltimaModificacion")
    private LocalDateTime fechaUltimaModificacion;

    @Column(name ="activo", insertable = false)
    @Generated(GenerationTime.INSERT)
    @ColumnDefault("1")
    private Integer activo;

    public boolean estEstado(){
        return this.estado == TipoEstadoLocal.PENDIENTE_VALIDACION;
    }
}
