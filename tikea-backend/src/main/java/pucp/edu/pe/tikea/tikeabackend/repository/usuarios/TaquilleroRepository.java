package pucp.edu.pe.tikea.tikeabackend.repository.usuarios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pucp.edu.pe.tikea.tikeabackend.model.usuarios.Taquillero;
import pucp.edu.pe.tikea.tikeabackend.model.infraestructura.PuntoDeVenta;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaquilleroRepository extends JpaRepository<Taquillero, Integer> {

    // Buscar por Punto de Venta
    List<Taquillero> findByPuntoDeVenta(PuntoDeVenta puntoDeVenta);

    // Buscar por rol
    List<Taquillero> findByRolIgnoreCase(String rol);

    // Buscar taquilleros con locales asignados mayores o iguales
    List<Taquillero> findByLocalesAsignadosGreaterThanEqual(int localesAsignados);

    // Buscar por fechas de asignación
    List<Taquillero> findByFechaInicioAsignacionAfter(LocalDateTime fecha);
    List<Taquillero> findByFechaFinAsignacionBefore(LocalDateTime fecha);

    // Búsqueda combinada por punto de venta y rol
    List<Taquillero> findByPuntoDeVentaAndRolIgnoreCase(PuntoDeVenta puntoDeVenta, String rol);

    // Búsqueda por rango de asignación activa (fecha actual en medio)
    @Query("""
           SELECT t FROM Taquillero t
           WHERE t.fechaInicioAsignacion <= :fechaActual
             AND (t.fechaFinAsignacion IS NULL OR t.fechaFinAsignacion >= :fechaActual)
           """)
    List<Taquillero> findAsignacionVigente(@Param("fechaActual") LocalDateTime fechaActual);

    // Buscar uno por rol y punto de venta
    Optional<Taquillero> findFirstByPuntoDeVentaAndRolIgnoreCase(PuntoDeVenta puntoDeVenta, String rol);
}
