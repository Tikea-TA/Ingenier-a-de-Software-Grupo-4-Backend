package pucp.edu.pe.tikea.tikeabackend.services.venta;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pucp.edu.pe.tikea.tikeabackend.DTO.venta.TicketRequest;
import pucp.edu.pe.tikea.tikeabackend.DTO.venta.TicketResponse;
import pucp.edu.pe.tikea.tikeabackend.model.infraestructura.*;
import pucp.edu.pe.tikea.tikeabackend.model.usuarios.Cliente;
import pucp.edu.pe.tikea.tikeabackend.model.venta.Reserva;
import pucp.edu.pe.tikea.tikeabackend.repository.venta.ReservaRepository;
import pucp.edu.pe.tikea.tikeabackend.repository.venta.TicketSpecificoRepository;
import pucp.edu.pe.tikea.tikeabackend.repository.infraestructura.AsientoRepository;
import pucp.edu.pe.tikea.tikeabackend.repository.infraestructura.EventoRepository;
import pucp.edu.pe.tikea.tikeabackend.repository.infraestructura.ZonaRepository;
import pucp.edu.pe.tikea.tikeabackend.repository.usuarios.cliente.ClienteRepository;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;

@Service
@Transactional
public class TicketSpecificoService {
    private final TicketSpecificoRepository  ticketSpecificoRepository;
    private final ReservaRepository reservaRepository;
    private final ClienteRepository clienteRepository;
    private final EventoRepository eventoRepository;
    private final ZonaRepository zonaRepository;
    private final AsientoRepository asientoRepository;
    public TicketSpecificoService(TicketSpecificoRepository ticketSpecificoRepository, ReservaRepository reservaRepository, ClienteRepository clienteRepository, EventoRepository eventoRepository, ZonaRepository zonaRepository, AsientoRepository asientoRepository) {
        this.ticketSpecificoRepository = ticketSpecificoRepository;
        this.reservaRepository = reservaRepository;
        this.clienteRepository = clienteRepository;
        this.eventoRepository = eventoRepository;
        this.zonaRepository = zonaRepository;
        this.asientoRepository = asientoRepository;
    }

    public TicketResponse crearTicket(TicketResponse dto) {
        TicketEspecifico ticket = new TicketEspecifico();

        ticket.setReserva(
                reservaRepository.findById(dto.getReservaId())
                        .orElseThrow(() -> new RuntimeException("Reserva no encontrada"))
        );
        ticket.setCliente(
                clienteRepository.findById(dto.getClienteId())
                        .orElseThrow(() -> new RuntimeException("Cliente no encontrado"))
        );
        ticket.setEvento(
                eventoRepository.findById(dto.getEventoId())
                        .orElseThrow(() -> new RuntimeException("Evento no encontrado"))
        );
        ticket.setZona(
                zonaRepository.findById(dto.getZonaId())
                        .orElseThrow(() -> new RuntimeException("Zona no encontrada"))
        );
        ticket.setAsiento(
                asientoRepository.findById(dto.getAsientoId())
                        .orElseThrow(() -> new RuntimeException("Asiento no encontrado"))
        );
        ticket.setTipo(dto.getTipo());
        ticket.setPrecioCompra(dto.getPrecioCompra());
        ticket.setDescuentoAplicado(dto.getDescuentoAplicado());
        ticket.setEstado(dto.getEstado());
        ticket.setPuntosGanados(dto.getPuntosGanados());
        ticket.setComision(dto.getComision());
        ticket.setCodigo(dto.getCodigoBase64().getBytes());
        ticket.setFechaEmision(LocalDateTime.now());
        ticket.setActivo(1);
        ticket = ticketSpecificoRepository.save(ticket);
        return toDTO(ticket);
    }

    public List<TicketResponse> listarTodos() {
        return ticketSpecificoRepository.findByActivo(1)
                .stream().map(this::toDTO).toList();
    }

