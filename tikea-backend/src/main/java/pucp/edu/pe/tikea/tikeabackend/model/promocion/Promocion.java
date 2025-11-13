package pucp.edu.pe.tikea.tikeabackend.model.promocion;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pucp.edu.pe.tikea.tikeabackend.model.infraestructura.Evento;

@Entity
@Table(name = "Promocion")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Promocion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPromocion;
    @Column(name = "nombrePromocion", nullable = false)
    private String nombre;
    private String descripcion;

    @Enumerated(EnumType.STRING) // Para que se guarde como "ACTIVA", "INACTIVA", etc, en lugar de guardarlo como enteros 0,1,2, etc
    @Column(name = "tipoPromocion", nullable = false)
    private TipoPromocion tipo;

    private double valorDescuento;
    private Timestamp fechaInicio;
    private Timestamp fechaFin;
    private int stockDisponible;
    private int stockUtilizado;

    @Enumerated(EnumType.STRING)
    private TipoRestriccionPromocion condicionesCanal;

    private String condicionesSector;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoActividad estado;

    private Timestamp fechaCreacion;
    @Column(name = "fechaUltimaCreacion")
    private Timestamp fechaUltimaModificacion;
    private Integer activo;

    @ManyToOne
    @JoinColumn(name = "idEvento", nullable = false)
    private Evento evento;
}