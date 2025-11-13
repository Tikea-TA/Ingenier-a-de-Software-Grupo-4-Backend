package pucp.edu.pe.tikea.tikeabackend.services.usuarios;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pucp.edu.pe.tikea.tikeabackend.DTO.usuarios.GestorRegistroRequest;
import pucp.edu.pe.tikea.tikeabackend.DTO.usuarios.GestorModificacionRequest;
import pucp.edu.pe.tikea.tikeabackend.DTO.usuarios.GestorResponse;
import pucp.edu.pe.tikea.tikeabackend.model.usuarios.Gestor;
import pucp.edu.pe.tikea.tikeabackend.model.usuarios.TipoArea;
import pucp.edu.pe.tikea.tikeabackend.model.usuarios.TipoEstado;
import lombok.RequiredArgsConstructor;
import pucp.edu.pe.tikea.tikeabackend.repository.usuarios.GestorRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class GestorService {

    private final GestorRepository gestorRepository;

    public GestorResponse registrarGestor(GestorRegistroRequest request) {
        // Validar que el correo no esté registrado
        if (gestorRepository.findByCorreoIgnoreCase(request.getEmail()).isPresent()) {
            throw new RuntimeException("El correo ya está registrado: " + request.getEmail());
        }

        // Validar que el nombre de usuario no esté registrado
        if (gestorRepository.findByNombreUserIgnoreCase(request.getNombreUsuario()).isPresent()) {
            throw new RuntimeException("El nombre de usuario ya está registrado: " + request.getNombreUsuario());
        }

        // Validar que el DNI no esté registrado
        if (gestorRepository.findByDNI(request.getDni()).isPresent()) {
            throw new RuntimeException("El DNI ya está registrado: " + request.getDni());
        }

        // Crear la entidad Gestor
        Gestor gestor = new Gestor();
        gestor.setNombre(request.getNombre());
        gestor.setApellidos(request.getApellidos());
        gestor.setCorreo(request.getEmail());
        gestor.setTelefono(request.getTelefono());
        gestor.setNombreUser(request.getNombreUsuario());
        gestor.setPassword(request.getPassword());
        gestor.setDNI(request.getDni());
        gestor.setTipoArea(request.getTipoArea());
        gestor.setEstado(TipoEstado.ACTIVO);

        // Guardar en la BD
        Gestor gestorGuardado = gestorRepository.save(gestor);

        // Retornar como DTO
        return convertirAResponseDTO(gestorGuardado);
    }

    public GestorResponse obtenerGestor(Integer idGestor) {
        Gestor gestor = gestorRepository.findById(idGestor)
                .orElseThrow(() -> new RuntimeException("Gestor no encontrado con ID: " + idGestor));

        return convertirAResponseDTO(gestor);
    }

    public List<GestorResponse> obtenerTodosLosGestores() {
        return gestorRepository.findAll()
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    public List<GestorResponse> obtenerGestoresPorArea(TipoArea tipoArea) {
        return gestorRepository.findByTipoArea(tipoArea)
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    public List<GestorResponse> obtenerGestoresPorEstado(TipoEstado estado) {
        return gestorRepository.findByEstado(estado)
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    public List<GestorResponse> obtenerGestoresActivosPorArea(TipoArea tipoArea) {
        return gestorRepository.findByEstadoAndTipoArea(TipoEstado.ACTIVO, tipoArea)
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    public GestorResponse actualizarGestor(Integer idGestor, GestorModificacionRequest request) {
        Gestor gestor = gestorRepository.findById(idGestor)
                .orElseThrow(() -> new RuntimeException("Gestor no encontrado con ID: " + idGestor));

        // Validar que el correo no esté usado por otro Gestor
        if (request.getCorreo() != null) {
            Optional<Gestor> gestorConCorreo = gestorRepository.findByCorreoIgnoreCase(request.getCorreo());
            if (gestorConCorreo.isPresent() && !gestorConCorreo.get().getIdUsuario().equals(idGestor)) {
                throw new RuntimeException("El correo ya está registrado por otro gestor");
            }
            gestor.setCorreo(request.getCorreo());
        }

        // Actualizar campos
        if (request.getPassword() != null) {
            gestor.setPassword(request.getPassword());
        }
        if (request.getNombreUsuario() != null) {
            gestor.setNombreUser(request.getNombreUsuario());
        }
        if (request.getTelefono() != null) {
            gestor.setTelefono(request.getTelefono());
        }
        if (request.getTipoArea() != null) {
            gestor.setTipoArea(request.getTipoArea());
        }

        // Guardar cambios
        Gestor gestorActualizado = gestorRepository.save(gestor);

        return convertirAResponseDTO(gestorActualizado);
    }

    public void eliminarGestor(Integer idGestor) {
        Gestor gestor = gestorRepository.findById(idGestor)
                .orElseThrow(() -> new RuntimeException("Gestor no encontrado con ID: " + idGestor));

        gestorRepository.delete(gestor);
    }

    private GestorResponse convertirAResponseDTO(Gestor gestor) {
        GestorResponse dto = new GestorResponse();
        dto.setIdUsuario(gestor.getIdUsuario());
        dto.setNombre(gestor.getNombre());
        dto.setApellidos(gestor.getApellidos());
        dto.setCorreo(gestor.getCorreo());
        dto.setTelefono(gestor.getTelefono());
        dto.setNombreUsuario(gestor.getNombreUser());
        dto.setDni(gestor.getDNI());
        dto.setEstado(gestor.getEstado());
        dto.setActivo(gestor.getActivo());
        dto.setAreaGestion(gestor.getTipoArea());

        return dto;
    }
}
