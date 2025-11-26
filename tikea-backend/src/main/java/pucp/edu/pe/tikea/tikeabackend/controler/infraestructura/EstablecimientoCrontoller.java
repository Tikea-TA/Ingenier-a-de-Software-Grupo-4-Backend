package pucp.edu.pe.tikea.tikeabackend.controler.infraestructura;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    // Endpoint para filtrar pendientes por gestor
    @GetMapping("/gestor/{idGestor}/estado/{estado}")
    public List<EstablecimientoResponse> obtenerEstablecimientoPorEstadoYGestor(
            @PathVariable Integer idGestor,
            @PathVariable TipoEstadoLocal estado) {
        return establecimientoService.obtenerEstablecimientosPorEstadoYGestor(estado, idGestor);
    }

    // Endpoint para descargar el archivo
    @GetMapping("/{idEstablecimiento}/documento")
    public ResponseEntity<byte[]> descargarDocumentacion(@PathVariable Integer idEstablecimiento) {
        byte[] documento = establecimientoService.obtenerDocumentacion(idEstablecimiento);

        // Asumimos PDF por defecto, pero el navegador intentará detectarlo
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"documentacion_local_" + idEstablecimiento + ".pdf\"")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE) 
                .body(documento);
    }
}
