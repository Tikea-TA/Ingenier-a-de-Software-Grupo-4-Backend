package pucp.edu.pe.tikea.tikeabackend.repository.infraestructura;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pucp.edu.pe.tikea.tikeabackend.DTO.infraestructura.ReporteEventoDetalle;
import pucp.edu.pe.tikea.tikeabackend.model.infraestructura.Evento;
import pucp.edu.pe.tikea.tikeabackend.model.infraestructura.CategoriaEvento;
import pucp.edu.pe.tikea.tikeabackend.model.infraestructura.EstadoEvento;
import pucp.edu.pe.tikea.tikeabackend.model.infraestructura.Establecimiento;
import pucp.edu.pe.tikea.tikeabackend.model.usuarios.Productor;
import pucp.edu.pe.tikea.tikeabackend.model.usuarios.Gestor;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Integer> {

    // Buscar un Evento por nombre (case-insensitive)
    Optional<Evento> findByNombreEventoIgnoreCase(String nombreEvento);

    // Buscar Eventos por Establecimiento
    List<Evento> findByEstablecimiento(Establecimiento establecimiento);

    // Buscar Eventos por Productor
    List<Evento> findByProductor(Productor productor);

    // Buscar Eventos por Gestor
    List<Evento> findByGestor(Gestor gestor);

    // Buscar Eventos por categoría
    List<Evento> findByCategoria(CategoriaEvento categoria);

    // Buscar Eventos por estado
    List<Evento> findByEstado(EstadoEvento estado);

    // Buscar Eventos por fecha
    List<Evento> findByFecha(LocalDate fecha);

    // Buscar Eventos entre fechas
    List<Evento> findByFechaBetween(LocalDate fechaInicio, LocalDate fechaFin);

    // Buscar Eventos activos
    List<Evento> findByActivo(Integer activo);

    // Buscar Eventos activos de un Establecimiento
    List<Evento> findByActivoAndEstablecimiento(Integer activo, Establecimiento establecimiento);

    // Buscar Eventos activos de un Productor
    List<Evento> findByActivoAndProductor(Integer activo, Productor productor);

    // Buscar Eventos activos de un Gestor
    List<Evento> findByActivoAndGestor(Integer activo, Gestor gestor);

    // Buscar Eventos por estado y activos
    List<Evento> findByEstadoAndActivo(EstadoEvento estado, Integer activo);

    // Buscar Eventos por categoría y activos
    List<Evento> findByCategoriaAndActivo(CategoriaEvento categoria, Integer activo);

    // Contar Eventos por Establecimiento
    long countByEstablecimiento(Establecimiento establecimiento);

    // Contar Eventos por Productor
    long countByProductor(Productor productor);

    // Contar Eventos activos
    long countByActivo(Integer activo);





    @Query("SELECT new pucp.edu.pe.tikea.tikeabackend.DTO.infraestructura.ReporteEventoDetalle( " +
            "e.nombreEvento, " +
                "e.fecha, " +
                "e.aforoTotal, " +
                "COUNT(t.idTicketEspecifico), " +
                "COALESCE(SUM(t.precioCompra), 0), " +
                "COALESCE(SUM(t.precioCompra - COALESCE(t.descuentoAplicado, 0.0)), 0)" +
                ") " +
                "FROM Evento e " +
                "LEFT JOIN TicketEspecifico t ON t.evento = e " +
                "WHERE e.fecha BETWEEN :fechaInicio AND :fechaFin " +

                "AND e.productor.idUsuario = :idProductor " +
                // ----------------------------------
                "GROUP BY e.idEvento, e.nombreEvento, e.fecha, e.aforoTotal " +
                "ORDER BY e.fecha")
    List<ReporteEventoDetalle> generarReporteDetalladoPorFechaEvento(
                @Param("fechaInicio") LocalDate fechaInicio,
                @Param("fechaFin") LocalDate fechaFin,
                @Param("idProductor") Integer idProductor // El ID del productor (que es un idUsuario)
    );



}
