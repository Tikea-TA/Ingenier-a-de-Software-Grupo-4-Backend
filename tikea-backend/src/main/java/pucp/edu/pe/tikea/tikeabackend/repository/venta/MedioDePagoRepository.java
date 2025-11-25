package pucp.edu.pe.tikea.tikeabackend.repository.venta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pucp.edu.pe.tikea.tikeabackend.model.venta.MedioDePago;
import pucp.edu.pe.tikea.tikeabackend.model.venta.TipoMedioDePago;
import pucp.edu.pe.tikea.tikeabackend.model.infraestructura.TipoEstadoAproDes;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedioDePagoRepository extends JpaRepository<MedioDePago, Integer> {

    // Buscar por ID solo si está activo
    Optional<MedioDePago> findByIdMedioDePagoAndActivo(Integer idMedioDePago, Integer activo);

    // Buscar todos activos
    List<MedioDePago> findByActivo(Integer activo);

    // Buscar por tipo de pago
    List<MedioDePago> findByTipoPago(TipoMedioDePago tipoPago);

    // Buscar por estado
    List<MedioDePago> findByEstado(TipoEstadoAproDes estado);

    // Validación Sunat
    List<MedioDePago> findByValidacionSunat(boolean validacionSunat);

    // Buscar por pasarela específica
    List<MedioDePago> findByPasarelaIntegracion(String pasarelaIntegracion);

    // Combinaciones útiles
    List<MedioDePago> findByTipoPagoAndActivo(TipoMedioDePago tipoPago, Integer activo);

    List<MedioDePago> findByEstadoAndActivo(TipoEstadoAproDes estado, Integer activo);
}
