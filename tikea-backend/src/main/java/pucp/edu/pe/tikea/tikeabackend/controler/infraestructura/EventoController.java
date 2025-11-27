package pucp.edu.pe.tikea.tikeabackend.controler.infraestructura;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pucp.edu.pe.tikea.tikeabackend.DTO.infraestructura.*;
import pucp.edu.pe.tikea.tikeabackend.model.infraestructura.CategoriaEvento;
import pucp.edu.pe.tikea.tikeabackend.model.infraestructura.EstadoEvento;
import pucp.edu.pe.tikea.tikeabackend.services.infraestructura.EventoService;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/eventos")
@RequiredArgsConstructor
public class EventoController {

    private final EventoService eventoService;

    @GetMapping
    public List<EventoResponse> obtenerTodos() {
        return eventoService.obtenerTodosLosEventos();
    }

    @PostMapping("/registro")
    public EventoResponse registrarEvento(
            @Valid @RequestBody EventoRegistroRequest request) {
        return eventoService.registrarEvento(request);
    }

    @GetMapping("/{idEvento}")
    public EventoResponse obtenerEvento(
            @PathVariable Integer idEvento) {
        return eventoService.obtenerEvento(idEvento);
    }

    @GetMapping("/establecimiento/{idEstablecimiento}")
    public List<EventoResponse> obtenerEventosPorEstablecimiento(
            @PathVariable Integer idEstablecimiento) {
        return eventoService.obtenerEventosPorEstablecimiento(idEstablecimiento);
    }

    @GetMapping("/productor/{idProductor}")
    public List<EventoResponse> obtenerEventosPorProductor(
            @PathVariable Integer idProductor) {
        return eventoService.obtenerEventosPorProductor(idProductor);
    }

    @GetMapping("/gestor/{idGestor}")
    public List<EventoResponse> obtenerEventosPorGestor(
            @PathVariable Integer idGestor) {
        return eventoService.obtenerEventosPorGestor(idGestor);
    }

    @GetMapping("/categoria/{categoria}")
    public List<EventoResponse> obtenerEventosPorCategoria(
            @PathVariable CategoriaEvento categoria) {
        return eventoService.obtenerEventosPorCategoria(categoria);
    }

    @GetMapping("/estado/{estado}")
    public List<EventoResponse> obtenerEventosPorEstado(
            @PathVariable EstadoEvento estado) {
        return eventoService.obtenerEventosPorEstado(estado);
    }

    @GetMapping("/fecha/{fecha}")
    public List<EventoResponse> obtenerEventosPorFecha(
            @PathVariable LocalDate fecha) {
        return eventoService.obtenerEventosPorFecha(fecha);
    }

    @GetMapping("/fechas")
    public List<EventoResponse> obtenerEventosPorRangoFechas(
            @RequestParam LocalDate fechaInicio,
            @RequestParam LocalDate fechaFin) {
        return eventoService.obtenerEventosPorRangoFechas(fechaInicio, fechaFin);
    }

    @GetMapping("/activos")
    public List<EventoResponse> obtenerEventosActivos() {
        return eventoService.obtenerEventosActivos();
    }

    @PutMapping("/{idEvento}")
    public EventoResponse actualizarEvento(
            @PathVariable Integer idEvento,
            @Valid @RequestBody EventoModificacionRequest request) {
        return eventoService.actualizarEvento(idEvento, request);
    }

    @DeleteMapping("/{idEvento}")
    public void eliminarEvento(
            @PathVariable Integer idEvento) {
        eventoService.eliminarEvento(idEvento);
    }
    @PostMapping("/eventosReporte") //
    public ResponseEntity<List<ReporteEventoDetalle>> generarReporteDetalladoDeEventos(
            @RequestBody ReporteRequestEvento requestDTO) {

        // Llama al nuevo método del servicio
        List<ReporteEventoDetalle> reporte = eventoService.generarReporteDetalladoPorFechaEvento(requestDTO);
        return ResponseEntity.ok(reporte);
    }

    @GetMapping("/{id}/banner")
    public ResponseEntity<byte[]> obtenerBanner(@PathVariable Integer id) {
        byte[] bannerBytes = eventoService.obtenerBannerPorId(id);

        if (bannerBytes == null || bannerBytes.length == 0) {
            return ResponseEntity.notFound().build(); // Devuelve 404 si no hay imagen
        }

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG) // El navegador suele ser inteligente, pero JPEG es buen default
                .body(bannerBytes);
    }

}
