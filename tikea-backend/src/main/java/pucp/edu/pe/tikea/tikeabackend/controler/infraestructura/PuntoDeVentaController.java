package pucp.edu.pe.tikea.tikeabackend.controler.infraestructura;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pucp.edu.pe.tikea.tikeabackend.DTO.infraestructura.PuntoDeVentaRequest;
import pucp.edu.pe.tikea.tikeabackend.DTO.infraestructura.PuntoDeVentaResponse;
import pucp.edu.pe.tikea.tikeabackend.model.infraestructura.TipoEstadoAproDes;
import pucp.edu.pe.tikea.tikeabackend.model.infraestructura.TipoPuntoVenta;
import pucp.edu.pe.tikea.tikeabackend.services.infraestructura.PuntoDeVentaService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/puntos-venta")
@RequiredArgsConstructor
public class PuntoDeVentaController {

    private final PuntoDeVentaService puntoDeVentaService;

    //           REGISTRAR (Usa DTO Request para entrada y DTO Response para salida)
    @PostMapping
    public ResponseEntity<PuntoDeVentaResponse> registrar(@RequestBody PuntoDeVentaRequest request) {
        PuntoDeVentaResponse response = puntoDeVentaService.registrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //           MODIFICAR (Usa DTO Request para entrada y DTO Response para salida)
    @PutMapping("/{id}")
    public ResponseEntity<PuntoDeVentaResponse> modificar(
            @PathVariable Integer id,
            @RequestBody PuntoDeVentaRequest nuevosDatos) {
        // El servicio maneja la actualización parcial, ignorando campos nulos del request.
        return ResponseEntity.ok(puntoDeVentaService.modificar(id, nuevosDatos));
    }

    //       ELIMINACIÓN LÓGICA
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarLogico(@PathVariable Integer id) {
        puntoDeVentaService.eliminarLogico(id);
        return ResponseEntity.noContent().build();
    }

    //           ACTIVAR
    @PutMapping("/{id}/activar")
    public ResponseEntity<Void> activar(@PathVariable Integer id) {
        puntoDeVentaService.activar(id);
        return ResponseEntity.noContent().build();
    }

    //        BUSCAR POR ID (Retorna DTO Response)
    @GetMapping("/{id}")
    public ResponseEntity<PuntoDeVentaResponse> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(puntoDeVentaService.buscarPorId(id));
    }

    //       LISTAR TODOS ACTIVOS (Retorna DTO Response List)
    @GetMapping("/activos")
    public ResponseEntity<List<PuntoDeVentaResponse>> listarActivos() {
        return ResponseEntity.ok(puntoDeVentaService.listarActivos());
    }

    //       LISTAR TODOS (Retorna DTO Response List)
    @GetMapping
    public ResponseEntity<List<PuntoDeVentaResponse>> listarTodos() {
        return ResponseEntity.ok(puntoDeVentaService.listarTodos());
    }

    //       BUSCAR POR TIPO (Retorna DTO Response List)
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<PuntoDeVentaResponse>> buscarPorTipo(@PathVariable TipoPuntoVenta tipo) {
        return ResponseEntity.ok(puntoDeVentaService.buscarPorTipo(tipo));
    }

    //       BUSCAR ACTIVOS POR TIPO (Retorna DTO Response List)
    @GetMapping("/tipo/{tipo}/activos")
    public ResponseEntity<List<PuntoDeVentaResponse>> buscarActivosPorTipo(@PathVariable TipoPuntoVenta tipo) {
        return ResponseEntity.ok(puntoDeVentaService.buscarActivosPorTipo(tipo));
    }

    //       BUSCAR POR ESTADO (Retorna DTO Response List)
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<PuntoDeVentaResponse>> buscarPorEstado(@PathVariable TipoEstadoAproDes estado) {
        return ResponseEntity.ok(puntoDeVentaService.buscarPorEstado(estado));
    }

    //       BUSCAR ACTIVOS POR ESTADO (Retorna DTO Response List)
    @GetMapping("/estado/{estado}/activos")
    public ResponseEntity<List<PuntoDeVentaResponse>> buscarActivosPorEstado(@PathVariable TipoEstadoAproDes estado) {
        return ResponseEntity.ok(puntoDeVentaService.buscarActivosPorEstado(estado));
    }

    //       BUSCAR POR RANGO DE FECHAS (Retorna DTO Response List)
    @GetMapping("/rango-fechas")
    public ResponseEntity<List<PuntoDeVentaResponse>> buscarPorRangoFechas(
            @RequestParam LocalDateTime inicio,
            @RequestParam LocalDateTime fin) {
        return ResponseEntity.ok(puntoDeVentaService.buscarPorRangoFechas(inicio, fin));
    }

    //       BUSCAR ACTIVOS POR RANGO DE OPERACIÓN (Retorna DTO Response List)
    @GetMapping("/rango-fechas/activos")
    public ResponseEntity<List<PuntoDeVentaResponse>> buscarActivosPorRangoOperacion(
            @RequestParam LocalDateTime inicio,
            @RequestParam LocalDateTime fin) {
        return ResponseEntity.ok(puntoDeVentaService.buscarActivosPorRangoOperacion(inicio, fin));
    }
}
