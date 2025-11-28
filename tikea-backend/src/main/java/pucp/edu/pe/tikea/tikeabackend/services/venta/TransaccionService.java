package pucp.edu.pe.tikea.tikeabackend.services.venta;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pucp.edu.pe.tikea.tikeabackend.DTO.venta.TransaccionRequest;
import pucp.edu.pe.tikea.tikeabackend.DTO.venta.TransaccionResponse;
import pucp.edu.pe.tikea.tikeabackend.model.venta.*;
import pucp.edu.pe.tikea.tikeabackend.repository.venta.MedioDePagoRepository;
import pucp.edu.pe.tikea.tikeabackend.repository.venta.ReservaRepository;
import pucp.edu.pe.tikea.tikeabackend.repository.venta.TransaccionRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TransaccionService {

    private final TransaccionRepository transaccionRepository;
    private final MedioDePagoRepository medioDePagoRepository;
    private final ReservaRepository reservaRepository;

    // --- MAPPERS ---
    private TransaccionResponse toDTO(Transaccion transaccion) {
        if (transaccion == null) {
            throw new RuntimeException("Transacción no encontrada o inactiva");
        }
        return TransaccionResponse.builder()
                .idTransaccion(transaccion.getIdTransaccion())
                .idMedioDePago(transaccion.getMedioDePago() != null ? transaccion.getMedioDePago().getIdMedioDePago() : null)
                .idReserva(transaccion.getReserva() != null ? transaccion.getReserva().getIdReserva() : null)
                .numeroTransaccion(transaccion.getNumeroTransaccion())
                .monto(transaccion.getMonto())
                .estado(transaccion.getEstado())
                .moneda(transaccion.getMoneda())
                .resultado(transaccion.getResultado())
                .ipOrigen(transaccion.getIpOrigen())
                .fechaTransaccion(transaccion.getFechaTransaccion())
                .activo(transaccion.getActivo())
                .build();
    }

    private Transaccion toEntity(TransaccionRequest request) {
        Transaccion entity = new Transaccion();

        // Mapeo de IDs de relaciones (obligatorios en el request para registro)
        if (request.getIdMedioDePago() == null || request.getIdReserva() == null) {
            throw new IllegalArgumentException("El ID del Medio de Pago y el ID de Reserva son obligatorios para registrar una transacción.");
        }

        MedioDePago medioDePago = medioDePagoRepository.findById(request.getIdMedioDePago())
                .orElseThrow(() -> new IllegalArgumentException("Medio de Pago no encontrado con ID: " + request.getIdMedioDePago()));

        Reserva reserva = reservaRepository.findById(request.getIdReserva())
                .orElseThrow(() -> new IllegalArgumentException("Reserva no encontrada con ID: " + request.getIdReserva()));

        entity.setMedioDePago(medioDePago);
        entity.setReserva(reserva);

        // Mapeo de campos directos
        entity.setNumeroTransaccion(request.getNumeroTransaccion());
        entity.setMonto(request.getMonto());
        entity.setEstado(request.getEstado());
        entity.setMoneda(request.getMoneda());
        entity.setResultado(request.getResultado());
        entity.setIpOrigen(request.getIpOrigen());

        // Valores de control
        entity.setFechaTransaccion(LocalDateTime.now());
        entity.setActivo(1);

        return entity;
    }

    // --- CRUD Y OPERACIONES PRINCIPALES ---
    @Transactional
    public TransaccionResponse registrar(TransaccionRequest request) {
        Transaccion transaccion = toEntity(request);
        return toDTO(transaccionRepository.save(transaccion));
    }

    public TransaccionResponse obtenerPorId(Integer id) {
        return transaccionRepository.findByIdTransaccionAndActivo(id, 1)
                .map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Transacción no encontrada o inactiva con ID: " + id));
    }

    public List<TransaccionResponse> listarActivos() {
        return transaccionRepository.findByActivo(1).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public TransaccionResponse actualizar(Integer id, TransaccionRequest datos) {

        Transaccion transaccion = transaccionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transacción no encontrada con ID: " + id));

        // Actualización parcial de relaciones
        if (datos.getIdMedioDePago() != null) {
            MedioDePago medioDePago = medioDePagoRepository.findById(datos.getIdMedioDePago())
                    .orElseThrow(() -> new IllegalArgumentException("Medio de Pago no encontrado con ID: " + datos.getIdMedioDePago()));
            transaccion.setMedioDePago(medioDePago);
        }

        if (datos.getIdReserva() != null) {
            Reserva reserva = reservaRepository.findById(datos.getIdReserva())
                    .orElseThrow(() -> new IllegalArgumentException("Reserva no encontrada con ID: " + datos.getIdReserva()));
            transaccion.setReserva(reserva);
        }

        // Actualización parcial de campos directos
        if (datos.getNumeroTransaccion() != null) {
            transaccion.setNumeroTransaccion(datos.getNumeroTransaccion());
        }
        if (datos.getMonto() != null) {
            transaccion.setMonto(datos.getMonto());
        }
        if (datos.getEstado() != null) {
            transaccion.setEstado(datos.getEstado());
        }
        if (datos.getMoneda() != null) {
            transaccion.setMoneda(datos.getMoneda());
        }
        if (datos.getResultado() != null) {
            transaccion.setResultado(datos.getResultado());
        }
        if (datos.getIpOrigen() != null) {
            transaccion.setIpOrigen(datos.getIpOrigen());
        }

        return toDTO(transaccionRepository.save(transaccion));
    }

    @Transactional
    public void eliminar(Integer id) {
        Transaccion transaccion = transaccionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transacción no encontrada con ID: " + id));
        transaccion.setActivo(0);
        transaccionRepository.save(transaccion);
    }

    // CONSULTAS PERSONALIZADAS (Retornan List<TransaccionResponse> o TransaccionResponse)

    public TransaccionResponse obtenerPorNumeroTransaccion(String numero) {
        return transaccionRepository.findByNumeroTransaccion(numero)
                .map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Transacción no encontrada con número: " + numero));
    }

    public List<TransaccionResponse> buscarPorMedioPago(Integer idMedioPago) {
        return transaccionRepository.findByMedioDePago_IdMedioDePagoAndActivo(idMedioPago, 1).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<TransaccionResponse> buscarPorReserva(Integer idReserva) {
        return transaccionRepository.findByReserva_IdReservaAndActivo(idReserva, 1).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<TransaccionResponse> buscarPorEstado(TipoTransferencia estado) {
        return transaccionRepository.findByEstadoAndActivo(estado, 1).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<TransaccionResponse> buscarPorMoneda(TipoMoneda moneda) {
        return transaccionRepository.findByMonedaAndActivo(moneda, 1).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}