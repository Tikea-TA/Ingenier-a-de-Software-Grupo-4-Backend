package pucp.edu.pe.tikea.tikeabackend.services.infraestructura;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pucp.edu.pe.tikea.tikeabackend.DTO.infraestructura.PuntoDeVentaRequest;
import pucp.edu.pe.tikea.tikeabackend.DTO.infraestructura.PuntoDeVentaResponse;
import pucp.edu.pe.tikea.tikeabackend.model.infraestructura.PuntoDeVenta;
import pucp.edu.pe.tikea.tikeabackend.model.infraestructura.TipoEstadoAproDes;
import pucp.edu.pe.tikea.tikeabackend.model.infraestructura.TipoPuntoVenta;
import pucp.edu.pe.tikea.tikeabackend.repository.infraestructura.PuntoDeVentaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PuntoDeVentaService {

    private final PuntoDeVentaRepository puntoDeVentaRepository;

    // --- MAPPERS ---

    // Convierte Entidad (PuntoDeVenta) a DTO (PuntoDeVentaResponse)
    private PuntoDeVentaResponse toDTO(PuntoDeVenta punto) {
        if (punto == null) return null;

        PuntoDeVentaResponse dto = new PuntoDeVentaResponse();
        dto.setIdPuntoDeVenta(punto.getIdPuntoDeVenta());
        dto.setNombrePuntoVenta(punto.getNombrePuntoVenta());
        dto.setDireccion(punto.getDireccion());
        dto.setEstado(punto.getEstado());
        dto.setTipo(punto.getTipo());
        dto.setFechaInicioOperacion(punto.getFechaInicioOperacion());
        dto.setFechaFinOperacion(punto.getFechaFinOperacion());
        dto.setFechaCreacion(punto.getFechaCreacion());
        dto.setActivo(punto.getActivo());
        return dto;
    }

    // Convierte DTO (PuntoDeVentaRequest) a Entidad (PuntoDeVenta) para CREACIÓN
    private PuntoDeVenta toEntity(PuntoDeVentaRequest dto) {
        PuntoDeVenta entity = new PuntoDeVenta();
        entity.setNombrePuntoVenta(dto.getNombrePuntoVenta());
        entity.setDireccion(dto.getDireccion());
        entity.setEstado(dto.getEstado());
        entity.setTipo(dto.getTipo());
        entity.setFechaInicioOperacion(dto.getFechaInicioOperacion());
        entity.setFechaFinOperacion(dto.getFechaFinOperacion());

        // Asignar valores por defecto para campos de control al crear
        entity.setFechaCreacion(LocalDateTime.now());
        entity.setActivo(1); // 1: activo por defecto

        return entity;
    }

    // --- CRUD Y OPERACIONES PRINCIPALES ---

    /**
     * Registra un nuevo Punto de Venta a partir de un DTO de solicitud.
     * @param request DTO con los datos del nuevo punto de venta.
     * @return DTO con los datos registrados y su ID.
     */
    public PuntoDeVentaResponse registrar(PuntoDeVentaRequest request) {
        PuntoDeVenta nuevoPunto = toEntity(request);
        return toDTO(puntoDeVentaRepository.save(nuevoPunto));
    }

    /**
     * Modifica un Punto de Venta existente, permitiendo actualizaciones parciales (campos nulos son ignorados).
     * @param id ID del punto de venta a modificar.
     * @param nuevosDatos DTO con los datos a actualizar (pueden ser parciales).
     * @return DTO del punto de venta modificado.
     */
    public PuntoDeVentaResponse modificar(Integer id, PuntoDeVentaRequest nuevosDatos) {
        PuntoDeVenta pv = puntoDeVentaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Punto de Venta no encontrado con ID: " + id));

        // Aplicar actualizaciones parciales (solo si el campo no es nulo en el DTO)
        if (nuevosDatos.getNombrePuntoVenta() != null) {
            pv.setNombrePuntoVenta(nuevosDatos.getNombrePuntoVenta());
        }
        if (nuevosDatos.getDireccion() != null) {
            pv.setDireccion(nuevosDatos.getDireccion());
        }
        if (nuevosDatos.getTipo() != null) {
            pv.setTipo(nuevosDatos.getTipo());
        }
        if (nuevosDatos.getEstado() != null) {
            pv.setEstado(nuevosDatos.getEstado());
        }
        if (nuevosDatos.getFechaInicioOperacion() != null) {
            pv.setFechaInicioOperacion(nuevosDatos.getFechaInicioOperacion());
        }
        if (nuevosDatos.getFechaFinOperacion() != null) {
            pv.setFechaFinOperacion(nuevosDatos.getFechaFinOperacion());
        }

        return toDTO(puntoDeVentaRepository.save(pv));
    }

    /**
     * Realiza la eliminación lógica (inactivación) de un Punto de Venta.
     * @param id ID del punto de venta a inactivar.
     */
    public void eliminarLogico(Integer id) {
        PuntoDeVenta pv = puntoDeVentaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Punto de Venta no encontrado para eliminación: " + id));

        pv.setActivo(0);
        puntoDeVentaRepository.save(pv);
    }

    /**
     * Activa un Punto de Venta previamente inactivado lógicamente.
     * @param id ID del punto de venta a activar.
     */
    public void activar(Integer id) {
        PuntoDeVenta pv = puntoDeVentaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Punto de Venta no encontrado"));

        pv.setActivo(1);
        puntoDeVentaRepository.save(pv);
    }

    /**
     * Busca un Punto de Venta por su ID y lo retorna como DTO.
     * @param id ID del punto de venta.
     * @return DTO del punto de venta.
     */
    public PuntoDeVentaResponse buscarPorId(Integer id) {
        return puntoDeVentaRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Punto de Venta no encontrado con ID: " + id));
    }

    // --- CONSULTAS (Todas retornan List<PuntoDeVentaResponse>) ---

    public List<PuntoDeVentaResponse> listarActivos() {
        return puntoDeVentaRepository.findAllActive().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<PuntoDeVentaResponse> listarTodos() {
        return puntoDeVentaRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<PuntoDeVentaResponse> buscarPorTipo(TipoPuntoVenta tipo) {
        return puntoDeVentaRepository.findByTipo(tipo).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<PuntoDeVentaResponse> buscarActivosPorTipo(TipoPuntoVenta tipo) {
        return puntoDeVentaRepository.findActiveByTipo(tipo).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<PuntoDeVentaResponse> buscarPorEstado(TipoEstadoAproDes estado) {
        return puntoDeVentaRepository.findByEstado(estado).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<PuntoDeVentaResponse> buscarActivosPorEstado(TipoEstadoAproDes estado) {
        return puntoDeVentaRepository.findActiveByEstado(estado).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<PuntoDeVentaResponse> buscarPorRangoFechas(LocalDateTime inicio, LocalDateTime fin) {
        return puntoDeVentaRepository.findByFechaInicioOperacionBetween(inicio, fin).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<PuntoDeVentaResponse> buscarActivosPorRangoOperacion(LocalDateTime inicio, LocalDateTime fin) {
        return puntoDeVentaRepository.findActiveByRangoOperacion(inicio, fin).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}