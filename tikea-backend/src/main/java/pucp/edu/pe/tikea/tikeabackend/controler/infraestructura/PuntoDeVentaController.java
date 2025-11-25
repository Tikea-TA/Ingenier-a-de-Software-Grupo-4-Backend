package pucp.edu.pe.tikea.tikeabackend.controler.infraestructura;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pucp.edu.pe.tikea.tikeabackend.model.infraestructura.PuntoDeVenta;
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

    //           REGISTRAR
    @PostMapping
    public ResponseEntity<PuntoDeVenta> registrar(@RequestBody PuntoDeVenta punto) {
        return ResponseEntity.ok(puntoDeVentaService.registrar(punto));
    }

    //           MODIFICAR
    @PutMapping("/{id}")
    public ResponseEntity<PuntoDeVenta> modificar(
            @PathVariable Integer id,
            @RequestBody PuntoDeVenta nuevosDatos) {
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

    //        BUSCAR POR ID
    @GetMapping("/{id}")
    public ResponseEntity<PuntoDeVenta> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(puntoDeVentaService.buscarPorId(id));
    }

    //       LISTAR TODOS ACTIVOS
    @GetMapping("/activos")
    public ResponseEntity<List<PuntoDeVenta>> listarActivos() {
        return ResponseEntity.ok(puntoDeVentaService.listarActivos());
    }

    //       LISTAR TODOS
    @GetMapping
    public ResponseEntity<List<PuntoDeVenta>> listarTodos() {
        return ResponseEntity.ok(puntoDeVentaService.listarTodos());
    }

    //       BUSCAR POR TIPO
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<PuntoDeVenta>> buscarPorTipo(@PathVariable TipoPuntoVenta tipo) {
        return ResponseEntity.ok(puntoDeVentaService.buscarPorTipo(tipo));
    }

    //       BUSCAR ACTIVOS POR TIPO
    @GetMapping("/tipo/{tipo}/activos")
    public ResponseEntity<List<PuntoDeVenta>> buscarActivosPorTipo(@PathVariable TipoPuntoVenta tipo) {
        return ResponseEntity.ok(puntoDeVentaService.buscarActivosPorTipo(tipo));
    }

    //       BUSCAR POR ESTADO
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<PuntoDeVenta>> buscarPorEstado(@PathVariable TipoEstadoAproDes estado) {
        return ResponseEntity.ok(puntoDeVentaService.buscarPorEstado(estado));
    }

    //       BUSCAR ACTIVOS POR ESTADO
    @GetMapping("/estado/{estado}/activos")
    public ResponseEntity<List<PuntoDeVenta>> buscarActivosPorEstado(@PathVariable TipoEstadoAproDes estado) {
        return ResponseEntity.ok(puntoDeVentaService.buscarActivosPorEstado(estado));
    }

    //       BUSCAR POR RANGO DE FECHAS
    @GetMapping("/rango-fechas")
    public ResponseEntity<List<PuntoDeVenta>> buscarPorRangoFechas(
            @RequestParam LocalDateTime inicio,
            @RequestParam LocalDateTime fin) {
        return ResponseEntity.ok(puntoDeVentaService.buscarPorRangoFechas(inicio, fin));
    }

    //       BUSCAR ACTIVOS POR RANGO DE OPERACIÓN
    @GetMapping("/rango-fechas/activos")
    public ResponseEntity<List<PuntoDeVenta>> buscarActivosPorRangoOperacion(
            @RequestParam LocalDateTime inicio,
            @RequestParam LocalDateTime fin) {
        return ResponseEntity.ok(puntoDeVentaService.buscarActivosPorRangoOperacion(inicio, fin));
    }
}
