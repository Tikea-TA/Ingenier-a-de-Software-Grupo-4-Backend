package pucp.edu.pe.tikea.tikeabackend.model;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Promocion")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Promocion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPromocion;
    @Column(name = "nombrePromocion")
    private String nombre;
    private String descripcion;
    @Column(name = "tipoPromocion")
    private TipoPromocion tipo;
    private double valorDescuento;
    private Timestamp fechaInicio;
    private Timestamp fechaFin;
    private int stockDisponible;
    private int stockUtilizado;
    private TipoRestriccionPromocion condicionesCanal;
    private String condicionesSector;
    private EstadoActividad estado;
    private Timestamp fechaCreacion;
    private Timestamp fechaUltimaCreacion;
    private int activo;
}