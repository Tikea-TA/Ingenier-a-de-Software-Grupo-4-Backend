package pucp.edu.pe.tikea.tikeabackend.controler.infraestructura;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pucp.edu.pe.tikea.tikeabackend.DTO.infraestructura.EstablecimientoRegistroRequest;
import pucp.edu.pe.tikea.tikeabackend.DTO.infraestructura.EstablecimientoModificacionRequest;
import pucp.edu.pe.tikea.tikeabackend.DTO.infraestructura.EstablecimientoResponse;
import pucp.edu.pe.tikea.tikeabackend.model.infraestructura.TipoEstadoLocal;
import pucp.edu.pe.tikea.tikeabackend.model.infraestructura.TipoLocal;
import pucp.edu.pe.tikea.tikeabackend.services.infraestructura.EstablecimientoService;

import java.util.List;

@RestController
@RequestMapping("/api/establecimientos")
@RequiredArgsConstructor
public class EstablecimientoCrontoller {

    private final EstablecimientoService establecimientoService;

    @GetMapping
    public List<EstablecimientoResponse> obtenerTodos() {
        return establecimientoService.obtenerTodosLosEstablecimientos();
    }

    @PostMapping("/registro")
    public EstablecimientoResponse registrarEstablecimiento(
            @Valid @RequestBody EstablecimientoRegistroRequest request) {
        return establecimientoService.registrarEstablecimiento(request);
    }

    @GetMapping("/{idEstablecimiento}")
    public EstablecimientoResponse obtenerEstablecimiento(
            @PathVariable Integer idEstablecimiento) {
        return establecimientoService.obtenerEstablecimiento(idEstablecimiento);
    }

    @GetMapping("/gestor/{idGestor}")
    public List<EstablecimientoResponse> obtenerEstablecimientosPorGestor(
            @PathVariable Integer idGestor) {
        return establecimientoService.obtenerEstablecimientosPorGestor(idGestor);
    }

    @GetMapping("/gestor/{idGestor}/activos")
    public List<EstablecimientoResponse> obtenerEstablecimientosActivosPorGestor(
            @PathVariable Integer idGestor) {
        return establecimientoService.obtenerEstablecimientosActivosPorGestor(idGestor);
    }

    @GetMapping("/tipo/{tipo}")
    public List<EstablecimientoResponse> obtenerEstablecimientosPorTipo(
            @PathVariable TipoLocal tipo) {
        return establecimientoService.obtenerEstablecimientosPorTipo(tipo);
    }

    @GetMapping("/estado/{estado}")
    public List<EstablecimientoResponse> obtenerEstablecimientosPorEstado(
            @PathVariable TipoEstadoLocal estado) {
        return establecimientoService.obtenerEstablecimientosPorEstado(estado);
    }

    @GetMapping("/activos")
    public List<EstablecimientoResponse> obtenerEstablecimientosActivos() {
        return establecimientoService.obtenerEstablecimientosActivos();
    }

    @PutMapping("/{idEstablecimiento}")
    public EstablecimientoResponse actualizarEstablecimiento(
            @PathVariable Integer idEstablecimiento,
            @Valid @RequestBody EstablecimientoModificacionRequest request) {
        return establecimientoService.actualizarEstablecimiento(idEstablecimiento, request);
    }

    @DeleteMapping("/{idEstablecimiento}")
    public void eliminarEstablecimiento(
            @PathVariable Integer idEstablecimiento) {
        establecimientoService.eliminarEstablecimiento(idEstablecimiento);
    }
}
