package pucp.edu.pe.tikea.tikeabackend.services.venta;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pucp.edu.pe.tikea.tikeabackend.model.venta.Transaccion;
import pucp.edu.pe.tikea.tikeabackend.model.venta.TipoMoneda;
import pucp.edu.pe.tikea.tikeabackend.model.venta.TipoTransferencia;
import pucp.edu.pe.tikea.tikeabackend.repository.venta.TransaccionRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransaccionService {

    private final TransaccionRepository transaccionRepository;

    @Transactional
    public Transaccion registrar(Transaccion transaccion) {
        transaccion.setActivo(1);
        return transaccionRepository.save(transaccion);
    }

    public Transaccion obtenerPorId(Integer id) {
        return transaccionRepository.findByIdTransaccionAndActivo(id, 1)
                .orElseThrow(() -> new RuntimeException("Transacción no encontrada o inactiva"));
    }

    public List<Transaccion> listarActivos() {
        return transaccionRepository.findByActivo(1);
    }

    @Transactional
    public Transaccion actualizar(Integer id, Transaccion datos) {

        Transaccion transaccion = obtenerPorId(id);

        transaccion.setMedioDePago(datos.getMedioDePago());
        transaccion.setReserva(datos.getReserva());
        transaccion.setEstado(datos.getEstado());
        transaccion.setMonto(datos.getMonto());
        transaccion.setMoneda(datos.getMoneda());
        transaccion.setNumeroTransaccion(datos.getNumeroTransaccion());

        return transaccionRepository.save(transaccion);
    }

    @Transactional
    public void eliminar(Integer id) {
        Transaccion transaccion = obtenerPorId(id);
        transaccion.setActivo(0);
        transaccionRepository.save(transaccion);
    }

    // CONSULTAS

    public Transaccion obtenerPorNumeroTransaccion(String numero) {
        return transaccionRepository.findByNumeroTransaccion(numero)
                .orElseThrow(() -> new RuntimeException("Transacción no encontrada"));
    }

    public List<Transaccion> buscarPorMedioPago(Integer idMedioPago) {
        return transaccionRepository.findByMedioDePago_IdMedioDePagoAndActivo(idMedioPago, 1);
    }

    public List<Transaccion> buscarPorReserva(Integer idReserva) {
        return transaccionRepository.findByReserva_IdReservaAndActivo(idReserva, 1);
    }

    public List<Transaccion> buscarPorEstado(TipoTransferencia estado) {
        return transaccionRepository.findByEstadoAndActivo(estado, 1);
    }

    public List<Transaccion> buscarPorMoneda(TipoMoneda moneda) {
        return transaccionRepository.findByMonedaAndActivo(moneda, 1);
    }
}
