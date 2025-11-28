package pucp.edu.pe.tikea.tikeabackend.controler.usuario;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pucp.edu.pe.tikea.tikeabackend.DTO.usuarios.GestorRegistroRequest;
import pucp.edu.pe.tikea.tikeabackend.DTO.usuarios.GestorModificacionRequest;
import pucp.edu.pe.tikea.tikeabackend.DTO.usuarios.GestorResponse;
import pucp.edu.pe.tikea.tikeabackend.DTO.usuarios.cliente.LoginRequest;
import pucp.edu.pe.tikea.tikeabackend.model.usuarios.TipoArea;
import pucp.edu.pe.tikea.tikeabackend.services.usuarios.GestorService;

import java.util.List;

@RestController
@RequestMapping("/api/gestores")
@RequiredArgsConstructor
public class GestorController {
    private final GestorService gestorService;


    @PostMapping("/registro")
    public GestorResponse registrarGestor(
            @Valid @RequestBody GestorRegistroRequest request) {
        return gestorService.registrarGestor(request);
    }

    @GetMapping
    public List<GestorResponse> obtenerTodos() {
        return gestorService.obtenerTodosLosGestores();
    }

    @GetMapping("/{idGestor}")
    public GestorResponse obtenerGestor(
            @PathVariable Integer idGestor) {
        return gestorService.obtenerGestor(idGestor);
    }

    @GetMapping("/area/{tipoArea}")
    public List<GestorResponse> obtenerGestoresPorArea(
            @PathVariable TipoArea tipoArea) {
        return gestorService.obtenerGestoresPorArea(tipoArea);
    }

    @GetMapping("/area/{tipoArea}/activos")
    public List<GestorResponse> obtenerGestoresActivosPorArea(
            @PathVariable TipoArea tipoArea) {
        return gestorService.obtenerGestoresActivosPorArea(tipoArea);
    }

    @PutMapping("/{idGestor}")
    public GestorResponse actualizarGestor(
            @PathVariable Integer idGestor,
            @Valid @RequestBody GestorModificacionRequest request) {
        return gestorService.actualizarGestor(idGestor, request);
    }

    @DeleteMapping("/{idGestor}")
    public void eliminarGestor(
            @PathVariable Integer idGestor) {
        gestorService.eliminarGestor(idGestor);
    }

    @PostMapping("/login")
    public GestorResponse login(@Valid @RequestBody LoginRequest dto) {
        return gestorService.login(dto);
    }
}
