package pucp.edu.pe.tikea.tikeabackend.repository.infraestructura;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pucp.edu.pe.tikea.tikeabackend.model.infraestructura.PuntoDeVenta;
import pucp.edu.pe.tikea.tikeabackend.model.infraestructura.TipoEstadoAproDes;
import pucp.edu.pe.tikea.tikeabackend.model.infraestructura.TipoPuntoVenta;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PuntoDeVentaRepository extends JpaRepository<PuntoDeVenta, Integer> {

    // Buscar por nombre (case insensitive)
    Optional<PuntoDeVenta> findByNombrePuntoVentaIgnoreCase(String nombrePuntoVenta);

    // Buscar por tipo
    List<PuntoDeVenta> findByTipo(TipoPuntoVenta tipo);

    // Buscar por estado (APROBADO, DESAPROBADO, PENDIENTE...)
    List<PuntoDeVenta> findByEstado(TipoEstadoAproDes estado);

    // Buscar activos
    @Query("SELECT p FROM PuntoDeVenta p WHERE p.activo = 1")
    List<PuntoDeVenta> findAllActive();

    // Buscar activos por tipo
    @Query("SELECT p FROM PuntoDeVenta p WHERE p.activo = 1 AND p.tipo = :tipo")
    List<PuntoDeVenta> findActiveByTipo(@Param("tipo") TipoPuntoVenta tipo);

    // Buscar activos por estado
    @Query("SELECT p FROM PuntoDeVenta p WHERE p.activo = 1 AND p.estado = :estado")
    List<PuntoDeVenta> findActiveByEstado(@Param("estado") TipoEstadoAproDes estado);

    // Buscar puntos de venta cuya operación empezó después de una fecha
    List<PuntoDeVenta> findByFechaInicioOperacionAfter(LocalDateTime fecha);

    // Buscar puntos de venta dentro de un rango de fechas de inicio
    List<PuntoDeVenta> findByFechaInicioOperacionBetween(LocalDateTime inicio, LocalDateTime fin);

    // Buscar puntos de venta activos dentro de un rango de operación
    @Query("""
           SELECT p FROM PuntoDeVenta p 
           WHERE p.activo = 1 
           AND p.fechaInicioOperacion >= :inicio 
           AND p.fechaFinOperacion <= :fin
           """)
    List<PuntoDeVenta> findActiveByRangoOperacion(
            @Param("inicio") LocalDateTime inicio,
            @Param("fin") LocalDateTime fin
    );
}