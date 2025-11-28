package pucp.edu.pe.tikea.tikeabackend.controler.usuario;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pucp.edu.pe.tikea.tikeabackend.DTO.usuarios.ProductorRegistroRequest;
import pucp.edu.pe.tikea.tikeabackend.DTO.usuarios.ProductorModificacionRequest;
import pucp.edu.pe.tikea.tikeabackend.DTO.usuarios.ProductorResponse;
import pucp.edu.pe.tikea.tikeabackend.DTO.usuarios.cliente.LoginRequest;
import pucp.edu.pe.tikea.tikeabackend.model.usuarios.TipoEstadoProductor;
import pucp.edu.pe.tikea.tikeabackend.services.usuarios.ProductorService;

import java.util.List;

@RestController
@RequestMapping("/api/productores")
@RequiredArgsConstructor
public class ProductorController {

    private final ProductorService productorService;

    @PostMapping("/registro")
    public ProductorResponse registrarProductor(
            @Valid @RequestBody ProductorRegistroRequest request) {
        return productorService.registrarProductor(request);
    }

    @GetMapping
    public List<ProductorResponse> obtenerTodos() {
        return productorService.obtenerTodosLosProductores();
    }

    @GetMapping("/{idProductor}")
    public ProductorResponse obtenerProductor(
            @PathVariable Integer idProductor) {
        return productorService.obtenerProductor(idProductor);
    }

    @GetMapping("/gestor/{idGestor}")
    public List<ProductorResponse> obtenerProductoresPorGestor(
            @PathVariable Integer idGestor) {
        return productorService.obtenerProductoresPorGestor(idGestor);
    }

    @GetMapping("/estado/{estado}")
    public List<ProductorResponse> obtenerProductoresPorEstado(
            @PathVariable TipoEstadoProductor estado) {
        return productorService.obtenerProductoresPorEstado(estado);
    }

    @PutMapping("/{idProductor}")
    public ProductorResponse actualizarProductor(
            @PathVariable Integer idProductor,
            @Valid @RequestBody ProductorModificacionRequest request) {
        return productorService.actualizarProductor(idProductor, request);
    }

    @DeleteMapping("/{idProductor}")
    public void eliminarProductor(
            @PathVariable Integer idProductor) {
        productorService.eliminarProductor(idProductor);
    }

    @GetMapping("/gestor/{idGestor}/pendientes")
    public List<ProductorResponse> obtenerPendientesPorGestor(@PathVariable Integer idGestor) {
        return productorService.obtenerProductoresPendientesPorGestor(idGestor);
    }

    // Endpoint para descargar el archivo de sustento del productor
    @GetMapping("/{idProductor}/documento")
    public ResponseEntity<byte[]> descargarDocumentacion(@PathVariable Integer idProductor) {
        byte[] documento = productorService.obtenerDocumentacion(idProductor);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"sustento_productor_" + idProductor + ".pdf\"")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE)
                .body(documento);
    }

    // Endpoint para validar (PUT)
    @PutMapping("/{idProductor}/validacion")
    public ProductorResponse validarProductor(
            @PathVariable Integer idProductor, 
            @RequestParam TipoEstadoProductor nuevoEstado) {
        return productorService.validarProductor(idProductor, nuevoEstado);
    }

    @PostMapping("/login")
    public ProductorResponse login(@Valid @RequestBody LoginRequest dto) {
        return productorService.login(dto);
    }
}