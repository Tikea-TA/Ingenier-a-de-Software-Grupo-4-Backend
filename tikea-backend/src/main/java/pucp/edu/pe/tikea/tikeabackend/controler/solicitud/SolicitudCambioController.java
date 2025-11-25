package pucp.edu.pe.tikea.tikeabackend.controler.solicitud;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pucp.edu.pe.tikea.tikeabackend.model.solicitud.SolicitudCambio;
import pucp.edu.pe.tikea.tikeabackend.model.solicitud.TipoOperacionCambio;
import pucp.edu.pe.tikea.tikeabackend.model.usuarios.Cliente;
import pucp.edu.pe.tikea.tikeabackend.model.venta.Reserva;
import pucp.edu.pe.tikea.tikeabackend.services.solicitud.SolicitudCambioService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/solicitudes-cambio")
@RequiredArgsConstructor
public class SolicitudCambioController {

    private final SolicitudCambioService solicitudCambioService;

    //           REGISTRAR
    @PostMapping
    public ResponseEntity<SolicitudCambio> registrar(@RequestBody SolicitudCambio solicitud) {
        return ResponseEntity.ok(solicitudCambioService.registrar(solicitud));
    }

    //           MODIFICAR
    @PutMapping("/{id}")
    public ResponseEntity<SolicitudCambio> modificar(
            @PathVariable Integer id,
            @RequestBody SolicitudCambio nuevosDatos) {
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

    //           BUSCAR POR ID
    @GetMapping("/{id}")
    public ResponseEntity<SolicitudCambio> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(solicitudCambioService.buscarPorId(id));
    }

    //        LISTAR TODOS
    @GetMapping
    public ResponseEntity<List<SolicitudCambio>> listarTodos() {
        return ResponseEntity.ok(solicitudCambioService.listarTodos());
    }

    //       LISTAR SOLO ACTIVOS
    @GetMapping("/activos")
    public ResponseEntity<List<SolicitudCambio>> listarActivos() {
        return ResponseEntity.ok(solicitudCambioService.listarActivos());
    }

    //       BUSCAR POR CLIENTE
    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<List<SolicitudCambio>> buscarPorCliente(@PathVariable Integer idCliente) {
        Cliente cliente = new Cliente();
        cliente.setIdUsuario(idCliente);
        return ResponseEntity.ok(solicitudCambioService.buscarPorCliente(cliente));
    }

    @GetMapping("/cliente/{idCliente}/activos")
    public ResponseEntity<List<SolicitudCambio>> buscarActivosPorCliente(@PathVariable Integer idCliente) {
        Cliente cliente = new Cliente();
        cliente.setIdUsuario(idCliente);
        return ResponseEntity.ok(solicitudCambioService.buscarActivosPorCliente(cliente));
    }

    //       BUSCAR POR RESERVA
    @GetMapping("/reserva/{idReserva}")
    public ResponseEntity<List<SolicitudCambio>> buscarPorReserva(@PathVariable Integer idReserva) {
        Reserva reserva = new Reserva();
        reserva.setIdReserva(idReserva);
        return ResponseEntity.ok(solicitudCambioService.buscarPorReserva(reserva));
    }

    //       BUSCAR POR TIPO DE OPERACIÓN
    @GetMapping("/tipo/{tipoOperacion}")
    public ResponseEntity<List<SolicitudCambio>> buscarPorTipo(@PathVariable TipoOperacionCambio tipoOperacion) {
        return ResponseEntity.ok(solicitudCambioService.buscarPorTipo(tipoOperacion));
    }

    //       BUSCAR POR DISPONIBILIDAD VALIDADA
    @GetMapping("/disponibilidad/{validada}")
    public ResponseEntity<List<SolicitudCambio>> buscarPorDisponibilidad(@PathVariable Boolean validada) {
        return ResponseEntity.ok(solicitudCambioService.buscarPorDisponibilidad(validada));
    }

    //       BUSCAR POR RANGO DE FECHAS
    @GetMapping("/rango-fechas")
    public ResponseEntity<List<SolicitudCambio>> buscarPorRangoFechas(
            @RequestParam LocalDateTime inicio,
            @RequestParam LocalDateTime fin) {
        return ResponseEntity.ok(solicitudCambioService.buscarPorRangoFechas(inicio, fin));
    }

    //       BUSCAR ACTIVOS POR RANGO DE FECHAS
    @GetMapping("/rango-fechas/activos")
    public ResponseEntity<List<SolicitudCambio>> buscarActivosPorFechas(
            @RequestParam LocalDateTime inicio,
            @RequestParam LocalDateTime fin) {
        return ResponseEntity.ok(solicitudCambioService.buscarActivosPorFechas(inicio, fin));
    }
}
