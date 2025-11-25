package pucp.edu.pe.tikea.tikeabackend.repository.venta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pucp.edu.pe.tikea.tikeabackend.model.venta.Transaccion;
import pucp.edu.pe.tikea.tikeabackend.model.venta.TipoTransferencia;
import pucp.edu.pe.tikea.tikeabackend.model.venta.TipoMoneda;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransaccionRepository extends JpaRepository<Transaccion, Integer> {

    // Buscar por ID y activo
    Optional<Transaccion> findByIdTransaccionAndActivo(Integer idTransaccion, Integer activo);

    // Listar solo activos
    List<Transaccion> findByActivo(Integer activo);

    // Buscar por medio de pago
    List<Transaccion> findByMedioDePago_IdMedioDePagoAndActivo(Integer idMedioDePago, Integer activo);

    // Buscar por reserva
    List<Transaccion> findByReserva_IdReservaAndActivo(Integer idReserva, Integer activo);

    // Buscar por estado de transferencia
    List<Transaccion> findByEstadoAndActivo(TipoTransferencia estado, Integer activo);

    // Buscar por moneda
    List<Transaccion> findByMonedaAndActivo(TipoMoneda moneda, Integer activo);

    // Buscar por número de transacción (único)
    Optional<Transaccion> findByNumeroTransaccion(String numeroTransaccion);
}
