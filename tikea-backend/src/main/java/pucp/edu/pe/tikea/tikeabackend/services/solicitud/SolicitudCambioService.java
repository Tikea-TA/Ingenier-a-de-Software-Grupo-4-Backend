package pucp.edu.pe.tikea.tikeabackend.services.solicitud;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pucp.edu.pe.tikea.tikeabackend.model.solicitud.SolicitudCambio;
import pucp.edu.pe.tikea.tikeabackend.model.solicitud.TipoOperacionCambio;
import pucp.edu.pe.tikea.tikeabackend.model.usuarios.Cliente;
import pucp.edu.pe.tikea.tikeabackend.model.venta.Reserva;
import pucp.edu.pe.tikea.tikeabackend.repository.solicitud.SolicitudCambioRepository;
import pucp.edu.pe.tikea.tikeabackend.repository.usuarios.cliente.ClienteRepository;
import pucp.edu.pe.tikea.tikeabackend.repository.venta.ReservaRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SolicitudCambioService {

    private final SolicitudCambioRepository solicitudCambioRepository;
    private final ClienteRepository clienteRepository;
    private final ReservaRepository reservaRepository;

    public SolicitudCambio registrar(SolicitudCambio solicitud) {

        if (solicitud.getCliente() == null || solicitud.getCliente().getIdUsuario() == null) {
            throw new IllegalArgumentException("El ID del Cliente es obligatorio para registrar la solicitud.");
        }
        Integer clienteId = solicitud.getCliente().getIdUsuario();

        Cliente clienteManaged = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente con ID " + clienteId + " no encontrado."));

        if (solicitud.getReserva() == null || solicitud.getReserva().getIdReserva() == null) {
            throw new IllegalArgumentException("El ID de la Reserva es obligatorio para registrar la solicitud.");
        }
        Integer reservaId = solicitud.getReserva().getIdReserva();

        Reserva reservaManaged = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new RuntimeException("Reserva con ID " + reservaId + " no encontrada."));

        solicitud.setCliente(clienteManaged);
        solicitud.setReserva(reservaManaged);

        return solicitudCambioRepository.save(solicitud);
    }

    // MODIFICAR
    public SolicitudCambio modificar(Integer id, SolicitudCambio nuevosDatos) {
        SolicitudCambio sc = solicitudCambioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        sc.setTipoOperacion(nuevosDatos.getTipoOperacion());
        sc.setDiferenciaPrecio(nuevosDatos.getDiferenciaPrecio());
        sc.setDisponibilidadValidada(nuevosDatos.getDisponibilidadValidada());

        if (nuevosDatos.getCliente() != null && nuevosDatos.getCliente().getIdUsuario() != null) {
            Integer clienteId = nuevosDatos.getCliente().getIdUsuario();
            Cliente clienteManaged = clienteRepository.findById(clienteId)
                    .orElseThrow(() -> new RuntimeException("Cliente con ID " + clienteId + " no encontrado para modificación."));
            sc.setCliente(clienteManaged);
        }

        if (nuevosDatos.getReserva() != null && nuevosDatos.getReserva().getIdReserva() != null) {
            Integer reservaId = nuevosDatos.getReserva().getIdReserva();
            Reserva reservaManaged = reservaRepository.findById(reservaId)
                    .orElseThrow(() -> new RuntimeException("Reserva con ID " + reservaId + " no encontrada para modificación."));
            sc.setReserva(reservaManaged);
        }

        return solicitudCambioRepository.save(sc);
    }

    // ELIMINACIÓN LÓGICA
    public void eliminarLogico(Integer id) {
        SolicitudCambio sc = solicitudCambioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        sc.setActivo(0);
        solicitudCambioRepository.save(sc);
    }

    // ACTIVAR LOGICAMENTE
    public void activar(Integer id) {
        SolicitudCambio sc = solicitudCambioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        sc.setActivo(1);
        solicitudCambioRepository.save(sc);
    }

    // BUSCAR POR ID
    public SolicitudCambio buscarPorId(Integer id) {
        return solicitudCambioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));
    }

    // BUSCAR POR CLIENTE
    public List<SolicitudCambio> buscarPorCliente(Cliente cliente) {
        return solicitudCambioRepository.findByCliente(cliente);
    }

    public List<SolicitudCambio> buscarActivosPorCliente(Cliente cliente) {
        return solicitudCambioRepository.findActiveByCliente(cliente);
    }

    // BUSCAR POR RESERVA
    public List<SolicitudCambio> buscarPorReserva(Reserva reserva) {
        return solicitudCambioRepository.findByReserva(reserva);
    }

    // BUSCAR POR TIPO OPERACIÓN
    public List<SolicitudCambio> buscarPorTipo(TipoOperacionCambio tipo) {
        return solicitudCambioRepository.findByTipoOperacion(tipo);
    }

    // BUSCAR POR DISPONIBILIDAD VALIDADA
    public List<SolicitudCambio> buscarPorDisponibilidad(Boolean validada) {
        return solicitudCambioRepository.findByDisponibilidadValidada(validada);
    }

    // LISTAR ACTIVOS
    public List<SolicitudCambio> listarActivos() {
        return solicitudCambioRepository.findAllActive();
    }

    // LISTAR TODOS
    public List<SolicitudCambio> listarTodos() {
        return solicitudCambioRepository.findAll();
    }

    // BUSCAR POR RANGO DE FECHAS
    public List<SolicitudCambio> buscarPorRangoFechas(LocalDateTime inicio, LocalDateTime fin) {
        return solicitudCambioRepository.findByFechaBetween(inicio, fin);
    }

    // BUSCAR ACTIVOS POR RANGO DE FECHAS
    public List<SolicitudCambio> buscarActivosPorFechas(LocalDateTime inicio, LocalDateTime fin) {
        return solicitudCambioRepository.findActiveByFechaBetween(inicio, fin);
    }
}
