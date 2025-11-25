package pucp.edu.pe.tikea.tikeabackend.model.solicitud;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import pucp.edu.pe.tikea.tikeabackend.model.usuarios.Cliente;
import pucp.edu.pe.tikea.tikeabackend.model.infraestructura.TicketEspecifico;

@Entity
@Table(name = "SolicitudDevolucion")
@Data
@AllArgsConstructor
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "idSolicitudDevolucion")
@Getter
@Setter
@DynamicInsert
public class SolicitudDevolucion extends Solicitud {

    @ManyToOne
    @JoinColumn(name = "idTicketEspecifico", nullable = false)
    private TicketEspecifico ticketEspecifico;

    @Column(name = "motivo")
    private String motivo;

    @Column(name = "montoSolicitado")
    private Double montoSolicitado;

    @Column(name = "politicaDevolucion")
    private Boolean politicaDevolucion;

    @Column(name = "reincorporacionStock")
    private Boolean reincorporacionStock;

    @Column(name = "activo", insertable = false)
    @Generated(GenerationTime.INSERT)
    @ColumnDefault("1")
    private Integer activo;

    @Override
    public boolean estActivo() {
        return this.activo == 1;
    }
}
