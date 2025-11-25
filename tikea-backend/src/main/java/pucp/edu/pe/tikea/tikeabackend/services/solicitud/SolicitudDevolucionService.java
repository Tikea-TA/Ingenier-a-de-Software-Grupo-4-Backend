package pucp.edu.pe.tikea.tikeabackend.services.solicitud;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pucp.edu.pe.tikea.tikeabackend.model.solicitud.SolicitudDevolucion;
import pucp.edu.pe.tikea.tikeabackend.model.usuarios.Cliente;
import pucp.edu.pe.tikea.tikeabackend.model.infraestructura.TicketEspecifico;
import pucp.edu.pe.tikea.tikeabackend.repository.solicitud.SolicitudDevolucionRepository;
import pucp.edu.pe.tikea.tikeabackend.repository.usuarios.cliente.ClienteRepository;
import pucp.edu.pe.tikea.tikeabackend.repository.venta.TicketSpecificoRepository;

import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class SolicitudDevolucionService {

    private final SolicitudDevolucionRepository repository;
    private final ClienteRepository clienteRepository;
    private final TicketSpecificoRepository ticketEspecificoRepository;

    @Transactional
    public SolicitudDevolucion registrar(SolicitudDevolucion solicitud) {

        if (solicitud.getCliente() == null || solicitud.getCliente().getIdUsuario() == null) {
            throw new IllegalArgumentException("El ID del Cliente es obligatorio para registrar la solicitud de devolución.");
        }
        Integer clienteId = solicitud.getCliente().getIdUsuario();

        Cliente clienteManaged = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente con ID " + clienteId + " no encontrado."));
        solicitud.setCliente(clienteManaged);

        if (solicitud.getTicketEspecifico() == null || solicitud.getTicketEspecifico().getIdTicketEspecifico() == null) {
            throw new IllegalArgumentException("El ID del Ticket Específico es obligatorio para registrar la solicitud de devolución.");
        }
        Integer ticketId = solicitud.getTicketEspecifico().getIdTicketEspecifico();

        TicketEspecifico ticketManaged = ticketEspecificoRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket Específico con ID " + ticketId + " no encontrado."));
        solicitud.setTicketEspecifico(ticketManaged);

        return repository.save(solicitud);
    }

    // Obtener todos
    public List<SolicitudDevolucion> listarTodos() {
        return repository.findAll();
    }

    // Obtener por ID
    public Optional<SolicitudDevolucion> obtenerPorId(Integer id) {
        return repository.findById(id);
    }

    //   ELIMINACIÓN

    @Transactional
    public boolean eliminarLogico(Integer id) {
        Optional<SolicitudDevolucion> opt = repository.findById(id);

        if (opt.isEmpty()) return false;

        SolicitudDevolucion solicitud = opt.get();
        solicitud.setActivo(0);        // ← eliminado lógico
        repository.save(solicitud);

        return true;
    }

    //       CONSULTAS

    public List<SolicitudDevolucion> listarActivos() {
        return repository.findAllActive();
    }

    public List<SolicitudDevolucion> buscarPorCliente(Cliente cliente) {
        return repository.findByCliente(cliente);
    }

    public List<SolicitudDevolucion> buscarActivosPorCliente(Cliente cliente) {
        return repository.findActiveByCliente(cliente);
    }

    public List<SolicitudDevolucion> buscarPorTicket(TicketEspecifico ticket) {
        return repository.findByTicketEspecifico(ticket);
    }

    public List<SolicitudDevolucion> buscarActivosPorTicket(TicketEspecifico ticket) {
        return repository.findActiveByTicketEspecifico(ticket);
    }

    public List<SolicitudDevolucion> buscarPorPolitica(Boolean aplicada) {
        return repository.findByPoliticaDevolucion(aplicada);
    }

    public List<SolicitudDevolucion> buscarPorReincorporacion(Boolean reincorporado) {
        return repository.findByReincorporacionStock(reincorporado);
    }
}
