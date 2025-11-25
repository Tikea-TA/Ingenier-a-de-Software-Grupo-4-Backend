package pucp.edu.pe.tikea.tikeabackend.controler.infraestructura;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pucp.edu.pe.tikea.tikeabackend.DTO.infraestructura.AsientoRequest;
import pucp.edu.pe.tikea.tikeabackend.DTO.infraestructura.AsientoResponse;
import pucp.edu.pe.tikea.tikeabackend.model.infraestructura.TipoEstadoAsiento;
import pucp.edu.pe.tikea.tikeabackend.services.infraestructura.AsientoService;

import java.util.List;

@RestController
@RequestMapping("/api/asientos")
@RequiredArgsConstructor
public class AsientoController {

    private final AsientoService asientoService;

    //           REGISTRAR
    @PostMapping("/registro")
    public ResponseEntity<AsientoResponse> registrar(@RequestBody AsientoRequest request) {
        return ResponseEntity.ok(asientoService.registrar(request));
    }

    //           MODIFICAR
    @PutMapping("/{id}")
    public ResponseEntity<AsientoResponse> modificar(
            @PathVariable Integer id,
            @RequestBody AsientoRequest request) {
        return ResponseEntity.ok(asientoService.modificar(id, request));
    }

    //      OBTENER POR ID
    @GetMapping("/{id}")
    public ResponseEntity<AsientoResponse> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(asientoService.obtenerPorId(id));
    }

    //      LISTAR ACTIVOS
    @GetMapping("/activos")
    public ResponseEntity<List<AsientoResponse>> listarActivos() {
        return ResponseEntity.ok(asientoService.listarActivos());
    }

    //      LISTAR POR ZONA
    @GetMapping("/zona/{idZona}")
    public ResponseEntity<List<AsientoResponse>> listarPorZona(@PathVariable Integer idZona) {
        return ResponseEntity.ok(asientoService.listarPorZona(idZona));
    }

    //      LISTAR POR ESTADO

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<AsientoResponse>> listarPorEstado(@PathVariable TipoEstadoAsiento estado) {
        return ResponseEntity.ok(asientoService.listarPorEstado(estado));
    }

    //      ELIMINACIÓN LÓGICA
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        asientoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
