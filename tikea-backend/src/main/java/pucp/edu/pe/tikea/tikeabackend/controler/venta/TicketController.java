package pucp.edu.pe.tikea.tikeabackend.controler.venta;

import org.springframework.web.bind.annotation.*;
import pucp.edu.pe.tikea.tikeabackend.DTO.venta.TicketRequest;
import pucp.edu.pe.tikea.tikeabackend.DTO.venta.TicketResponse;
import pucp.edu.pe.tikea.tikeabackend.model.infraestructura.TipoEstadoTicket;
import pucp.edu.pe.tikea.tikeabackend.services.venta.TicketSpecificoService;

import java.util.List;
@RestController
@RequestMapping("/api/tiket")
public class TicketController {
    private final TicketSpecificoService ticketSpecificoService;

    public TicketController(TicketSpecificoService ticketSpecificoService) {
        this.ticketSpecificoService = ticketSpecificoService;
    }
    @PostMapping("/crear")
    public TicketResponse crearTicket(@RequestBody TicketResponse dto) {
        return ticketSpecificoService.crearTicket(dto);
    }

    @PutMapping("/{idTicket}")
    public TicketResponse modificarTicket(
            @PathVariable Integer idTicket,
            @RequestBody TicketRequest dto
    ) {
        return ticketSpecificoService.modificarTicket(idTicket, dto);
    }

    @GetMapping
    public List<TicketResponse> listarTodos() {
        return ticketSpecificoService.listarTodos();
    }

    @GetMapping("/cliente/{idCliente}")
    public List<TicketResponse> listarPorCliente(
            @PathVariable Integer idCliente) {
        return ticketSpecificoService.listarPorCliente(idCliente);
    }

    @GetMapping("/zona/{idZona}")
    public List<TicketResponse> listarPorZona(
            @PathVariable Integer idZona) {
        return ticketSpecificoService.listarPorZona(idZona);
    }

    @GetMapping("/evento/{idEvento}")
    public List<TicketResponse> listarPorEvento(
            @PathVariable Integer idEvento) {
        return ticketSpecificoService.listarPorEvento(idEvento);
    }

    @GetMapping("/evento/{idEvento}/zona/{idZona}")
    public List<TicketResponse> listarPorEventoYZona(
            @PathVariable Integer idEvento,
            @PathVariable Integer idZona) {
        return ticketSpecificoService.listarPorEventoYZona(idEvento, idZona);
    }

    @GetMapping("/estado/{estado}")
    public List<TicketResponse> listarPorEstado(
            @PathVariable TipoEstadoTicket estado) {
        return ticketSpecificoService.listarPorEstado(estado);
    }
}
