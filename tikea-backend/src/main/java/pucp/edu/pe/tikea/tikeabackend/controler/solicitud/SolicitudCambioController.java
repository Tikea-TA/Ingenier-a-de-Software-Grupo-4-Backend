package pucp.edu.pe.tikea.tikeabackend.controler.solicitud;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pucp.edu.pe.tikea.tikeabackend.DTO.solicitud.SolicitudCambioRequest;
import pucp.edu.pe.tikea.tikeabackend.DTO.solicitud.SolicitudCambioResponse;
import pucp.edu.pe.tikea.tikeabackend.model.solicitud.TipoOperacionCambio;
import pucp.edu.pe.tikea.tikeabackend.services.solicitud.SolicitudCambioService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/solicitudes-cambio")
@RequiredArgsConstructor
public class SolicitudCambioController {

    private final SolicitudCambioService solicitudCambioService;

    //           REGISTRAR (Usa DTO Request para entrada y DTO Response para salida)
    @PostMapping
    public ResponseEntity<SolicitudCambioResponse> registrar(@RequestBody SolicitudCambioRequest request) {
        SolicitudCambioResponse response = solicitudCambioService.registrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //           MODIFICAR (Usa DTO Request para entrada y DTO Response para salida)
    @PutMapping("/{id}")
    public ResponseEntity<SolicitudCambioResponse> modificar(
            @PathVariable Integer id,
            @RequestBody SolicitudCambioRequest nuevosDatos) {
        // El servicio maneja la actualización parcial, ignorando campos nulos del request.
        return ResponseEntity.ok(solicitudCambioService.modificar(id, nuevosDatos));
    }

    //       ELIMINACIÓN LÓGICA
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarLogico(@PathVariable Integer id) {
        solicitudCambioService.eliminarLogico(id);
        return ResponseEntity.noContent().build();
    }

    //       ACTIVAR LÓGICAMENTE
    @PutMapping("/{id}/activar")
    public ResponseEntity<Void> activar(@PathVariable Integer id) {
        solicitudCambioService.activar(id);
        return ResponseEntity.noContent().build();
    }

    //           BUSCAR POR ID (Retorna DTO Response)
    @GetMapping("/{id}")
    public ResponseEntity<SolicitudCambioResponse> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(solicitudCambioService.buscarPorId(id));
    }

    //        LISTAR TODOS (Retorna DTO Response List)
    @GetMapping
    public ResponseEntity<List<SolicitudCambioResponse>> listarTodos() {
        return ResponseEntity.ok(solicitudCambioService.listarTodos());
    }

    //       LISTAR SOLO ACTIVOS (Retorna DTO Response List)
    @GetMapping("/activos")
    public ResponseEntity<List<SolicitudCambioResponse>> listarActivos() {
        return ResponseEntity.ok(solicitudCambioService.listarActivos());
    }

    //       BUSCAR POR CLIENTE (Ahora recibe Integer y retorna DTO Response List)
    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<List<SolicitudCambioResponse>> buscarPorCliente(@PathVariable Integer idCliente) {
        return ResponseEntity.ok(solicitudCambioService.buscarPorCliente(idCliente));
    }

    //       BUSCAR ACTIVOS POR CLIENTE (Ahora recibe Integer y retorna DTO Response List)
    @GetMapping("/cliente/{idCliente}/activos")
    public ResponseEntity<List<SolicitudCambioResponse>> buscarActivosPorCliente(@PathVariable Integer idCliente) {
        return ResponseEntity.ok(solicitudCambioService.buscarActivosPorCliente(idCliente));
    }

    //       BUSCAR POR RESERVA (Ahora recibe Integer y retorna DTO Response List)
    @GetMapping("/reserva/{idReserva}")
    public ResponseEntity<List<SolicitudCambioResponse>> buscarPorReserva(@PathVariable Integer idReserva) {
        return ResponseEntity.ok(solicitudCambioService.buscarPorReserva(idReserva));
    }

    //       BUSCAR POR TIPO DE OPERACIÓN (Retorna DTO Response List)
    @GetMapping("/tipo/{tipoOperacion}")
    public ResponseEntity<List<SolicitudCambioResponse>> buscarPorTipo(@PathVariable TipoOperacionCambio tipoOperacion) {
        return ResponseEntity.ok(solicitudCambioService.buscarPorTipo(tipoOperacion));
    }

    //       BUSCAR POR DISPONIBILIDAD VALIDADA (Retorna DTO Response List)
    @GetMapping("/disponibilidad/{validada}")
    public ResponseEntity<List<SolicitudCambioResponse>> buscarPorDisponibilidad(@PathVariable Boolean validada) {
        return ResponseEntity.ok(solicitudCambioService.buscarPorDisponibilidad(validada));
    }

    //       BUSCAR POR RANGO DE FECHAS (Retorna DTO Response List)
    @GetMapping("/rango-fechas")
    public ResponseEntity<List<SolicitudCambioResponse>> buscarPorRangoFechas(
            @RequestParam LocalDateTime inicio,
            @RequestParam LocalDateTime fin) {
        return ResponseEntity.ok(solicitudCambioService.buscarPorRangoFechas(inicio, fin));
    }

    //       BUSCAR ACTIVOS POR RANGO DE FECHAS (Retorna DTO Response List)
    @GetMapping("/rango-fechas/activos")
    public ResponseEntity<List<SolicitudCambioResponse>> buscarActivosPorFechas(
            @RequestParam LocalDateTime inicio,
            @RequestParam LocalDateTime fin) {
        return ResponseEntity.ok(solicitudCambioService.buscarActivosPorFechas(inicio, fin));
    }
}