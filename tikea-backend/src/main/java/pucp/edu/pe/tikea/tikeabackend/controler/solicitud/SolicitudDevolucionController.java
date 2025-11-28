package pucp.edu.pe.tikea.tikeabackend.controler.solicitud;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pucp.edu.pe.tikea.tikeabackend.DTO.solicitud.SolcitudDevolucionRequest;
import pucp.edu.pe.tikea.tikeabackend.DTO.solicitud.SolicitudDevolucionResponse;
import pucp.edu.pe.tikea.tikeabackend.services.solicitud.SolicitudDevolucionService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/solicitudes-devolucion")
@RequiredArgsConstructor
public class SolicitudDevolucionController {

    private final SolicitudDevolucionService servicio;

    //           REGISTRAR (Usa DTO Request para entrada y DTO Response para salida)
    @PostMapping
    public ResponseEntity<SolicitudDevolucionResponse> registrar(@RequestBody SolcitudDevolucionRequest solicitud) {
        SolicitudDevolucionResponse response = servicio.registrar(solicitud);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //           MODIFICAR (Implementado para permitir actualizaciones parciales con DTO Request)
    @PutMapping("/{id}")
    public ResponseEntity<SolicitudDevolucionResponse> modificar(
            @PathVariable Integer id,
            @RequestBody SolcitudDevolucionRequest nuevosDatos) {
        return ResponseEntity.ok(servicio.modificar(id, nuevosDatos));
    }

    //       ELIMINACIÓN LÓGICA
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarLogico(@PathVariable Integer id) {
        boolean eliminado = servicio.eliminarLogico(id);
        return eliminado ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    //           BUSCAR POR ID (Retorna DTO Response)
    @GetMapping("/{id}")
    public ResponseEntity<SolicitudDevolucionResponse> obtenerPorId(@PathVariable Integer id) {
        Optional<SolicitudDevolucionResponse> solicitud = servicio.obtenerPorId(id);
        return solicitud.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //        LISTAR TODOS (Retorna DTO Response List)
    @GetMapping
    public ResponseEntity<List<SolicitudDevolucionResponse>> listarTodos() {
        return ResponseEntity.ok(servicio.listarTodos());
    }

    //       LISTAR SOLO ACTIVOS (Retorna DTO Response List)
    @GetMapping("/activos")
    public ResponseEntity<List<SolicitudDevolucionResponse>> listarActivos() {
        return ResponseEntity.ok(servicio.listarActivos());
    }

    //       BUSCAR POR CLIENTE (Ahora recibe Integer y retorna DTO Response List)
    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<List<SolicitudDevolucionResponse>> buscarPorCliente(@PathVariable Integer idCliente) {
        return ResponseEntity.ok(servicio.buscarPorCliente(idCliente));
    }

    //       BUSCAR ACTIVOS POR CLIENTE (Ahora recibe Integer y retorna DTO Response List)
    @GetMapping("/cliente/{idCliente}/activos")
    public ResponseEntity<List<SolicitudDevolucionResponse>> buscarActivosPorCliente(@PathVariable Integer idCliente) {
        return ResponseEntity.ok(servicio.buscarActivosPorCliente(idCliente));
    }

    //       BUSCAR POR TICKET (Ahora recibe Integer y retorna DTO Response List)
    @GetMapping("/ticket/{idTicket}")
    public ResponseEntity<List<SolicitudDevolucionResponse>> buscarPorTicket(@PathVariable Integer idTicket) {
        return ResponseEntity.ok(servicio.buscarPorTicket(idTicket));
    }

    //       BUSCAR ACTIVOS POR TICKET (Ahora recibe Integer y retorna DTO Response List)
    @GetMapping("/ticket/{idTicket}/activos")
    public ResponseEntity<List<SolicitudDevolucionResponse>> buscarActivosPorTicket(@PathVariable Integer idTicket) {
        return ResponseEntity.ok(servicio.buscarActivosPorTicket(idTicket));
    }

    //       BUSCAR POR POLÍTICA (Retorna DTO Response List)
    @GetMapping("/politica/{aplicada}")
    public ResponseEntity<List<SolicitudDevolucionResponse>> buscarPorPolitica(@PathVariable Boolean aplicada) {
        return ResponseEntity.ok(servicio.buscarPorPolitica(aplicada));
    }

    //       BUSCAR POR REINCORPORACIÓN (Retorna DTO Response List)
    @GetMapping("/reincorporacion/{reincorporado}")
    public ResponseEntity<List<SolicitudDevolucionResponse>> buscarPorReincorporacion(@PathVariable Boolean reincorporado) {
        return ResponseEntity.ok(servicio.buscarPorReincorporacion(reincorporado));
    }
}
