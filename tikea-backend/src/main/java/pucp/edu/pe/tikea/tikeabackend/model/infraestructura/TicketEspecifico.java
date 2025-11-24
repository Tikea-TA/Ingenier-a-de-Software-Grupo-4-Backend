package pucp.edu.pe.tikea.tikeabackend.model.infraestructura;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import pucp.edu.pe.tikea.tikeabackend.model.venta.Reserva;
import pucp.edu.pe.tikea.tikeabackend.model.usuarios.Cliente;
import pucp.edu.pe.tikea.tikeabackend.model.infraestructura.Evento;
import pucp.edu.pe.tikea.tikeabackend.model.infraestructura.Zona;
import pucp.edu.pe.tikea.tikeabackend.model.infraestructura.Asiento;

import java.time.LocalDateTime;

@Entity
@Table(name = "TicketEspecifico")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@DynamicInsert
public class TicketEspecifico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idTicketEspecifico")
    private Integer idTicketEspecifico;

    @ManyToOne
    @JoinColumn(name = "idReserva")
    private Reserva reserva;

    @ManyToOne
    @JoinColumn(name = "idCliente", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "idEvento", nullable = false)
    private Evento evento;

    @ManyToOne
    @JoinColumn(name = "idZona", nullable = false)
    private Zona zona;

    @OneToOne
    @JoinColumn(name = "idAsiento", nullable = false)
    private Asiento asiento;

    @Lob
    @Column(name = "codigo", columnDefinition = "LONGBLOB")
    private byte[] codigo;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TipoTicket tipo;

    @Column(name = "precioCompra")
    private Double precioCompra;

    @Column(name = "descuentoAplicado")
    private Double descuentoAplicado;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private TipoEstadoTicket estado;

    @Column(name = "fechaEmision")
    private LocalDateTime fechaEmision;

    @Column(name = "fechaUso")
    private LocalDateTime fechaUso;

    @Column(name = "puntosGanados")
    private Integer puntosGanados;

    @Column(name = "comision")
    private Double comision;

    @Column(name = "activo", insertable = false)
    @Generated(GenerationTime.INSERT)
    @ColumnDefault("1")
    private Integer activo;

    public boolean estActivo() {
        return this.activo == 1;
    }
}