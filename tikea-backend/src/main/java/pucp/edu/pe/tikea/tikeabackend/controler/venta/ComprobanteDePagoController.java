package pucp.edu.pe.tikea.tikeabackend.controler.venta;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pucp.edu.pe.tikea.tikeabackend.DTO.venta.ComprobanteDePagoRequest;
import pucp.edu.pe.tikea.tikeabackend.DTO.venta.ComprobanteDePagoResponse;
import pucp.edu.pe.tikea.tikeabackend.model.venta.TipoComprobante;
import pucp.edu.pe.tikea.tikeabackend.model.venta.TipoTransferencia;
import pucp.edu.pe.tikea.tikeabackend.services.venta.ComprobanteDePagoService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/comprobantes")
@RequiredArgsConstructor
public class ComprobanteDePagoController {

    private final ComprobanteDePagoService servicio;

    //          REGISTRAR (Usa DTO Request y retorna DTO Response)
    @PostMapping
    public ResponseEntity<ComprobanteDePagoResponse> registrar(@RequestBody ComprobanteDePagoRequest request) {
        ComprobanteDePagoResponse response = servicio.registrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //          ACTUALIZAR (Usa DTO Request, incluye ID en Path y retorna DTO Response)
    @PutMapping("/{id}")
    public ResponseEntity<ComprobanteDePagoResponse> actualizar(
            @PathVariable Integer id,
            @RequestBody ComprobanteDePagoRequest request) {
        return ResponseEntity.ok(servicio.actualizar(id, request));
    }

    //       ELIMINACIÓN LÓGICA
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarLogico(@PathVariable Integer id) {
        boolean eliminado = servicio.eliminarLogico(id);
        return eliminado ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    //        LISTAR TODOS (Retorna DTO Response List)
    @GetMapping
    public ResponseEntity<List<ComprobanteDePagoResponse>> listarTodos() {
        return ResponseEntity.ok(servicio.listarTodos());
    }

    //        BUSCAR POR ID (Retorna DTO Response)
    @GetMapping("/{id}")
    public ResponseEntity<ComprobanteDePagoResponse> obtenerPorId(@PathVariable Integer id) {
        try {
            ComprobanteDePagoResponse response = servicio.obtenerPorId(id);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    //      BUSCAR POR NÚMERO (Retorna DTO Response)
    @GetMapping("/numero/{numero}")
    public ResponseEntity<ComprobanteDePagoResponse> buscarPorNumero(@PathVariable String numero) {
        Optional<ComprobanteDePagoResponse> opt = servicio.buscarPorNumero(numero);
        return opt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //      BUSCAR POR TRANSACCIÓN (Ahora recibe Integer y retorna DTO Response)
    @GetMapping("/transaccion/{idTransaccion}")
    public ResponseEntity<ComprobanteDePagoResponse> buscarPorTransaccion(@PathVariable Integer idTransaccion) {
        try {
            Optional<ComprobanteDePagoResponse> opt = servicio.buscarPorTransaccion(idTransaccion);
            return opt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (RuntimeException e) {
            // Manejar caso donde la Transacción no existe
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    //     LISTAR POR TIPO (Retorna DTO Response List)
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<ComprobanteDePagoResponse>> listarPorTipo(@PathVariable TipoComprobante tipo) {
        return ResponseEntity.ok(servicio.listarPorTipo(tipo));
    }

    //     LISTAR POR ESTADO (Retorna DTO Response List)
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<ComprobanteDePagoResponse>> listarPorEstado(@PathVariable TipoTransferencia estado) {
        return ResponseEntity.ok(servicio.listarPorEstado(estado));
    }

    //      LISTAR ACTIVOS (Retorna DTO Response List)
    @GetMapping("/activos")
    public ResponseEntity<List<ComprobanteDePagoResponse>> listarActivos() {
        return ResponseEntity.ok(servicio.listarActivos());
    }

    //     LISTAR POR RANGO DE FECHAS (Se usa LocalDateTime directamente)
    @GetMapping("/rango-fechas")
    public ResponseEntity<List<ComprobanteDePagoResponse>> listarPorRangoFecha(
            @RequestParam("inicio") LocalDateTime inicio,
            @RequestParam("fin") LocalDateTime fin) {
        return ResponseEntity.ok(servicio.listarPorRangoFecha(inicio, fin));
    }

    //     LISTAR POR VALIDACIÓN SUNAT (Retorna DTO Response List)
    @GetMapping("/validacion-sunat/{validacion}")
    public ResponseEntity<List<ComprobanteDePagoResponse>> listarPorValidacionSunat(@PathVariable String validacion) {
        return ResponseEntity.ok(servicio.listarPorValidacionSunat(validacion));
    }

    //     LISTAR POR SERIE (Retorna DTO Response List)
    @GetMapping("/serie/{serie}")
    public ResponseEntity<List<ComprobanteDePagoResponse>> listarPorSerie(@PathVariable String serie) {
        return ResponseEntity.ok(servicio.listarPorSerie(serie));
    }

    //     LISTAR POR MONTO MAYOR O IGUAL (Retorna DTO Response List)
    @GetMapping("/monto-mayor/{monto}")
    public ResponseEntity<List<ComprobanteDePagoResponse>> listarMontoMayorIgual(@PathVariable Double monto) {
        return ResponseEntity.ok(servicio.listarMontoMayorIgual(monto));
    }

    //     LISTAR POR TIPO Y ESTADO (Retorna DTO Response List)
    @GetMapping("/tipo/{tipo}/estado/{estado}")
    public ResponseEntity<List<ComprobanteDePagoResponse>> listarPorTipoYEstado(
            @PathVariable TipoComprobante tipo,
            @PathVariable TipoTransferencia estado) {
        return ResponseEntity.ok(servicio.listarPorTipoYEstado(tipo, estado));
    }

    //     OBTENER CON TRANSACCIÓN (Retorna DTO Response)
    @GetMapping("/con-transaccion/{id}")
    public ResponseEntity<ComprobanteDePagoResponse> obtenerConTransaccion(@PathVariable Integer id) {
        Optional<ComprobanteDePagoResponse> opt = servicio.obtenerConTransaccion(id);
        return opt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
