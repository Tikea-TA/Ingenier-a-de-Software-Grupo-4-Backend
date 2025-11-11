package pucp.edu.pe.tikea.tikeabackend.controler;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import pucp.edu.pe.tikea.tikeabackend.DTO.PromocionModificacionRequest;
import pucp.edu.pe.tikea.tikeabackend.DTO.PromocionRegistroRequest;
import pucp.edu.pe.tikea.tikeabackend.DTO.PromocionResponse;
import pucp.edu.pe.tikea.tikeabackend.services.PromocionService;

import java.util.List;

@RestController
@RequestMapping("/api/promociones")
public class PromocionController {

    private final PromocionService promocionService;

    public PromocionController(PromocionService promocionService) {
        this.promocionService = promocionService;
    }

    @GetMapping
    public List<PromocionResponse> listar() {
        return promocionService.listarTodas();
    }

    @GetMapping("/{id}")
    public PromocionResponse buscarPorId(@PathVariable Integer id) {
        return promocionService.buscarPorId(id);
    }

    @PostMapping("/registro")
    public PromocionResponse registrar(@Valid @RequestBody PromocionRegistroRequest dto) {
        return promocionService.registrar(dto);
    }

    @PutMapping("/{id}")
    public PromocionResponse actualizar(@PathVariable Integer id,
                                         @RequestBody PromocionModificacionRequest dto) {
        return promocionService.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public PromocionResponse eliminarLogic(@PathVariable Integer id) {
        return promocionService.inactivar(id);
    }

    @GetMapping("/evento/{idEvento}")
    public List<PromocionResponse> listarPorEvento(@PathVariable Integer idEvento) {
        return promocionService.listarPorEvento(idEvento);
    }

    @GetMapping("/evento/{idEvento}/activas")
    public List<PromocionResponse> listarActivasPorEvento(@PathVariable Integer idEvento) {
        return promocionService.listarActivasPorEvento(idEvento);
    }
}