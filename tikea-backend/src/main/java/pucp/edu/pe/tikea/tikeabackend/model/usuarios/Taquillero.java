package pucp.edu.pe.tikea.tikeabackend.model.usuarios;

import jakarta.persistence.*;
import lombok.*;
import pucp.edu.pe.tikea.tikeabackend.model.infraestructura.PuntoDeVenta;

import java.time.LocalDateTime;

@Entity
@Table(name = "Taquillero")
@Data
@AllArgsConstructor
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "idTaquillero")
@Getter
@Setter
public class Taquillero extends Usuario {

    @ManyToOne
    @JoinColumn(name = "idPuntoDeVenta")
    private PuntoDeVenta puntoDeVenta;

    @Column(name = "rol")
    private String rol;

    @Column(name = "localesAsignados")
    private int localesAsignados;

    @Column(name = "fechaInicioAsignacion")
    private LocalDateTime fechaInicioAsignacion;

    @Column(name = "fechaFinAsignacion")
    private LocalDateTime fechaFinAsignacion;
}
