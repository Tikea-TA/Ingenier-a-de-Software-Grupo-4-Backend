package pucp.edu.pe.tikea.tikeabackend.services;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pucp.edu.pe.tikea.tikeabackend.DTO.PromocionRegistroRequest;
import pucp.edu.pe.tikea.tikeabackend.DTO.PromocionResponse;
import pucp.edu.pe.tikea.tikeabackend.DTO.RegistroClienteRequest;
import pucp.edu.pe.tikea.tikeabackend.model.*;
import pucp.edu.pe.tikea.tikeabackend.repository.EventoRepository;
import pucp.edu.pe.tikea.tikeabackend.repository.PromocionRepository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Service
public class PromocionService {

    private final PromocionRepository promocionRepository;
    private final EventoRepository eventoRepository;

    public PromocionService(PromocionRepository promocionRepository, EventoRepository eventoRepository) {
        this.promocionRepository = promocionRepository;
        this.eventoRepository = eventoRepository;
    }

    private PromocionResponse toDTO(Promocion p) {
        PromocionResponse dto = new PromocionResponse();
        dto.setIdPromocion(p.getIdPromocion());
        dto.setNombre(p.getNombre());
        dto.setDescripcion(p.getDescripcion());
        dto.setTipo(p.getTipo());
        dto.setValorDescuento(p.getValorDescuento());
        dto.setFechaInicio(p.getFechaInicio());
        dto.setFechaFin(p.getFechaFin());
        dto.setStockDisponible(p.getStockDisponible());
        dto.setStockUtilizado(p.getStockUtilizado());
        dto.setCondicionesCanal(p.getCondicionesCanal());
        dto.setCondicionesSector(p.getCondicionesSector());
        dto.setEstado(p.getEstado());
        dto.setFechaCreacion(p.getFechaCreacion());
        dto.setFechaUltimaModificacion(p.getFechaUltimaModificacion()); 
        
        if (p.getEvento() != null) {
            dto.setIdEvento(p.getEvento().getIdEvento()); 
        }
        
        return dto;
    }

    @Transactional
    public PromocionResponse registrar(PromocionRegistroRequest dto) {
        Evento evento = eventoRepository.findById(dto.getIdEvento())
                .orElseThrow(() -> new IllegalArgumentException("Evento no encontrado con ID: " + dto.getIdEvento()));

        Promocion p = new Promocion();
        p.setNombre(dto.getNombre());
        p.setDescripcion(dto.getDescripcion());
        p.setValorDescuento(dto.getValorDescuento());
        p.setFechaInicio(dto.getFechaInicio());
        p.setFechaFin(dto.getFechaFin());
        p.setStockDisponible(dto.getStockDisponible());
        p.setCondicionesSector(dto.getCondicionesSector());
        p.setTipo(dto.getTipo());
        p.setCondicionesCanal(dto.getCondicionesCanal());
        p.setStockUtilizado(0);
        p.setEstado(EstadoActividad.ACTIVA);
        p.setFechaCreacion(Timestamp.from(Instant.now()));
        p.setActivo(1);
        p.setEvento(evento);

        return toDTO(promocionRepository.save(p));
    }

    @Transactional
    public PromocionResponse actualizar(Integer id, RegistroClienteRequest.PromocionModificacionRequest dto) {
        Promocion p = buscarPromocionPorId(id);

        if (dto.getNombre() != null) p.setNombre(dto.getNombre());
        if (dto.getDescripcion() != null) p.setDescripcion(dto.getDescripcion());
        if (dto.getValorDescuento() != null) p.setValorDescuento(dto.getValorDescuento());
        if (dto.getFechaInicio() != null) p.setFechaInicio(dto.getFechaInicio());
        if (dto.getFechaFin() != null) p.setFechaFin(dto.getFechaFin());
        if (dto.getStockDisponible() != null) p.setStockDisponible(dto.getStockDisponible());
        if (dto.getCondicionesSector() != null) p.setCondicionesSector(dto.getCondicionesSector());
        if (dto.getTipo() != null) p.setTipo(dto.getTipo());
        if (dto.getCondicionesCanal() != null) p.setCondicionesCanal(dto.getCondicionesCanal());

        p.setFechaUltimaModificacion(Timestamp.from(Instant.now()));

        return toDTO(promocionRepository.save(p));
    }

    @Transactional
    public PromocionResponse inactivar(Integer id) {
        Promocion p = buscarPromocionPorId(id);

        p.setEstado(EstadoActividad.INACTIVA);
        p.setFechaUltimaModificacion(Timestamp.from(Instant.now()));

        return toDTO(promocionRepository.save(p));
    }

    public List<PromocionResponse> listarTodas() {
        return promocionRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public PromocionResponse buscarPorId(Integer id) {
        Promocion p = buscarPromocionPorId(id);
        return toDTO(p);
    }

    public List<PromocionResponse> listarPorEvento(Integer idEvento) {
        return promocionRepository.findByEventoIdEvento(idEvento)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public List<PromocionResponse> listarActivasPorEvento(Integer idEvento) {
        return promocionRepository.findByEventoIdEventoAndEstado(idEvento, EstadoActividad.ACTIVA)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    private Promocion buscarPromocionPorId(Integer id) {
        return promocionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Promoción no encontrada: " + id));
    }
}