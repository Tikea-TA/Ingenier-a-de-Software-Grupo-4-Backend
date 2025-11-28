package pucp.edu.pe.tikea.tikeabackend.controler.usuario;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pucp.edu.pe.tikea.tikeabackend.DTO.usuarios.TaquilleroRequest;
import pucp.edu.pe.tikea.tikeabackend.DTO.usuarios.TaquilleroResponse;
import pucp.edu.pe.tikea.tikeabackend.model.infraestructura.PuntoDeVenta;
import pucp.edu.pe.tikea.tikeabackend.services.usuarios.cliente.TaquilleroService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/taquilleros")
@RequiredArgsConstructor
public class TaquilleroController {
    private final TaquilleroService servicio;

    //           REGISTRAR - Ahora recibe DTO
    @PostMapping
    public ResponseEntity<TaquilleroResponse> registrar(@RequestBody TaquilleroRequest dto) {
        return ResponseEntity.ok(servicio.registrar(dto));
    }

    //          ACTUALIZAR - Ahora recibe DTO
    @PutMapping("/{id}")
    public ResponseEntity<TaquilleroResponse> actualizar(
            @PathVariable Integer id,
            @RequestBody TaquilleroRequest dto) {
        return ResponseEntity.ok(servicio.actualizar(id,dto));
    }

    //       ELIMINACIÓN LÓGICA - Ahora devuelve DTO
    @DeleteMapping("/{id}")
    public ResponseEntity<TaquilleroResponse> eliminarLogico(@PathVariable Integer id) {
        return ResponseEntity.ok(servicio.eliminarLogico(id));
    }

    //         LISTAR TODOS - Ahora devuelve lista de DTOs
    @GetMapping
    public ResponseEntity<List<TaquilleroResponse>> listarTodos() {
        return ResponseEntity.ok(servicio.listarTodos());
    }

    //      BUSCAR POR ID - Ahora devuelve DTO
    @GetMapping("/{id}")
    public ResponseEntity<TaquilleroResponse> obtenerPorId(@PathVariable Integer id) {
        return servicio.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    //     BUSCAR POR PUNTO DE VENTA
    @GetMapping("/punto-venta/{idPunto}")
    public ResponseEntity<List<TaquilleroResponse>> listarPorPuntoVenta(@PathVariable Integer idPunto) {
        // Nota: En un entorno real, se debería buscar la entidad PuntoDeVenta en un servicio
        PuntoDeVenta pv = new PuntoDeVenta();
        pv.setIdPuntoDeVenta(idPunto);
        return ResponseEntity.ok(servicio.listarPorPuntoVenta(pv));
    }

    //        BUSCAR POR ROL
    @GetMapping("/rol/{rol}")
    public ResponseEntity<List<TaquilleroResponse>> listarPorRol(@PathVariable String rol) {
        return ResponseEntity.ok(servicio.listarPorRol(rol));
    }

    //  BUSCAR CON LOCALES ASIGNADOS
    @GetMapping("/locales-asignados/{cantidad}")
    public ResponseEntity<List<TaquilleroResponse>> listarConLocalesAsignadosMayorIgual(@PathVariable int cantidad) {
        return ResponseEntity.ok(servicio.listarConLocalesAsignadosMayorIgual(cantidad));
    }

    //   BUSCAR POR FECHAS DE ASIGNACIÓN
    @GetMapping("/asignacion/inicio/despues/{fecha}")
    public ResponseEntity<List<TaquilleroResponse>> listarInicioAsignacionDespues(@PathVariable String fecha) {
        // Conversión a LocalDateTime. Se recomienda usar @DateTimeFormat o un DTO de filtro
        LocalDateTime fechaDt = LocalDateTime.parse(fecha);
        return ResponseEntity.ok(servicio.listarInicioAsignacionDespues(fechaDt));
    }

    @GetMapping("/asignacion/fin/antes/{fecha}")
    public ResponseEntity<List<TaquilleroResponse>> listarFinAsignacionAntes(@PathVariable String fecha) {
        // Conversión a LocalDateTime. Se recomienda usar @DateTimeFormat o un DTO de filtro
        LocalDateTime fechaDt = LocalDateTime.parse(fecha);
        return ResponseEntity.ok(servicio.listarFinAsignacionAntes(fechaDt));
    }

    //   BUSCAR POR PUNTO DE VENTA Y ROL
    @GetMapping("/punto-venta/{idPunto}/rol/{rol}")
    public ResponseEntity<List<TaquilleroResponse>> buscarPorPuntoDeVentaYRol(
            @PathVariable Integer idPunto,
            @PathVariable String rol) {
        // Nota: En un entorno real, se debería buscar la entidad PuntoDeVenta en un servicio
        PuntoDeVenta pv = new PuntoDeVenta();
        pv.setIdPuntoDeVenta(idPunto);
        return ResponseEntity.ok(servicio.buscarPorPuntoVentaYRol(pv, rol));
    }

    //   BUSCAR ASIGNACIÓN VIGENTE
    @GetMapping("/asignacion/vigente")
    public ResponseEntity<List<TaquilleroResponse>> buscarAsignacionVigente() {
        LocalDateTime ahora = LocalDateTime.now();
        return ResponseEntity.ok(servicio.buscarAsignacionVigente(ahora));
    }

    //   BUSCAR PRIMERO POR PUNTO DE VENTA Y ROL
    @GetMapping("/primero/punto-venta/{idPunto}/rol/{rol}")
    public ResponseEntity<TaquilleroResponse> buscarPrimeroPorPuntoDeVentaYRol(
            @PathVariable Integer idPunto,
            @PathVariable String rol) {
        // Nota: En un entorno real, se debería buscar la entidad PuntoDeVenta en un servicio
        PuntoDeVenta pv = new PuntoDeVenta();
        pv.setIdPuntoDeVenta(idPunto);
        return servicio.buscarPrimeroPorPuntoDeVentaYRol(pv, rol)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}