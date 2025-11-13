package pucp.edu.pe.tikea.tikeabackend.model.usuarios;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;

@Entity
@Table(name = "Productor")
@Data
@AllArgsConstructor
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "idProductor")
@Getter
@Setter
@DynamicInsert
public class Productor extends Usuario {

    @ManyToOne
    @JoinColumn(name = "idGestor")
    private Gestor gestor;

    @Column(name = "razonSocial")
    private String razonSocial;

    @Column(name = "RUC")
    private String RUC;

    @Column(name = "direccionFisica")
    private String direccionFisica;

    @Enumerated(EnumType.STRING)
    @Column(name = "estadoProductor")
    private TipoEstadoProductor tipoEstadoProductor;

    @Column(name = "documentacionAdjunta")
    @Lob
    private byte[] documentacionFisica;

    @Column(name = "localesRegistrados")
    private int localesRegistrados;

    @Column(name = "eventosRegistrados")
    private int eventosRegistrados;

    @Column(name = "promocionesCreadas")
    private int promocionesCreadas;

    @CreationTimestamp
    @Column(name = "fechaVerificacion")
    private LocalDateTime fechaVerificacion;
}
