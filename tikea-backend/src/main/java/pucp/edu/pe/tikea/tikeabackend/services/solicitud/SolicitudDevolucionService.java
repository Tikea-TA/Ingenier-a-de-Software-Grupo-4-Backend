package pucp.edu.pe.tikea.tikeabackend.services.solicitud;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pucp.edu.pe.tikea.tikeabackend.DTO.solicitud.SolcitudDevolucionRequest;
import pucp.edu.pe.tikea.tikeabackend.DTO.solicitud.SolicitudDevolucionResponse;
import pucp.edu.pe.tikea.tikeabackend.model.solicitud.SolicitudDevolucion;
import pucp.edu.pe.tikea.tikeabackend.model.usuarios.Cliente;
import pucp.edu.pe.tikea.tikeabackend.model.infraestructura.TicketEspecifico;
import pucp.edu.pe.tikea.tikeabackend.repository.solicitud.SolicitudDevolucionRepository;
import pucp.edu.pe.tikea.tikeabackend.repository.usuarios.cliente.ClienteRepository;
import pucp.edu.pe.tikea.tikeabackend.repository.venta.TicketSpecificoRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SolicitudDevolucionService {

    private final SolicitudDevolucionRepository repository;
    private final ClienteRepository clienteRepository;
    private final TicketSpecificoRepository ticketEspecificoRepository;

    // --- MAPPERS ---
    private SolicitudDevolucionResponse toDTO(SolicitudDevolucion solicitud) {
        if (solicitud == null) return null;

        return SolicitudDevolucionResponse.builder()
                .idSolicitud(solicitud.getIdSolicitud())
                .idCliente(solicitud.getCliente() != null ? solicitud.getCliente().getIdUsuario() : null)
                .idTicketEspecifico(solicitud.getTicketEspecifico() != null ? solicitud.getTicketEspecifico().getIdTicketEspecifico() : null)
                .motivo(solicitud.getMotivo())
                .montoSolicitado(solicitud.getMontoSolicitado())
                .politicaDevolucion(solicitud.getPoliticaDevolucion())
                .reincorporacionStock(solicitud.getReincorporacionStock())
                .activo(solicitud.getActivo())
                .build();
    }

    // Convierte DTO (SolcitudDevolucionRequest) a Entidad (SolicitudDevolucion) para CREACIÓN
    private SolicitudDevolucion toEntity(SolcitudDevolucionRequest dto, Cliente cliente, TicketEspecifico ticket) {
        SolicitudDevolucion entity = new SolicitudDevolucion();

        // Asignación de relaciones
        entity.setCliente(cliente);
        entity.setTicketEspecifico(ticket);

        // Asignación de campos de datos
        entity.setMotivo(dto.getMotivo());
        entity.setMontoSolicitado(dto.getMontoSolicitado());

        // Campos opcionales con valor por defecto
        entity.setPoliticaDevolucion(dto.getPoliticaDevolucion() != null ? dto.getPoliticaDevolucion() : false);
        entity.setReincorporacionStock(dto.getReincorporacionStock() != null ? dto.getReincorporacionStock() : false);

        entity.setActivo(1);

        return entity;
    }

    // --- CRUD Y OPERACIONES PRINCIPALES ---
    public SolicitudDevolucionResponse registrar(SolcitudDevolucionRequest request) {

        // 1. Obtener entidades gestionadas (managed) a partir de los IDs
        Integer clienteId = request.getIdCliente();
        Integer ticketId = request.getIdTicketEspecifico();

        Cliente clienteManaged = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente con ID " + clienteId + " no encontrado."));

        TicketEspecifico ticketManaged = ticketEspecificoRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket Específico con ID " + ticketId + " no encontrado."));

        // 2. Mapear a entidad y guardar
        SolicitudDevolucion nuevaSolicitud = toEntity(request, clienteManaged, ticketManaged);

        // 3. Devolver DTO de respuesta
        return toDTO(repository.save(nuevaSolicitud));
    }

    public SolicitudDevolucionResponse modificar(Integer id, SolcitudDevolucionRequest nuevosDatos) {
        SolicitudDevolucion sd = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitud de Devolución no encontrada con ID: " + id));

        // Aplicar actualizaciones parciales (solo si el campo no es nulo en el DTO)
        if (nuevosDatos.getMotivo() != null) {
            sd.setMotivo(nuevosDatos.getMotivo());
        }
        if (nuevosDatos.getMontoSolicitado() != null) {
            sd.setMontoSolicitado(nuevosDatos.getMontoSolicitado());
        }
        if (nuevosDatos.getPoliticaDevolucion() != null) {
            sd.setPoliticaDevolucion(nuevosDatos.getPoliticaDevolucion());
        }
        if (nuevosDatos.getReincorporacionStock() != null) {
            sd.setReincorporacionStock(nuevosDatos.getReincorporacionStock());
        }

        // Si se proporciona un nuevo Cliente ID, buscar y asignar entidad
        if (nuevosDatos.getIdCliente() != null) {
            Integer clienteId = nuevosDatos.getIdCliente();
            Cliente clienteManaged = clienteRepository.findById(clienteId)
                    .orElseThrow(() -> new RuntimeException("Cliente con ID " + clienteId + " no encontrado para modificación."));
            sd.setCliente(clienteManaged);
        }

        // Si se proporciona un nuevo Ticket ID, buscar y asignar entidad
        if (nuevosDatos.getIdTicketEspecifico() != null) {
            Integer ticketId = nuevosDatos.getIdTicketEspecifico();
            TicketEspecifico ticketManaged = ticketEspecificoRepository.findById(ticketId)
                    .orElseThrow(() -> new RuntimeException("Ticket Específico con ID " + ticketId + " no encontrado para modificación."));
            sd.setTicketEspecifico(ticketManaged);
        }

        return toDTO(repository.save(sd));
    }

    // ELIMINACIÓN LÓGICA
    @Transactional
    public boolean eliminarLogico(Integer id) {
        Optional<SolicitudDevolucion> opt = repository.findById(id);

        if (opt.isEmpty()) return false;

        SolicitudDevolucion solicitud = opt.get();
        solicitud.setActivo(0);
        repository.save(solicitud);

        return true;
    }

    // BUSCAR POR ID
    public Optional<SolicitudDevolucionResponse> obtenerPorId(Integer id) {
        return repository.findById(id)
                .map(this::toDTO);
    }

    // --- CONSULTAS (Todas retornan List<SolicitudDevolucionResponse>) ---

    public List<SolicitudDevolucionResponse> listarTodos() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<SolicitudDevolucionResponse> listarActivos() {
        return repository.findAllActive().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // BUSCAR POR CLIENTE (Ahora recibe solo el ID)
    public List<SolicitudDevolucionResponse> buscarPorCliente(Integer idCliente) {
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new RuntimeException("Cliente con ID " + idCliente + " no encontrado."));

        return repository.findByCliente(cliente).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // BUSCAR ACTIVOS POR CLIENTE (Ahora recibe solo el ID)
    public List<SolicitudDevolucionResponse> buscarActivosPorCliente(Integer idCliente) {
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new RuntimeException("Cliente con ID " + idCliente + " no encontrado."));

        return repository.findActiveByCliente(cliente).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // BUSCAR POR TICKET (Ahora recibe solo el ID)
    public List<SolicitudDevolucionResponse> buscarPorTicket(Integer idTicket) {
        TicketEspecifico ticket = ticketEspecificoRepository.findById(idTicket)
                .orElseThrow(() -> new RuntimeException("Ticket Específico con ID " + idTicket + " no encontrado."));

        return repository.findByTicketEspecifico(ticket).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // BUSCAR ACTIVOS POR TICKET (Ahora recibe solo el ID)
    public List<SolicitudDevolucionResponse> buscarActivosPorTicket(Integer idTicket) {
        TicketEspecifico ticket = ticketEspecificoRepository.findById(idTicket)
                .orElseThrow(() -> new RuntimeException("Ticket Específico con ID " + idTicket + " no encontrado."));

        return repository.findActiveByTicketEspecifico(ticket).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<SolicitudDevolucionResponse> buscarPorPolitica(Boolean aplicada) {
        return repository.findByPoliticaDevolucion(aplicada).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<SolicitudDevolucionResponse> buscarPorReincorporacion(Boolean reincorporado) {
        return repository.findByReincorporacionStock(reincorporado).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
