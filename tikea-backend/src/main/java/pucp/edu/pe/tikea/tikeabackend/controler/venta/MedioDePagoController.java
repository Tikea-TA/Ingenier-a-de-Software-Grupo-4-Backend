package pucp.edu.pe.tikea.tikeabackend.controler.venta;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pucp.edu.pe.tikea.tikeabackend.model.infraestructura.TipoEstadoAproDes;
import pucp.edu.pe.tikea.tikeabackend.model.venta.MedioDePago;
import pucp.edu.pe.tikea.tikeabackend.model.venta.TipoMedioDePago;
import pucp.edu.pe.tikea.tikeabackend.services.venta.MedioDePagoService;

import java.util.List;

@RestController
@RequestMapping("/api/medios-pago")
@RequiredArgsConstructor
public class MedioDePagoController {

    private final MedioDePagoService servicio;

    //          REGISTRAR
    @PostMapping
    public ResponseEntity<MedioDePago> registrar(@RequestBody MedioDePago medio) {
        return ResponseEntity.ok(servicio.registrar(medio));
    }

    //          ACTUALIZAR
    @PutMapping("/{id}")
    public ResponseEntity<MedioDePago> actualizar(@PathVariable Integer id,
                                                  @RequestBody MedioDePago medio) {
        return ResponseEntity.ok(servicio.actualizar(id, medio));
    }

    //       ELIMINACIÓN LÓGICA
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        servicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    //        LISTAR TODOS ACTIVOS
    @GetMapping("/activos")
    public ResponseEntity<List<MedioDePago>> listarActivos() {
        return ResponseEntity.ok(servicio.listarActivos());
    }

    //        OBTENER POR ID
    @GetMapping("/{id}")
    public ResponseEntity<MedioDePago> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(servicio.obtenerPorId(id));
    }

    //        BUSCAR POR TIPO
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<MedioDePago>> buscarPorTipo(@PathVariable TipoMedioDePago tipo) {
        return ResponseEntity.ok(servicio.buscarPorTipo(tipo));
    }

    //        BUSCAR ACTIVOS POR TIPO
    @GetMapping("/tipo/{tipo}/activos")
    public ResponseEntity<List<MedioDePago>> buscarActivosPorTipo(@PathVariable TipoMedioDePago tipo) {
        return ResponseEntity.ok(servicio.buscarActivosPorTipo(tipo));
    }

    //        BUSCAR POR ESTADO
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<MedioDePago>> buscarPorEstado(@PathVariable TipoEstadoAproDes estado) {
        return ResponseEntity.ok(servicio.buscarPorEstado(estado));
    }

    //        BUSCAR ACTIVOS POR ESTADO
    @GetMapping("/estado/{estado}/activos")
    public ResponseEntity<List<MedioDePago>> buscarActivosPorEstado(@PathVariable TipoEstadoAproDes estado) {
        return ResponseEntity.ok(servicio.buscarActivosPorEstado(estado));
    }

    //        BUSCAR POR PASARELA
    @GetMapping("/pasarela/{pasarela}")
    public ResponseEntity<List<MedioDePago>> buscarPorPasarela(@PathVariable String pasarela) {
        return ResponseEntity.ok(servicio.buscarPorPasarela(pasarela));
    }

    //        BUSCAR POR VALIDACIÓN SUNAT
    @GetMapping("/validacion-sunat/{validacion}")
    public ResponseEntity<List<MedioDePago>> buscarPorValidacionSunat(@PathVariable boolean validacion) {
        return ResponseEntity.ok(servicio.buscarPorValidacionSunat(validacion));
    }
}
