// src/main/java/pucp/edu/pe/tikea/tikeabackend/model/TicketEspecifico.java
package pucp.edu.pe.tikea.tikeabackend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.sql.Timestamp;

@Entity
@Table(name = "TicketEspecifico")
@Data
public class TicketEspecifico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idTicketEspecifico")
    private Integer idTicketEspecifico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idEvento")
    private Evento evento;

    @Column(name = "precioCompra")
    private Double precioCompra;

    @Column(name = "descuentoAplicado")
    private Double descuentoAplicado;

    @Column(name = "fechaEmision")
    private Timestamp fechaEmision;
}