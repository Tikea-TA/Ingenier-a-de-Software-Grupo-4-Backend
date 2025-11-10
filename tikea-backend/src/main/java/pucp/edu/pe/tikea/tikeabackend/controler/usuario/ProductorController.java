package pucp.edu.pe.tikea.tikeabackend.controler.usuario;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pucp.edu.pe.tikea.tikeabackend.DTO.usuarios.ProductorRegistroRequest;
import pucp.edu.pe.tikea.tikeabackend.DTO.usuarios.ProductorModificacionRequest;
import pucp.edu.pe.tikea.tikeabackend.DTO.usuarios.ProductorResponse;
import pucp.edu.pe.tikea.tikeabackend.model.usuarios.TipoEstadoProductor;
import pucp.edu.pe.tikea.tikeabackend.services.usuarios.ProductorService;

import java.util.List;

@RestController
@RequestMapping("/api/productores")
@RequiredArgsConstructor
public class ProductorController {

    private final ProductorService productorService;

    // =============== CREATE ===============
    /**
     * POST /api/productores/registro
     * Registra un nuevo Productor en el sistema
     */
    @PostMapping("/registro")
    public ProductorResponse registrarProductor(
            @Valid @RequestBody ProductorRegistroRequest request) {
        return productorService.registrarProductor(request);
    }

    // =============== READ ===============
    /**
     * GET /api/productores
     * Obtiene todos los Productores
     */
    @GetMapping
    public List<ProductorResponse> obtenerTodos() {
        return productorService.obtenerTodosLosProductores();
    }

    /**
     * GET /api/productores/{idProductor}
     * Obtiene un Productor por ID
     */
    @GetMapping("/{idProductor}")
    public ProductorResponse obtenerProductor(
            @PathVariable Integer idProductor) {
        return productorService.obtenerProductor(idProductor);
    }

    /**
     * GET /api/productores/gestor/{idGestor}
     * Obtiene todos los Productores de un Gestor específico
     */
    @GetMapping("/gestor/{idGestor}")
    public List<ProductorResponse> obtenerProductoresPorGestor(
            @PathVariable Integer idGestor) {
        return productorService.obtenerProductoresPorGestor(idGestor);
    }

    /**
     * GET /api/productores/estado/{estado}
     * Obtiene Productores por estado
     */
    @GetMapping("/estado/{estado}")
    public List<ProductorResponse> obtenerProductoresPorEstado(
            @PathVariable TipoEstadoProductor estado) {
        return productorService.obtenerProductoresPorEstado(estado);
    }

    // =============== UPDATE ===============
    /**
     * PUT /api/productores/{idProductor}
     * Actualiza los datos de un Productor
     */
    @PutMapping("/{idProductor}")
    public ProductorResponse actualizarProductor(
            @PathVariable Integer idProductor,
            @Valid @RequestBody ProductorModificacionRequest request) {
        return productorService.actualizarProductor(idProductor, request);
    }

    // =============== DELETE ===============
    /**
     * DELETE /api/productores/{idProductor}
     * Elimina un Productor
     */
    @DeleteMapping("/{idProductor}")
    public void eliminarProductor(
            @PathVariable Integer idProductor) {
        productorService.eliminarProductor(idProductor);
    }
}