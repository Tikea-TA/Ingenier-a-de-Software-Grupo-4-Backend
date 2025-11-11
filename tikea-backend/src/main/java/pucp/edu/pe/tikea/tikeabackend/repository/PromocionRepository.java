package pucp.edu.pe.tikea.tikeabackend.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pucp.edu.pe.tikea.tikeabackend.model.EstadoActividad;
import pucp.edu.pe.tikea.tikeabackend.model.Promocion;

public interface PromocionRepository extends JpaRepository<Promocion, Integer> {
    // Consultas ligadas al evento
    List<Promocion> findByEventoIdEvento(Integer idEvento);
    List<Promocion> findByEventoIdEventoAndEstado(Integer idEvento, EstadoActividad estado);
    List<Promocion> findByEventoIdEventoAndFechaInicioBeforeAndFechaFinAfter(Integer idEvento, Timestamp inicio, Timestamp fin);

    // Consultas globales
    List<Promocion> findByEstado(EstadoActividad estado);
    List<Promocion> findByStockDisponibleGreaterThan(int cantidad);
}
