package pucp.edu.pe.tikea.tikeabackend.controler.venta;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pucp.edu.pe.tikea.tikeabackend.DTO.venta.TransaccionRequest;
import pucp.edu.pe.tikea.tikeabackend.DTO.venta.TransaccionResponse;
import pucp.edu.pe.tikea.tikeabackend.model.venta.TipoMoneda;
import pucp.edu.pe.tikea.tikeabackend.model.venta.TipoTransferencia;
import pucp.edu.pe.tikea.tikeabackend.services.venta.TransaccionService;

import java.util.List;

@RestController
@RequestMapping("/api/transacciones")
@RequiredArgsConstructor
public class TransaccionController {

    private final TransaccionService servicio;

    //          REGISTRAR
    @PostMapping
    public ResponseEntity<TransaccionResponse> registrar(@RequestBody TransaccionRequest request) {
        try {
            TransaccionResponse response = servicio.registrar(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            // Manejo de errores de datos de entrada o entidades relacionadas no encontradas
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    //          ACTUALIZAR
    @PutMapping("/{id}")
    public ResponseEntity<TransaccionResponse> actualizar(@PathVariable Integer id,
                                                          @RequestBody TransaccionRequest request) {
        try {
            TransaccionResponse response = servicio.actualizar(id, request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) { // Primero la específica (400 BAD REQUEST)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (RuntimeException e) { // Luego la general (usada para 404 NOT FOUND)
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

    //        LISTAR ACTIVOS
    @GetMapping("/activos")
    public ResponseEntity<List<TransaccionResponse>> listarActivos() {
        return ResponseEntity.ok(servicio.listarActivos());
    }

    //        OBTENER POR ID
    @GetMapping("/{id}")
    public ResponseEntity<TransaccionResponse> obtenerPorId(@PathVariable Integer id) {
        try {
            TransaccionResponse response = servicio.obtenerPorId(id);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    //        OBTENER POR NÚMERO DE TRANSACCIÓN
    @GetMapping("/numero/{numero}")
    public ResponseEntity<TransaccionResponse> obtenerPorNumero(@PathVariable String numero) {
        try {
            TransaccionResponse response = servicio.obtenerPorNumeroTransaccion(numero);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    //        BUSCAR POR MEDIO DE PAGO
    @GetMapping("/medio-pago/{idMedioPago}")
    public ResponseEntity<List<TransaccionResponse>> buscarPorMedioPago(@PathVariable Integer idMedioPago) {
        return ResponseEntity.ok(servicio.buscarPorMedioPago(idMedioPago));
    }

    //        BUSCAR POR RESERVA
    @GetMapping("/reserva/{idReserva}")
    public ResponseEntity<List<TransaccionResponse>> buscarPorReserva(@PathVariable Integer idReserva) {
        return ResponseEntity.ok(servicio.buscarPorReserva(idReserva));
    }

    //        BUSCAR POR ESTADO
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<TransaccionResponse>> buscarPorEstado(@PathVariable TipoTransferencia estado) {
        return ResponseEntity.ok(servicio.buscarPorEstado(estado));
    }

    //        BUSCAR POR MONEDA
    @GetMapping("/moneda/{moneda}")
    public ResponseEntity<List<TransaccionResponse>> buscarPorMoneda(@PathVariable TipoMoneda moneda) {
        return ResponseEntity.ok(servicio.buscarPorMoneda(moneda));
    }
}