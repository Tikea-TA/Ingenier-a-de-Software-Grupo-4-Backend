package pucp.edu.pe.tikea.tikeabackend.controler.usuario;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pucp.edu.pe.tikea.tikeabackend.model.usuarios.Taquillero;
import pucp.edu.pe.tikea.tikeabackend.model.infraestructura.PuntoDeVenta;
import pucp.edu.pe.tikea.tikeabackend.services.usuarios.cliente.TaquilleroService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/taquilleros")
@RequiredArgsConstructor
public class TaquilleroController {
    private final TaquilleroService servicio;

    //           REGISTRAR
    @PostMapping
    public ResponseEntity<Taquillero> registrar(@RequestBody Taquillero taquillero) {
        return ResponseEntity.ok(servicio.registrar(taquillero));
    }

    //          ACTUALIZAR
    @PutMapping("/{id}")
    public ResponseEntity<Taquillero> actualizar(@PathVariable Integer id,@RequestBody Taquillero taquillero) {
        taquillero.setIdUsuario(id);
        return ResponseEntity.ok(servicio.actualizar(taquillero));
    }

    //       ELIMINACIÓN LÓGICA
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarLogico(@PathVariable Integer id) {
        boolean eliminado = servicio.eliminarLogico(id);
        return eliminado ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    //         LISTAR TODOS
    @GetMapping
    public ResponseEntity<List<Taquillero>> listarTodos() {
        return ResponseEntity.ok(servicio.listarTodos());
    }

    //      BUSCAR POR ID
    @GetMapping("/{id}")
    public ResponseEntity<Taquillero> obtenerPorId(@PathVariable Integer id) {
        Optional<Taquillero> opt = servicio.obtenerPorId(id);
        return opt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //     BUSCAR POR PUNTO DE VENTA
    @GetMapping("/punto-venta/{idPunto}")
    public ResponseEntity<List<Taquillero>> listarPorPuntoVenta(@PathVariable Integer idPunto) {
        PuntoDeVenta pv = new PuntoDeVenta();
        pv.setIdPuntoDeVenta(idPunto);
        return ResponseEntity.ok(servicio.listarPorPuntoVenta(pv));
    }

    //        BUSCAR POR ROL
    @GetMapping("/rol/{rol}")
    public ResponseEntity<List<Taquillero>> listarPorRol(@PathVariable String rol) {
        return ResponseEntity.ok(servicio.listarPorRol(rol));
    }

    //  BUSCAR CON LOCALES ASIGNADOS
    @GetMapping("/locales-asignados/{cantidad}")
    public ResponseEntity<List<Taquillero>> listarConLocalesAsignadosMayorIgual(@PathVariable int cantidad) {
        return ResponseEntity.ok(servicio.listarConLocalesAsignadosMayorIgual(cantidad));
    }

    //   BUSCAR POR FECHAS DE ASIGNACIÓN
    @GetMapping("/asignacion/inicio/despues/{fecha}")
    public ResponseEntity<List<Taquillero>> listarInicioAsignacionDespues(@PathVariable String fecha) {
        LocalDateTime fechaDt = LocalDateTime.parse(fecha);
        return ResponseEntity.ok(servicio.listarInicioAsignacionDespues(fechaDt));
    }

    @GetMapping("/asignacion/fin/antes/{fecha}")
    public ResponseEntity<List<Taquillero>> listarFinAsignacionAntes(@PathVariable String fecha) {
        LocalDateTime fechaDt = LocalDateTime.parse(fecha);
        return ResponseEntity.ok(servicio.listarFinAsignacionAntes(fechaDt));
    }

    //   BUSCAR POR PUNTO DE VENTA Y ROL
    @GetMapping("/punto-venta/{idPunto}/rol/{rol}")
    public ResponseEntity<List<Taquillero>> buscarPorPuntoDeVentaYRol(
            @PathVariable Integer idPunto,
            @PathVariable String rol) {
        PuntoDeVenta pv = new PuntoDeVenta();
        pv.setIdPuntoDeVenta(idPunto);
        return ResponseEntity.ok(servicio.buscarPorPuntoVentaYRol(pv, rol));
    }

    //   BUSCAR ASIGNACIÓN VIGENTE
    @GetMapping("/asignacion/vigente")
    public ResponseEntity<List<Taquillero>> buscarAsignacionVigente() {
        LocalDateTime ahora = LocalDateTime.now();
        return ResponseEntity.ok(servicio.buscarAsignacionVigente(ahora));
    }

    //   BUSCAR PRIMERO POR PUNTO DE VENTA Y ROL
    @GetMapping("/primero/punto-venta/{idPunto}/rol/{rol}")
    public ResponseEntity<Taquillero> buscarPrimeroPorPuntoDeVentaYRol(
            @PathVariable Integer idPunto,
            @PathVariable String rol) {
        PuntoDeVenta pv = new PuntoDeVenta();
        pv.setIdPuntoDeVenta(idPunto);
        Optional<Taquillero> opt = servicio.buscarPrimeroPorPuntoDeVentaYRol(pv, rol);
        return opt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

}
