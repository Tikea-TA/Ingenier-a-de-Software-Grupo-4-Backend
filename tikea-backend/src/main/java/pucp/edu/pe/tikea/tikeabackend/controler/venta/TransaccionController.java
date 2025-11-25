package pucp.edu.pe.tikea.tikeabackend.controler.venta;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pucp.edu.pe.tikea.tikeabackend.model.venta.TipoMoneda;
import pucp.edu.pe.tikea.tikeabackend.model.venta.TipoTransferencia;
import pucp.edu.pe.tikea.tikeabackend.model.venta.Transaccion;
import pucp.edu.pe.tikea.tikeabackend.services.venta.TransaccionService;

import java.util.List;

@RestController
@RequestMapping("/api/transacciones")
@RequiredArgsConstructor
public class TransaccionController {

    private final TransaccionService servicio;

    //          REGISTRAR
    @PostMapping
    public ResponseEntity<Transaccion> registrar(@RequestBody Transaccion transaccion) {
        return ResponseEntity.ok(servicio.registrar(transaccion));
    }

    //          ACTUALIZAR
    @PutMapping("/{id}")
    public ResponseEntity<Transaccion> actualizar(@PathVariable Integer id,
                                                  @RequestBody Transaccion transaccion) {
        return ResponseEntity.ok(servicio.actualizar(id, transaccion));
    }

    //       ELIMINACIÓN LÓGICA
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        servicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    //        LISTAR ACTIVOS
    @GetMapping("/activos")
    public ResponseEntity<List<Transaccion>> listarActivos() {
        return ResponseEntity.ok(servicio.listarActivos());
    }

    //        OBTENER POR ID
    @GetMapping("/{id}")
    public ResponseEntity<Transaccion> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(servicio.obtenerPorId(id));
    }

    //        OBTENER POR NÚMERO DE TRANSACCIÓN
    @GetMapping("/numero/{numero}")
    public ResponseEntity<Transaccion> obtenerPorNumero(@PathVariable String numero) {
        return ResponseEntity.ok(servicio.obtenerPorNumeroTransaccion(numero));
    }

    //        BUSCAR POR MEDIO DE PAGO
    @GetMapping("/medio-pago/{idMedioPago}")
    public ResponseEntity<List<Transaccion>> buscarPorMedioPago(@PathVariable Integer idMedioPago) {
        return ResponseEntity.ok(servicio.buscarPorMedioPago(idMedioPago));
    }

    //        BUSCAR POR RESERVA
    @GetMapping("/reserva/{idReserva}")
    public ResponseEntity<List<Transaccion>> buscarPorReserva(@PathVariable Integer idReserva) {
        return ResponseEntity.ok(servicio.buscarPorReserva(idReserva));
    }

    //        BUSCAR POR ESTADO
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Transaccion>> buscarPorEstado(@PathVariable TipoTransferencia estado) {
        return ResponseEntity.ok(servicio.buscarPorEstado(estado));
    }

    //        BUSCAR POR MONEDA
    @GetMapping("/moneda/{moneda}")
    public ResponseEntity<List<Transaccion>> buscarPorMoneda(@PathVariable TipoMoneda moneda) {
        return ResponseEntity.ok(servicio.buscarPorMoneda(moneda));
    }
}
