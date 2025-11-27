package pucp.edu.pe.tikea.tikeabackend.services.usuarios;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pucp.edu.pe.tikea.tikeabackend.DTO.usuarios.ProductorRegistroRequest;
import pucp.edu.pe.tikea.tikeabackend.DTO.usuarios.ProductorModificacionRequest;
import pucp.edu.pe.tikea.tikeabackend.DTO.usuarios.ProductorResponse;
import pucp.edu.pe.tikea.tikeabackend.DTO.usuarios.GestorResponse;
import pucp.edu.pe.tikea.tikeabackend.model.usuarios.Productor;
import pucp.edu.pe.tikea.tikeabackend.model.usuarios.Gestor;
import pucp.edu.pe.tikea.tikeabackend.model.usuarios.TipoEstado;
import pucp.edu.pe.tikea.tikeabackend.model.usuarios.TipoEstadoProductor;
import lombok.RequiredArgsConstructor;
import jakarta.persistence.EntityManager;
import pucp.edu.pe.tikea.tikeabackend.repository.usuarios.GestorRepository;
import pucp.edu.pe.tikea.tikeabackend.repository.usuarios.ProductorRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductorService {

    private final ProductorRepository productorRepository;
    private final GestorRepository gestorRepository;
    private final EntityManager entityManager;
    private final BCryptPasswordEncoder encoder;

    // =============== CREATE ===============
    public ProductorResponse registrarProductor(ProductorRegistroRequest productorRegistroRequest) {
        // Validar que el Gestor existe
        Gestor gestor = gestorRepository.findById(productorRegistroRequest.getIdGestor())
                .orElseThrow(() -> new RuntimeException("Gestor no encontrado con ID: " + productorRegistroRequest.getIdGestor()));

        // Validar que el correo no esté registrado
        if (productorRepository.findByCorreoIgnoreCase(productorRegistroRequest.getEmail()).isPresent()) {
            throw new RuntimeException("El correo ya está registrado con el email: " + productorRegistroRequest.getEmail());
        }

        // Validar que el RUC no esté registrado
        if (productorRepository.findByRUC(productorRegistroRequest.getRUC()).isPresent()) {
            throw new RuntimeException("El RUC ya está registrado: " + productorRegistroRequest.getRUC());
        }

        // Crear la entidad Productor
        Productor productor = new Productor();
        productor.setNombre(productorRegistroRequest.getNombre());
        productor.setApellidos(productorRegistroRequest.getApellidos());
        productor.setCorreo(productorRegistroRequest.getEmail());
        productor.setTelefono(productorRegistroRequest.getTelefono());
        productor.setNombreUser(productorRegistroRequest.getNombreUsuario());
        productor.setPassword(encoder.encode(productorRegistroRequest.getPassword()));
        productor.setDNI(productorRegistroRequest.getDNI());
        productor.setGestor(gestor);
        productor.setEstado(TipoEstado.ACTIVO);
        productor.setRazonSocial(productorRegistroRequest.getRazonSocial());
        productor.setRUC(productorRegistroRequest.getRUC());
        productor.setDireccionFisica(productorRegistroRequest.getDireccionFisica());
        productor.setTipoEstadoProductor(productorRegistroRequest.getTipoEstadoProductor());
        productor.setDocumentacionAdjunta(productorRegistroRequest.getDocumentacionFisica());
        productor.setLocalesRegistrados(0);
        productor.setEventosRegistrados(0);
        productor.setPromocionesCreadas(0);

        // Guardar en la BD
        Productor productorGuardado = productorRepository.save(productor);

        // Refrescar la entidad para obtener el valor de activo de la BD
        productorRepository.flush();
        entityManager.refresh(productorGuardado);

        // Retornar como DTO
        return convertirAResponseDTO(productorGuardado);
    }

    // =============== READ ===============
    public ProductorResponse obtenerProductor(Integer idProductor) {
        Productor productor = productorRepository.findById(idProductor)
                .orElseThrow(() -> new RuntimeException("Productor no encontrado con ID: " + idProductor));

        // Forzar carga de relaciones
        if (productor.getGestor() != null) {
            productor.getGestor().getNombre();
        }

        return convertirAResponseDTO(productor);
    }

    public List<ProductorResponse> obtenerTodosLosProductores() {
        return productorRepository.findAll()
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    public List<ProductorResponse> obtenerProductoresPorGestor(Integer idGestor) {
        Gestor gestor = gestorRepository.findById(idGestor)
                .orElseThrow(() -> new RuntimeException("Gestor no encontrado con ID: " + idGestor));

        return productorRepository.findByGestor(gestor)
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    public List<ProductorResponse> obtenerProductoresPorEstado(TipoEstadoProductor estado) {
        return productorRepository.findByTipoEstadoProductor(estado)
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    public List<ProductorResponse> obtenerProductoresPendientesPorGestor(Integer idGestor) {
        // Si idGestor es nulo o 0, podríamos asumir que es un ADMIN buscando todo
        if (idGestor == null || idGestor == 0) {
            return productorRepository.findByTipoEstadoProductor(TipoEstadoProductor.PENDIENTE_VALIDACION)
                    .stream().map(this::convertirAResponseDTO).collect(Collectors.toList());
        }
    
        Gestor gestor = gestorRepository.findById(idGestor)
                .orElseThrow(() -> new RuntimeException("Gestor no encontrado"));
    
        return productorRepository.findByTipoEstadoProductorAndGestor(TipoEstadoProductor.PENDIENTE_VALIDACION, gestor)
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    public byte[] obtenerDocumentacion(Integer idProductor) {
        Productor productor = productorRepository.findById(idProductor)
                .orElseThrow(() -> new RuntimeException("Productor no encontrado"));
    
        if (productor.getDocumentacionAdjunta() == null) {
            throw new RuntimeException("El productor no tiene documentación adjunta");
        }
        return productor.getDocumentacionAdjunta();
    }

    // =============== UPDATE ===============
    public ProductorResponse actualizarProductor(Integer idProductor, ProductorModificacionRequest request) {
        Productor productor = productorRepository.findById(idProductor)
                .orElseThrow(() -> new RuntimeException("Productor no encontrado con ID: " + idProductor));

        // Validar que el correo no esté usado por otro Productor
        if (request.getCorreo() != null) {
            Optional<Productor> productorConCorreo = productorRepository.findByCorreoIgnoreCase(request.getCorreo());
            if (productorConCorreo.isPresent() && !productorConCorreo.get().getIdUsuario().equals(idProductor)) {
                throw new RuntimeException("El correo ya está registrado por otro productor");
            }
            productor.setCorreo(request.getCorreo());
        }

        // Actualizar campos
        if (request.getPassword() != null) {
            productor.setPassword(request.getPassword()); // En producción, encriptar
        }
        if (request.getNombreUsuario() != null) {
            productor.setNombreUser(request.getNombreUsuario());
        }
        if (request.getTelefono() != null) {
            productor.setTelefono(request.getTelefono());
        }
        if (request.getRazonSocial() != null) {
            productor.setRazonSocial(request.getRazonSocial());
        }
        if (request.getRUC() != null) {
            productor.setRUC(request.getRUC());
        }
        if (request.getDireccionFisica() != null) {
            productor.setDireccionFisica(request.getDireccionFisica());
        }
        if (request.getTipoEstadoProductor() != null) {
            productor.setTipoEstadoProductor(request.getTipoEstadoProductor());
        }
        if (request.getDocumentacionFisica() != null) {
            productor.setDocumentacionAdjunta(request.getDocumentacionFisica());
        }

        // Guardar cambios
        Productor productorActualizado = productorRepository.save(productor);

        return convertirAResponseDTO(productorActualizado);
    }

    public ProductorResponse validarProductor(Integer idProductor, TipoEstadoProductor nuevoEstado) {
        Productor productor = productorRepository.findById(idProductor)
                .orElseThrow(() -> new RuntimeException("Productor no encontrado"));
    
        productor.setTipoEstadoProductor(nuevoEstado);
        // Actualizar fecha de verificación
        productor.setFechaVerificacion(LocalDateTime.now()); 
        
        return convertirAResponseDTO(productorRepository.save(productor));
    }

    // =============== DELETE ===============
    public void eliminarProductor(Integer idProductor) {
        Productor productor = productorRepository.findById(idProductor)
                .orElseThrow(() -> new RuntimeException("Productor no encontrado con ID: " + idProductor));

        productorRepository.delete(productor);
    }

    // =============== MÉTODOS AUXILIARES ===============
    private ProductorResponse convertirAResponseDTO(Productor productor) {
        ProductorResponse dto = new ProductorResponse();
        dto.setIdProductor(productor.getIdUsuario());
        dto.setNombre(productor.getNombre());
        dto.setApellidos(productor.getApellidos());
        dto.setCorreo(productor.getCorreo());
        dto.setTelefono(productor.getTelefono());
        dto.setNombreUsuario(productor.getNombreUser());
        dto.setDNI(productor.getDNI());
        dto.setEstado(productor.getEstado());
        dto.setActivo(productor.getActivo());
        dto.setRazonSocial(productor.getRazonSocial());
        dto.setRUC(productor.getRUC());
        dto.setDireccionFisica(productor.getDireccionFisica());
        dto.setTipoEstadoProductor(productor.getTipoEstadoProductor());
        dto.setLocalesRegistrados(productor.getLocalesRegistrados());
        dto.setEventosRegistrados(productor.getEventosRegistrados());
        dto.setPromocionesCreadas(productor.getPromocionesCreadas());
        dto.setFechaVerificacion(productor.getFechaVerificacion());

        // Convertir Gestor a GestorResponse
        if (productor.getGestor() != null) {
            GestorResponse gestorDTO = new GestorResponse();
            gestorDTO.setIdUsuario(productor.getGestor().getIdUsuario());
            gestorDTO.setNombre(productor.getGestor().getNombre());
            gestorDTO.setApellidos(productor.getGestor().getApellidos());
            gestorDTO.setCorreo(productor.getGestor().getCorreo());
            gestorDTO.setTelefono(productor.getGestor().getTelefono());
            gestorDTO.setNombreUsuario(productor.getGestor().getNombreUser());
            gestorDTO.setDni(productor.getGestor().getDNI());
            gestorDTO.setEstado(productor.getGestor().getEstado());
            gestorDTO.setActivo(productor.getGestor().getActivo());
            gestorDTO.setAreaGestion(productor.getGestor().getTipoArea());
            dto.setGestor(gestorDTO);
        }

        return dto;
    }
}