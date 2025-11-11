package pucp.edu.pe.tikea.tikeabackend.repository.infraestructura;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pucp.edu.pe.tikea.tikeabackend.model.infraestructura.Establecimiento;
import pucp.edu.pe.tikea.tikeabackend.model.infraestructura.TipoEstadoLocal;
import pucp.edu.pe.tikea.tikeabackend.model.infraestructura.TipoLocal;
import pucp.edu.pe.tikea.tikeabackend.model.usuarios.Gestor;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstablecimientoRepository extends JpaRepository<Establecimiento, Integer> {
    // Buscar un Establecimiento por nombre (case-insensitive)
    Optional<Establecimiento> findByNombreEstablecimientoIgnoreCase(String nombreEstablecimiento);

    // Buscar Establecimientos por Gestor
    List<Establecimiento> findByGestor(Gestor gestor);

    // Buscar Establecimientos por tipo
    List<Establecimiento> findByTipo(TipoLocal tipo);

    // Buscar Establecimientos por estado
    List<Establecimiento> findByEstado(TipoEstadoLocal estado);

    // Buscar Establecimientos por estado y Gestor
    List<Establecimiento> findByEstadoAndGestor(TipoEstadoLocal estado, Gestor gestor);

    // Buscar Establecimientos activos
    List<Establecimiento> findByActivo(Integer activo);

    // Buscar Establecimientos activos de un Gestor específico
    List<Establecimiento> findByActivoAndGestor(Integer activo, Gestor gestor);

    // Buscar Establecimientos por tipo y activos
    List<Establecimiento> findByTipoAndActivo(TipoLocal tipo, Integer activo);

    // Contar Establecimientos por Gestor
    long countByGestor(Gestor gestor);

    // Contar Establecimientos activos
    long countByActivo(Integer activo);
}
