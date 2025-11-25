package pucp.edu.pe.tikea.tikeabackend.controler.venta;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pucp.edu.pe.tikea.tikeabackend.model.venta.ComprobanteDePago;
import pucp.edu.pe.tikea.tikeabackend.model.venta.TipoComprobante;
import pucp.edu.pe.tikea.tikeabackend.model.venta.TipoTransferencia;
import pucp.edu.pe.tikea.tikeabackend.model.venta.Transaccion;
import pucp.edu.pe.tikea.tikeabackend.services.venta.ComprobanteDePagoService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/comprobantes")
@RequiredArgsConstructor
public class ComprobanteDePagoController {

    private final ComprobanteDePagoService servicio;

    //          REGISTRAR
    @PostMapping
    public ResponseEntity<ComprobanteDePago> registrar(@RequestBody ComprobanteDePago comprobante) {
        return ResponseEntity.ok(servicio.registrar(comprobante));
    }

    //          ACTUALIZAR
    @PutMapping
    public ResponseEntity<ComprobanteDePago> actualizar(@RequestBody ComprobanteDePago comprobante) {
        return ResponseEntity.ok(servicio.actualizar(comprobante));
    }

    //       ELIMINACIÓN LÓGICA
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarLogico(@PathVariable Integer id) {
        boolean eliminado = servicio.eliminarLogico(id);
        return eliminado ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    //        LISTAR TODOS
    @GetMapping
    public ResponseEntity<List<ComprobanteDePago>> listarTodos() {
        return ResponseEntity.ok(servicio.listarTodos());
    }

    //        BUSCAR POR ID
    @GetMapping("/{id}")
    public ResponseEntity<ComprobanteDePago> obtenerPorId(@PathVariable Integer id) {
        Optional<ComprobanteDePago> opt = servicio.obtenerPorId(id);
        return opt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //      BUSCAR POR NÚMERO
    @GetMapping("/numero/{numero}")
    public ResponseEntity<ComprobanteDePago> buscarPorNumero(@PathVariable String numero) {
        Optional<ComprobanteDePago> opt = servicio.buscarPorNumero(numero);
        return opt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //      BUSCAR POR TRANSACCIÓN
    @GetMapping("/transaccion/{idTransaccion}")
    public ResponseEntity<ComprobanteDePago> buscarPorTransaccion(@PathVariable Integer idTransaccion) {
        Transaccion t = new Transaccion();
        t.setIdTransaccion(idTransaccion);
        Optional<ComprobanteDePago> opt = servicio.buscarPorTransaccion(t);
        return opt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //     LISTAR POR TIPO
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<ComprobanteDePago>> listarPorTipo(@PathVariable TipoComprobante tipo) {
        return ResponseEntity.ok(servicio.listarPorTipo(tipo));
    }

    //     LISTAR POR ESTADO
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<ComprobanteDePago>> listarPorEstado(@PathVariable TipoTransferencia estado) {
        return ResponseEntity.ok(servicio.listarPorEstado(estado));
    }

    //      LISTAR ACTIVOS
    @GetMapping("/activos")
    public ResponseEntity<List<ComprobanteDePago>> listarActivos() {
        return ResponseEntity.ok(servicio.listarActivos());
    }

    //     LISTAR POR RANGO DE FECHAS
    @GetMapping("/rango-fechas")
    public ResponseEntity<List<ComprobanteDePago>> listarPorRangoFecha(
            @RequestParam("inicio") String inicio,
            @RequestParam("fin") String fin) {
        LocalDateTime inicioDt = LocalDateTime.parse(inicio);
        LocalDateTime finDt = LocalDateTime.parse(fin);
        return ResponseEntity.ok(servicio.listarPorRangoFecha(inicioDt, finDt));
    }

    //     LISTAR POR VALIDACIÓN SUNAT
    @GetMapping("/validacion-sunat/{validacion}")
    public ResponseEntity<List<ComprobanteDePago>> listarPorValidacionSunat(@PathVariable String validacion) {
        return ResponseEntity.ok(servicio.listarPorValidacionSunat(validacion));
    }

    //     LISTAR POR SERIE
    @GetMapping("/serie/{serie}")
    public ResponseEntity<List<ComprobanteDePago>> listarPorSerie(@PathVariable String serie) {
        return ResponseEntity.ok(servicio.listarPorSerie(serie));
    }

    //     LISTAR POR MONTO MAYOR O IGUAL
    @GetMapping("/monto-mayor/{monto}")
    public ResponseEntity<List<ComprobanteDePago>> listarMontoMayorIgual(@PathVariable Double monto) {
        return ResponseEntity.ok(servicio.listarMontoMayorIgual(monto));
    }

    //     LISTAR POR TIPO Y ESTADO
    @GetMapping("/tipo/{tipo}/estado/{estado}")
    public ResponseEntity<List<ComprobanteDePago>> listarPorTipoYEstado(
            @PathVariable TipoComprobante tipo,
            @PathVariable TipoTransferencia estado) {
        return ResponseEntity.ok(servicio.listarPorTipoYEstado(tipo, estado));
    }

    //     OBTENER CON TRANSACCIÓN
    @GetMapping("/con-transaccion/{id}")
    public ResponseEntity<ComprobanteDePago> obtenerConTransaccion(@PathVariable Integer id) {
        Optional<ComprobanteDePago> opt = servicio.obtenerConTransaccion(id);
        return opt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
