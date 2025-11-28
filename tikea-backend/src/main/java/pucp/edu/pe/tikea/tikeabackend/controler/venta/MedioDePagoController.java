package pucp.edu.pe.tikea.tikeabackend.controler.venta;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pucp.edu.pe.tikea.tikeabackend.DTO.venta.MedioDePagoRequest;
import pucp.edu.pe.tikea.tikeabackend.DTO.venta.MedioDePagoResponse;
import pucp.edu.pe.tikea.tikeabackend.model.infraestructura.TipoEstadoAproDes;
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
    public ResponseEntity<MedioDePagoResponse> registrar(@RequestBody MedioDePagoRequest request) {
        try {
            MedioDePagoResponse response = servicio.registrar(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    //          ACTUALIZAR (Usa DTO Request para entrada y DTO Response para salida)
    @PutMapping("/{id}")
    public ResponseEntity<MedioDePagoResponse> actualizar(@PathVariable Integer id,
                                                          @RequestBody MedioDePagoRequest request) {
        try {
            MedioDePagoResponse response = servicio.actualizar(id, request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    //       ELIMINACIÓN LÓGICA
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            servicio.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    //        LISTAR TODOS ACTIVOS
    @GetMapping("/activos")
    public ResponseEntity<List<MedioDePagoResponse>> listarActivos() {
        return ResponseEntity.ok(servicio.listarActivos());
    }

    //        OBTENER POR ID
    @GetMapping("/{id}")
    public ResponseEntity<MedioDePagoResponse> obtenerPorId(@PathVariable Integer id) {
        try {
            MedioDePagoResponse response = servicio.obtenerPorId(id);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    //        BUSCAR POR TIPO
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<MedioDePagoResponse>> buscarPorTipo(@PathVariable TipoMedioDePago tipo) {
        return ResponseEntity.ok(servicio.buscarPorTipo(tipo));
    }

    //        BUSCAR ACTIVOS POR TIPO
    @GetMapping("/tipo/{tipo}/activos")
    public ResponseEntity<List<MedioDePagoResponse>> buscarActivosPorTipo(@PathVariable TipoMedioDePago tipo) {
        return ResponseEntity.ok(servicio.buscarActivosPorTipo(tipo));
    }

    //        BUSCAR POR ESTADO
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<MedioDePagoResponse>> buscarPorEstado(@PathVariable TipoEstadoAproDes estado) {
        return ResponseEntity.ok(servicio.buscarPorEstado(estado));
    }

    //        BUSCAR ACTIVOS POR ESTADO
    @GetMapping("/estado/{estado}/activos")
    public ResponseEntity<List<MedioDePagoResponse>> buscarActivosPorEstado(@PathVariable TipoEstadoAproDes estado) {
        return ResponseEntity.ok(servicio.buscarActivosPorEstado(estado));
    }

    //        BUSCAR POR PASARELA
    @GetMapping("/pasarela/{pasarela}")
    public ResponseEntity<List<MedioDePagoResponse>> buscarPorPasarela(@PathVariable String pasarela) {
        return ResponseEntity.ok(servicio.buscarPorPasarela(pasarela));
    }

    //        BUSCAR POR VALIDACIÓN SUNAT
    @GetMapping("/validacion-sunat/{validacion}")
    public ResponseEntity<List<MedioDePagoResponse>> buscarPorValidacionSunat(@PathVariable boolean validacion) {
        return ResponseEntity.ok(servicio.buscarPorValidacionSunat(validacion));
    }
}