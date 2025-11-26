package pucp.edu.pe.tikea.tikeabackend.services.infraestructura;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pucp.edu.pe.tikea.tikeabackend.DTO.infraestructura.EstablecimientoRegistroRequest;
import pucp.edu.pe.tikea.tikeabackend.DTO.infraestructura.EstablecimientoModificacionRequest;
import pucp.edu.pe.tikea.tikeabackend.DTO.infraestructura.EstablecimientoResponse;
import pucp.edu.pe.tikea.tikeabackend.DTO.usuarios.GestorResponse;
import pucp.edu.pe.tikea.tikeabackend.model.infraestructura.Establecimiento;
import pucp.edu.pe.tikea.tikeabackend.model.infraestructura.TipoEstadoLocal;
import pucp.edu.pe.tikea.tikeabackend.model.infraestructura.TipoLocal;
import pucp.edu.pe.tikea.tikeabackend.model.usuarios.Gestor;
import lombok.RequiredArgsConstructor;
import jakarta.persistence.EntityManager;
import pucp.edu.pe.tikea.tikeabackend.repository.infraestructura.EstablecimientoRepository;
import pucp.edu.pe.tikea.tikeabackend.repository.usuarios.GestorRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class EstablecimientoService {

    private final EstablecimientoRepository establecimientoRepository;
    private final GestorRepository gestorRepository;
    private final EntityManager entityManager;

    public EstablecimientoResponse registrarEstablecimiento(EstablecimientoRegistroRequest request) {
        // Validar que el Gestor existe
        Gestor gestor = gestorRepository.findById(request.getIdGestor())
                .orElseThrow(() -> new RuntimeException("Gestor no encontrado con ID: " + request.getIdGestor()));

        // Validar que el nombre no esté registrado
        if (establecimientoRepository.findByNombreEstablecimientoIgnoreCase(request.getNombreEstablecimiento()).isPresent()) {
            throw new RuntimeException("El establecimiento ya está registrado con el nombre: " + request.getNombreEstablecimiento());
        }

        // Crear la entidad Establecimiento
        Establecimiento establecimiento = new Establecimiento();
        establecimiento.setGestor(gestor);
        establecimiento.setNombreEstablecimiento(request.getNombreEstablecimiento());
        establecimiento.setDireccionEstablecimiento(request.getDireccionEstablecimiento());
        establecimiento.setTipo(request.getTipo());
        establecimiento.setCapacidadMaxima(request.getCapacidadMaxima());
        establecimiento.setEstado(request.getEstado());
        establecimiento.setDocumentacionAdjunta(request.getDocumentacionAdjunta());
        establecimiento.setFechaVerificacion(LocalDateTime.now());

        // Guardar en la BD
        Establecimiento establecimientoGuardado = establecimientoRepository.save(establecimiento);

        // Refrescar la entidad para obtener el valor de activo de la BD
        Establecimiento establecimientoConGestor = establecimientoRepository.findByIdWithGestor(establecimientoGuardado.getIdEstablecimiento())
                .orElse(establecimientoGuardado);

        // Retornar como DTO
        return convertirAResponseDTO(establecimientoConGestor);
    }

    public EstablecimientoResponse obtenerEstablecimiento(Integer idEstablecimiento) {
        Establecimiento establecimiento = establecimientoRepository.findByIdWithGestor(idEstablecimiento)
                .orElseThrow(() -> new RuntimeException("Establecimiento no encontrado con ID: " + idEstablecimiento));

        return convertirAResponseDTO(establecimiento);
    }

    public List<EstablecimientoResponse> obtenerTodosLosEstablecimientos() {
        return establecimientoRepository.findAll()
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    public List<EstablecimientoResponse> obtenerEstablecimientosPorGestor(Integer idGestor) {
        Gestor gestor = gestorRepository.findById(idGestor)
                .orElseThrow(() -> new RuntimeException("Gestor no encontrado con ID: " + idGestor));

        return establecimientoRepository.findByGestor(gestor)
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    public List<EstablecimientoResponse> obtenerEstablecimientosPorTipo(TipoLocal tipo) {
        return establecimientoRepository.findByTipo(tipo)
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    public List<EstablecimientoResponse> obtenerEstablecimientosPorEstado(TipoEstadoLocal estado) {
        return establecimientoRepository.findByEstado(estado)
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    public List<EstablecimientoResponse> obtenerEstablecimientosActivos() {
        return establecimientoRepository.findByActivo(1)
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    public List<EstablecimientoResponse> obtenerEstablecimientosActivosPorGestor(Integer idGestor) {
        Gestor gestor = gestorRepository.findById(idGestor)
                .orElseThrow(() -> new RuntimeException("Gestor no encontrado con ID: " + idGestor));

        return establecimientoRepository.findByActivoAndGestor(1, gestor)
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    public EstablecimientoResponse actualizarEstablecimiento(Integer idEstablecimiento, EstablecimientoModificacionRequest request) {
        Establecimiento establecimiento = establecimientoRepository.findById(idEstablecimiento)
                .orElseThrow(() -> new RuntimeException("Establecimiento no encontrado con ID: " + idEstablecimiento));

        // Actualizar campos si no son null
        if (request.getNombreEstablecimiento() != null) {
            establecimiento.setNombreEstablecimiento(request.getNombreEstablecimiento());
        }
        if (request.getDireccionEstablecimiento() != null) {
            establecimiento.setDireccionEstablecimiento(request.getDireccionEstablecimiento());
        }
        if (request.getTipo() != null) {
            establecimiento.setTipo(request.getTipo());
        }
        if (request.getCapacidadMaxima() > 0) {
            establecimiento.setCapacidadMaxima(request.getCapacidadMaxima());
        }
        if (request.getEstado() != null) {
            establecimiento.setEstado(request.getEstado());
        }
        if (request.getDocumentacionAdjunta() != null) {
            establecimiento.setDocumentacionAdjunta(request.getDocumentacionAdjunta());
        }
        if (request.getFechaVerificacion() != null) {
            establecimiento.setFechaVerificacion(request.getFechaVerificacion());
        }

        establecimiento.setFechaUltimaModificacion(LocalDateTime.now());

        // Guardar cambios
        Establecimiento establecimientoActualizado = establecimientoRepository.save(establecimiento);

        return convertirAResponseDTO(establecimientoActualizado);
    }

    public void eliminarEstablecimiento(Integer idEstablecimiento) {
        Establecimiento establecimiento = establecimientoRepository.findById(idEstablecimiento)
                .orElseThrow(() -> new RuntimeException("Establecimiento no encontrado con ID: " + idEstablecimiento));

        establecimientoRepository.delete(establecimiento);
    }

    private EstablecimientoResponse convertirAResponseDTO(Establecimiento establecimiento) {
        EstablecimientoResponse dto = new EstablecimientoResponse();
        dto.setIdEstablecimiento(establecimiento.getIdEstablecimiento());
        dto.setNombreEstablecimiento(establecimiento.getNombreEstablecimiento());
        dto.setDireccionEstablecimiento(establecimiento.getDireccionEstablecimiento());
        dto.setTipo(establecimiento.getTipo());
        dto.setCapacidadMaxima(establecimiento.getCapacidadMaxima());
        dto.setEstado(establecimiento.getEstado());
        dto.setFechaVerificacion(establecimiento.getFechaVerificacion());
        dto.setFechaCreacion(establecimiento.getFechaCreacion());
        dto.setFechaUltimaModificacion(establecimiento.getFechaUltimaModificacion());
        dto.setActivo(establecimiento.getActivo());

        // Convertir Gestor a GestorResponse
        if (establecimiento.getGestor() != null) {
            GestorResponse gestorDTO = new GestorResponse();
            gestorDTO.setIdUsuario(establecimiento.getGestor().getIdUsuario());
            gestorDTO.setNombre(establecimiento.getGestor().getNombre());
            gestorDTO.setApellidos(establecimiento.getGestor().getApellidos());
            gestorDTO.setCorreo(establecimiento.getGestor().getCorreo());
            gestorDTO.setTelefono(establecimiento.getGestor().getTelefono());
            gestorDTO.setNombreUsuario(establecimiento.getGestor().getNombreUser());
            gestorDTO.setDni(establecimiento.getGestor().getDNI());
            gestorDTO.setEstado(establecimiento.getGestor().getEstado());
            gestorDTO.setActivo(establecimiento.getGestor().getActivo());
            gestorDTO.setAreaGestion(establecimiento.getGestor().getTipoArea());
            dto.setGestor(gestorDTO);
        }

        return dto;
    }

    public List<EstablecimientoResponse> obtenerEstablecimientosPorEstadoYGestor(TipoEstadoLocal estado, Integer idGestor) {
        Gestor gestor = gestorRepository.findById(idGestor)
                .orElseThrow(() -> new RuntimeException("Gestor no encontrado"));
    
        return establecimientoRepository.findByEstadoAndGestor(estado, gestor)
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }
    
    public byte[] obtenerDocumentacion(Integer idEstablecimiento) {
        Establecimiento establecimiento = establecimientoRepository.findById(idEstablecimiento)
                .orElseThrow(() -> new RuntimeException("Establecimiento no encontrado"));
                
        if (establecimiento.getDocumentacionAdjunta() == null) {
            throw new RuntimeException("El establecimiento no tiene documentación adjunta");
        }
        return establecimiento.getDocumentacionAdjunta();
    }
}
