package pucp.edu.pe.tikea.tikeabackend.services.venta;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pucp.edu.pe.tikea.tikeabackend.DTO.venta.MedioDePagoRequest;
import pucp.edu.pe.tikea.tikeabackend.DTO.venta.MedioDePagoResponse;
import pucp.edu.pe.tikea.tikeabackend.model.infraestructura.TipoEstadoAproDes;
import pucp.edu.pe.tikea.tikeabackend.model.venta.MedioDePago;
import pucp.edu.pe.tikea.tikeabackend.model.venta.TipoMedioDePago;
import pucp.edu.pe.tikea.tikeabackend.repository.venta.MedioDePagoRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MedioDePagoService {

    private final MedioDePagoRepository medioDePagoRepository;

    // --- MAPPERS ---

    private MedioDePagoResponse toDTO(MedioDePago medio) {
        if (medio == null) {
            throw new RuntimeException("Medio de pago no encontrado o inactivo");
        }
        return MedioDePagoResponse.builder()
                .idMedioDePago(medio.getIdMedioDePago())
                .tipoPago(medio.getTipoPago() != null ? medio.getTipoPago().name() : null)
                .estado(medio.getEstado() != null ? medio.getEstado().name() : null)
                .comision(medio.getComision())
                .pasarelaIntegracion(medio.getPasarelaIntegracion())
                .validacionSunat(medio.getValidacionSunat())
                .fechaCreacion(medio.getFechaCreacion())
                .fechaUltimaCreacion(medio.getFechaUltimaCreacion())
                .activo(medio.getActivo())
                .build();
    }

    private MedioDePago toEntity(MedioDePagoRequest request) {
        MedioDePago entity = new MedioDePago();

        // Conversión de Strings a Enums
        try {
            if (request.getTipoPago() != null) {
                entity.setTipoPago(TipoMedioDePago.valueOf(request.getTipoPago().toUpperCase()));
            }
            if (request.getEstado() != null) {
                entity.setEstado(TipoEstadoAproDes.valueOf(request.getEstado().toUpperCase()));
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Valor de enumeración inválido: " + e.getMessage());
        }

        // Asignación de campos
        entity.setComision(request.getComision() != null ? request.getComision() : 0.0);
        entity.setPasarelaIntegracion(request.getPasarelaIntegracion());
        entity.setValidacionSunat(request.getValidacionSunat() != null ? request.getValidacionSunat() : false);

        // Campos de control
        entity.setFechaCreacion(LocalDateTime.now());
        entity.setActivo(1);

        return entity;
    }

    // --- CRUD Y OPERACIONES PRINCIPALES ---
    // Registrar medio de pago
    @Transactional
    public MedioDePagoResponse registrar(MedioDePagoRequest request) {
        MedioDePago medioDePago = toEntity(request);
        // La entidad ya está marcada como activo(1) en el mapper toEntity
        return toDTO(medioDePagoRepository.save(medioDePago));
    }

    // Obtener por ID (solo activos)
    public MedioDePagoResponse obtenerPorId(Integer id) {
        return medioDePagoRepository.findByIdMedioDePagoAndActivo(id, 1)
                .map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Medio de pago no encontrado o inactivo con ID: " + id));
    }

    // Listar activos
    public List<MedioDePagoResponse> listarActivos() {
        return medioDePagoRepository.findByActivo(1).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Actualizar medio de pago (parcial)
    @Transactional
    public MedioDePagoResponse actualizar(Integer id, MedioDePagoRequest datos) {

        MedioDePago medio = medioDePagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medio de pago no encontrado con ID: " + id));

        // Actualización parcial: solo si el campo NO es nulo en el request
        if (datos.getTipoPago() != null) {
            try {
                medio.setTipoPago(TipoMedioDePago.valueOf(datos.getTipoPago().toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Tipo de Pago inválido: " + datos.getTipoPago());
            }
        }
        if (datos.getEstado() != null) {
            try {
                medio.setEstado(TipoEstadoAproDes.valueOf(datos.getEstado().toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Estado inválido: " + datos.getEstado());
            }
        }
        if (datos.getComision() != null) {
            medio.setComision(datos.getComision());
        }
        if (datos.getPasarelaIntegracion() != null) {
            medio.setPasarelaIntegracion(datos.getPasarelaIntegracion());
        }
        if (datos.getValidacionSunat() != null) {
            medio.setValidacionSunat(datos.getValidacionSunat());
        }

        medio.setFechaUltimaCreacion(LocalDateTime.now()); // Actualizar timestamp de modificación

        return toDTO(medioDePagoRepository.save(medio));
    }

    // Eliminado lógico
    @Transactional
    public void eliminar(Integer id) {
        MedioDePago medio = medioDePagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medio de pago no encontrado con ID: " + id));

        medio.setActivo(0);
        medioDePagoRepository.save(medio);
    }

    // CONSULTAS PERSONALIZADAS (Retornan List<MedioDePagoResponse>)

    public List<MedioDePagoResponse> buscarPorTipo(TipoMedioDePago tipo) {
        return medioDePagoRepository.findByTipoPago(tipo).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<MedioDePagoResponse> buscarPorEstado(TipoEstadoAproDes estado) {
        return medioDePagoRepository.findByEstado(estado).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<MedioDePagoResponse> buscarPorPasarela(String pasarela) {
        return medioDePagoRepository.findByPasarelaIntegracion(pasarela).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<MedioDePagoResponse> buscarActivosPorTipo(TipoMedioDePago tipo) {
        return medioDePagoRepository.findByTipoPagoAndActivo(tipo, 1).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<MedioDePagoResponse> buscarActivosPorEstado(TipoEstadoAproDes estado) {
        return medioDePagoRepository.findByEstadoAndActivo(estado, 1).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<MedioDePagoResponse> buscarPorValidacionSunat(boolean validacion) {
        // Asumiendo que esta consulta puede devolver inactivos
        return medioDePagoRepository.findByValidacionSunat(validacion).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}