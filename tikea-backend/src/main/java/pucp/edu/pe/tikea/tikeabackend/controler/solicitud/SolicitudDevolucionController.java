package pucp.edu.pe.tikea.tikeabackend.controler.solicitud;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pucp.edu.pe.tikea.tikeabackend.model.solicitud.SolicitudDevolucion;
import pucp.edu.pe.tikea.tikeabackend.model.usuarios.Cliente;
import pucp.edu.pe.tikea.tikeabackend.model.infraestructura.TicketEspecifico;
import pucp.edu.pe.tikea.tikeabackend.services.solicitud.SolicitudDevolucionService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/solicitudes-devolucion")
@RequiredArgsConstructor
public class SolicitudDevolucionController {

    private final SolicitudDevolucionService servicio;

    //           REGISTRAR
    @PostMapping
    public ResponseEntity<SolicitudDevolucion> registrar(@RequestBody SolicitudDevolucion solicitud) {
        return ResponseEntity.ok(servicio.registrar(solicitud));
    }

    //       ELIMINACIÓN LÓGICA
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarLogico(@PathVariable Integer id) {
        boolean eliminado = servicio.eliminarLogico(id);
        return eliminado ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    //           BUSCAR POR ID
    @GetMapping("/{id}")
    public ResponseEntity<SolicitudDevolucion> obtenerPorId(@PathVariable Integer id) {
        Optional<SolicitudDevolucion> solicitud = servicio.obtenerPorId(id);
        return solicitud.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //        LISTAR TODOS
    @GetMapping
    public ResponseEntity<List<SolicitudDevolucion>> listarTodos() {
        return ResponseEntity.ok(servicio.listarTodos());
    }

    //       LISTAR SOLO ACTIVOS
    @GetMapping("/activos")
    public ResponseEntity<List<SolicitudDevolucion>> listarActivos() {
        return ResponseEntity.ok(servicio.listarActivos());
    }

    //       BUSCAR POR CLIENTE
    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<List<SolicitudDevolucion>> buscarPorCliente(@PathVariable Integer idCliente) {
        Cliente cliente = new Cliente();
        cliente.setIdUsuario(idCliente);
        return ResponseEntity.ok(servicio.buscarPorCliente(cliente));
    }

    @GetMapping("/cliente/{idCliente}/activos")
    public ResponseEntity<List<SolicitudDevolucion>> buscarActivosPorCliente(@PathVariable Integer idCliente) {
        Cliente cliente = new Cliente();
        cliente.setIdUsuario(idCliente);
        return ResponseEntity.ok(servicio.buscarActivosPorCliente(cliente));
    }

    //       BUSCAR POR TICKET
    @GetMapping("/ticket/{idTicket}")
    public ResponseEntity<List<SolicitudDevolucion>> buscarPorTicket(@PathVariable Integer idTicket) {
        TicketEspecifico ticket = new TicketEspecifico();
        ticket.setIdTicketEspecifico(idTicket);
        return ResponseEntity.ok(servicio.buscarPorTicket(ticket));
    }

    @GetMapping("/ticket/{idTicket}/activos")
    public ResponseEntity<List<SolicitudDevolucion>> buscarActivosPorTicket(@PathVariable Integer idTicket) {
        TicketEspecifico ticket = new TicketEspecifico();
        ticket.setIdTicketEspecifico(idTicket);
        return ResponseEntity.ok(servicio.buscarActivosPorTicket(ticket));
    }

    //       BUSCAR POR POLÍTICA
    @GetMapping("/politica/{aplicada}")
    public ResponseEntity<List<SolicitudDevolucion>> buscarPorPolitica(@PathVariable Boolean aplicada) {
        return ResponseEntity.ok(servicio.buscarPorPolitica(aplicada));
    }

    //       BUSCAR POR REINCORPORACIÓN
    @GetMapping("/reincorporacion/{reincorporado}")
    public ResponseEntity<List<SolicitudDevolucion>> buscarPorReincorporacion(@PathVariable Boolean reincorporado) {
        return ResponseEntity.ok(servicio.buscarPorReincorporacion(reincorporado));
    }
}