    public List<TicketResponse> listarPorCliente(Integer idCliente) {
        return ticketSpecificoRepository.findByCliente_IdUsuarioAndActivo(idCliente, 1)
                .stream().map(this::toDTO).toList();
    }
    public List<TicketResponse> listarPorZona(Integer idZona) {
        return ticketSpecificoRepository.findByZona_IdZonaAndActivo(idZona, 1)
                .stream().map(this::toDTO).toList();
    }
    public List<TicketResponse> listarPorEvento(Integer idEvento) {
        return ticketSpecificoRepository.findByEvento_IdEventoAndActivo(idEvento, 1)
                .stream().map(this::toDTO).toList();
    }
    public List<TicketResponse> listarPorEventoYZona(Integer idEvento, Integer idZona) {
        return ticketSpecificoRepository.findByEvento_IdEventoAndZona_IdZonaAndActivo(idEvento, idZona, 1)
                .stream().map(this::toDTO).toList();
    }
    public List<TicketResponse> listarPorEstado(TipoEstadoTicket estado) {
        return ticketSpecificoRepository.findByEstadoAndActivo(estado, 1)
                .stream().map(this::toDTO).toList();
    }
    public TicketResponse modificarTicket(Integer idTicketEspecifico, TicketRequest dto) {

        // 1. Buscar el ticket existente
        TicketEspecifico ticket = ticketSpecificoRepository.findById(idTicketEspecifico)
                .orElseThrow(() -> new RuntimeException("Ticket no encontrado"));

        // 2. Actualizar relaciones SOLO si vienen en el DTO

        if (dto.getReservaId() != null) {
            Reserva reserva = reservaRepository.findById(dto.getReservaId())
                    .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
            ticket.setReserva(reserva);
        }

        if (dto.getClienteId() != null) {
            Cliente cliente = clienteRepository.findById(dto.getClienteId())
                    .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
            ticket.setCliente(cliente);
        }

        if (dto.getEventoId() != null) {
            Evento evento = eventoRepository.findById(dto.getEventoId())
                    .orElseThrow(() -> new RuntimeException("Evento no encontrado"));
            ticket.setEvento(evento);
        }

        if (dto.getZonaId() != null) {
            Zona zona = zonaRepository.findById(dto.getZonaId())
                    .orElseThrow(() -> new RuntimeException("Zona no encontrada"));
            ticket.setZona(zona);
        }

        if (dto.getAsientoId() != null) {
            Asiento asiento = asientoRepository.findById(dto.getAsientoId())
                    .orElseThrow(() -> new RuntimeException("Asiento no encontrado"));
            ticket.setAsiento(asiento);
        }

        // 3. Actualizar campos simples

        if (dto.getTipo() != null) {
            ticket.setTipo(dto.getTipo());
        }

        if (dto.getPrecioCompra() != null) {
            ticket.setPrecioCompra(dto.getPrecioCompra());
        }

        if (dto.getDescuentoAplicado() != null) {
            ticket.setDescuentoAplicado(dto.getDescuentoAplicado());
        }

        if (dto.getEstado() != null) {
            ticket.setEstado(dto.getEstado());
            // si quieres marcar fechaUso cuando pasa a USADO:
            // if (dto.getEstado() == TipoEstadoTicket.USADO) {
            //     ticket.setFechaUso(LocalDateTime.now());
            // }
        }



        if (dto.getPuntosGanados() != null) {
            ticket.setPuntosGanados(dto.getPuntosGanados());
        }

        if (dto.getComision() != null) {
            ticket.setComision(dto.getComision());
        }

        if (dto.getCodigo() != null) {
            ticket.setCodigo(dto.getCodigo());
        }

        // 4. Guardar cambios
        ticket = ticketSpecificoRepository.save(ticket);

        // 5. Devolver response
        return toDTO(ticket);
    }



    private TicketResponse toDTO(TicketEspecifico ticket) {
        TicketResponse dto = new TicketResponse();

        dto.setIdTicketEspecifico(ticket.getIdTicketEspecifico());

        // Reserva
        if (ticket.getReserva() != null) {
            dto.setReservaId(ticket.getReserva().getIdReserva());
        }

        // Cliente
        if (ticket.getCliente() != null) {
            dto.setClienteId(ticket.getCliente().getIdUsuario()); // o getIdCliente, según tu modelo
            dto.setNombreCliente(ticket.getCliente().getNombre()); // ajusta al nombre real del campo
        }

        // Evento
        if (ticket.getEvento() != null) {
            dto.setEventoId(ticket.getEvento().getIdEvento());
            dto.setNombreEvento(ticket.getEvento().getNombreEvento()); // ajusta al campo real
        }

        // Zona
        if (ticket.getZona() != null) {
            dto.setZonaId(ticket.getZona().getIdZona());
            dto.setNombreZona(ticket.getZona().getNombreZona());
        }

        // Asiento
        if (ticket.getAsiento() != null) {
            dto.setAsientoId(ticket.getAsiento().getIdAsiento());
            dto.setCodigoAsiento(ticket.getAsiento().getColumnaAsiento().concat(ticket.getAsiento().getColumnaAsiento())); // o fila/numero, según tu modelo
        }

        // Datos del ticket
        dto.setTipo(ticket.getTipo());
        dto.setPrecioCompra(ticket.getPrecioCompra());
        dto.setDescuentoAplicado(ticket.getDescuentoAplicado());
        dto.setEstado(ticket.getEstado());

        dto.setFechaEmision(ticket.getFechaEmision());
        dto.setFechaUso(ticket.getFechaUso());

        dto.setPuntosGanados(ticket.getPuntosGanados());
        dto.setComision(ticket.getComision());

        // Código binario -> Base64 (opcional)
        if (ticket.getCodigo() != null) {
            String base64 = Base64.getEncoder().encodeToString(ticket.getCodigo());
            dto.setCodigoBase64(base64);
        }

        dto.setActivo(ticket.getActivo());

        return dto;
    }
}
