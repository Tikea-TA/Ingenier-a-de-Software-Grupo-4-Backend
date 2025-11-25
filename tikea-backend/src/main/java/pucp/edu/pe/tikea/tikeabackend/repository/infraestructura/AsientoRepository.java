package pucp.edu.pe.tikea.tikeabackend.repository.infraestructura;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pucp.edu.pe.tikea.tikeabackend.model.infraestructura.Asiento;
import pucp.edu.pe.tikea.tikeabackend.model.infraestructura.TipoEstadoAsiento;

import java.util.List;

@Repository
public interface AsientoRepository extends JpaRepository<Asiento,Integer> {

    // Buscar asientos por zona
    List<Asiento> findByZona_IdZona(Integer idZona);

    // Buscar por estado (LIBRE, OCUPADO, RESERVADO...)
    List<Asiento> findByEstado(TipoEstadoAsiento estado);

    // Buscar por fila
    List<Asiento> findByFilaAsiento(String filaAsiento);

    // Buscar por columna
    List<Asiento> findByColumnaAsiento(String columnaAsiento);

    // Buscar por fila y columna en una zona específica
    List<Asiento> findByZona_IdZonaAndFilaAsientoAndColumnaAsiento(Integer idZona,
                                                                   String filaAsiento,
                                                                   String columnaAsiento);

    // Buscar solo asientos activos (activo = 1)
    @Query("SELECT a FROM Asiento a WHERE a.activo = 1")
    List<Asiento> findAllActive();

    // Buscar asientos activos por zona
    @Query("SELECT a FROM Asiento a WHERE a.activo = 1 AND a.zona.idZona = :idZona")
    List<Asiento> findActiveByZona(@Param("idZona") Integer idZona);
}
