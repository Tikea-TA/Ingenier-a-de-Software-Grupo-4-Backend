package pucp.edu.pe.tikea.tikeabackend.services.solicitud;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pucp.edu.pe.tikea.tikeabackend.DTO.solicitud.SolicitudCambioRequest;
import pucp.edu.pe.tikea.tikeabackend.DTO.solicitud.SolicitudCambioResponse;
import pucp.edu.pe.tikea.tikeabackend.model.solicitud.SolicitudCambio;
import pucp.edu.pe.tikea.tikeabackend.model.solicitud.TipoOperacionCambio;
import pucp.edu.pe.tikea.tikeabackend.model.usuarios.Cliente;
import pucp.edu.pe.tikea.tikeabackend.model.venta.Reserva;
import pucp.edu.pe.tikea.tikeabackend.repository.solicitud.SolicitudCambioRepository;
import pucp.edu.pe.tikea.tikeabackend.repository.usuarios.cliente.ClienteRepository;
import pucp.edu.pe.tikea.tikeabackend.repository.venta.ReservaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SolicitudCambioService {

    private final SolicitudCambioRepository solicitudCambioRepository;
    private final ClienteRepository clienteRepository;
    private final ReservaRepository reservaRepository;

    // --- MAPPERS ---
    private SolicitudCambioResponse toDTO(SolicitudCambio solicitud) {
        if (solicitud == null) return null;

        return SolicitudCambioResponse.builder()
                .idSolicitud(solicitud.getIdSolicitud())
                .idCliente(solicitud.getCliente() != null ? solicitud.getCliente().getIdUsuario() : null)
                .idReserva(solicitud.getReserva() != null ? solicitud.getReserva().getIdReserva() : null)
                .cantidad(solicitud.getCantidad())
                .disponibilidadValidada(solicitud.getDisponibilidadValidada())
                .diferenciaPrecio(solicitud.getDiferenciaPrecio())
                .tipoOperacion(solicitud.getTipoOperacion())
                .fecha(solicitud.getFecha())
                .activo(solicitud.getActivo())
                .build();
    }

    // Convierte DTO (SolicitudCambioRequest) a Entidad (SolicitudCambio) para CREACIÓN
    private SolicitudCambio toEntity(SolicitudCambioRequest dto, Cliente cliente, Reserva reserva) {
        SolicitudCambio entity = new SolicitudCambio();

        entity.setCliente(cliente);
        entity.setReserva(reserva);

        entity.setCantidad(dto.getCantidad());
        entity.setTipoOperacion(dto.getTipoOperacion());

        entity.setDisponibilidadValidada(dto.getDisponibilidadValidada() != null ? dto.getDisponibilidadValidada() : false);
        entity.setDiferenciaPrecio(dto.getDiferenciaPrecio() != null ? dto.getDiferenciaPrecio() : 0.0);

        // Valores de control por defecto
        entity.setFecha(LocalDateTime.now());
        entity.setActivo(1);

        return entity;
    }

    // --- CRUD Y OPERACIONES PRINCIPALES ---

    // REGISTRAR (Usa DTO Request)
    public SolicitudCambioResponse registrar(SolicitudCambioRequest request) {

        Integer clienteId = request.getIdCliente();
        Integer reservaId = request.getIdReserva();

        Cliente clienteManaged = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente con ID " + clienteId + " no encontrado."));

        Reserva reservaManaged = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new RuntimeException("Reserva con ID " + reservaId + " no encontrada."));

        SolicitudCambio nuevaSolicitud = toEntity(request, clienteManaged, reservaManaged);

        return toDTO(solicitudCambioRepository.save(nuevaSolicitud));
    }

    // MODIFICAR (Soporte para actualización parcial)
    public SolicitudCambioResponse modificar(Integer id, SolicitudCambioRequest nuevosDatos) {
        SolicitudCambio sc = solicitudCambioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitud de Cambio no encontrada con ID: " + id));

        if (nuevosDatos.getTipoOperacion() != null) {
            sc.setTipoOperacion(nuevosDatos.getTipoOperacion());
        }
        if (nuevosDatos.getDiferenciaPrecio() != null) {
            sc.setDiferenciaPrecio(nuevosDatos.getDiferenciaPrecio());
        }
        if (nuevosDatos.getDisponibilidadValidada() != null) {
            sc.setDisponibilidadValidada(nuevosDatos.getDisponibilidadValidada());
        }
        if (nuevosDatos.getCantidad() != null) {
            sc.setCantidad(nuevosDatos.getCantidad());
        }

        if (nuevosDatos.getIdCliente() != null) {
            Integer clienteId = nuevosDatos.getIdCliente();
            Cliente clienteManaged = clienteRepository.findById(clienteId)
                    .orElseThrow(() -> new RuntimeException("Cliente con ID " + clienteId + " no encontrado para modificación."));
            sc.setCliente(clienteManaged);
        }

        if (nuevosDatos.getIdReserva() != null) {
            Integer reservaId = nuevosDatos.getIdReserva();
            Reserva reservaManaged = reservaRepository.findById(reservaId)
                    .orElseThrow(() -> new RuntimeException("Reserva con ID " + reservaId + " no encontrada para modificación."));
            sc.setReserva(reservaManaged);
        }

        return toDTO(solicitudCambioRepository.save(sc));
    }

    // ELIMINACIÓN LÓGICA
    public void eliminarLogico(Integer id) {
        SolicitudCambio sc = solicitudCambioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitud de Cambio no encontrada para eliminación: " + id));

        sc.setActivo(0);
        solicitudCambioRepository.save(sc);
    }

    // ACTIVAR LOGICAMENTE
    public void activar(Integer id) {
        SolicitudCambio sc = solicitudCambioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitud de Cambio no encontrada para activación: " + id));

        sc.setActivo(1);
        solicitudCambioRepository.save(sc);
    }

    // BUSCAR POR ID
    public SolicitudCambioResponse buscarPorId(Integer id) {
        return solicitudCambioRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Solicitud de Cambio no encontrada con ID: " + id));
    }

    // BUSCAR POR CLIENTE (Ahora recibe solo el ID del cliente)
    public List<SolicitudCambioResponse> buscarPorCliente(Integer idCliente) {
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new RuntimeException("Cliente con ID " + idCliente + " no encontrado."));

        return solicitudCambioRepository.findByCliente(cliente).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<SolicitudCambioResponse> buscarActivosPorCliente(Integer idCliente) {
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new RuntimeException("Cliente con ID " + idCliente + " no encontrado."));

        return solicitudCambioRepository.findActiveByCliente(cliente).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // BUSCAR POR RESERVA (Ahora recibe solo el ID de la reserva)
    public List<SolicitudCambioResponse> buscarPorReserva(Integer idReserva) {
        Reserva reserva = reservaRepository.findById(idReserva)
                .orElseThrow(() -> new RuntimeException("Reserva con ID " + idReserva + " no encontrada."));

        return solicitudCambioRepository.findByReserva(reserva).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // BUSCAR POR TIPO OPERACIÓN
    public List<SolicitudCambioResponse> buscarPorTipo(TipoOperacionCambio tipo) {
        return solicitudCambioRepository.findByTipoOperacion(tipo).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // BUSCAR POR DISPONIBILIDAD VALIDADA
    public List<SolicitudCambioResponse> buscarPorDisponibilidad(Boolean validada) {
        return solicitudCambioRepository.findByDisponibilidadValidada(validada).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // LISTAR ACTIVOS
    public List<SolicitudCambioResponse> listarActivos() {
        return solicitudCambioRepository.findAllActive().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // LISTAR TODOS
    public List<SolicitudCambioResponse> listarTodos() {
        return solicitudCambioRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // BUSCAR POR RANGO DE FECHAS
    public List<SolicitudCambioResponse> buscarPorRangoFechas(LocalDateTime inicio, LocalDateTime fin) {
        return solicitudCambioRepository.findByFechaBetween(inicio, fin).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // BUSCAR ACTIVOS POR RANGO DE FECHAS
    public List<SolicitudCambioResponse> buscarActivosPorFechas(LocalDateTime inicio, LocalDateTime fin) {
        return solicitudCambioRepository.findActiveByFechaBetween(inicio, fin).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
