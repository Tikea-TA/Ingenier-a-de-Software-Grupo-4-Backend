package pucp.edu.pe.tikea.tikeabackend.model.solicitud;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import pucp.edu.pe.tikea.tikeabackend.model.usuarios.Cliente;
import pucp.edu.pe.tikea.tikeabackend.model.venta.Reserva;

import java.time.LocalDateTime;

@Entity
@Table(name = "SolicitudCambio")
@Data
@AllArgsConstructor
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "idSolicitudCambio")
@Getter
@Setter
@DynamicInsert
public class SolicitudCambio extends Solicitud {

    @ManyToOne
    @JoinColumn(name = "idReserva", nullable = false)
    private Reserva reserva;

    @Column(name = "fecha", insertable = false)
    @Generated(GenerationTime.INSERT)
    @ColumnDefault("CURRENT_TIMESTAMP")
    private LocalDateTime fecha;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "disponibilidadValidada")
    private Boolean disponibilidadValidada;

    @Column(name = "diferenciaPrecio")
    private Double diferenciaPrecio;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipoOperacion")
    private TipoOperacionCambio tipoOperacion;

    @Column(name = "activo", insertable = false)
    @Generated(GenerationTime.INSERT)
    @ColumnDefault("1")
    private Integer activo;

    @Override
    public boolean estActivo() {
        return this.activo == 1;
    }
}
