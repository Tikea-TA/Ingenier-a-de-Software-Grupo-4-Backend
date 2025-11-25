package pucp.edu.pe.tikea.tikeabackend.services.infraestructura;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pucp.edu.pe.tikea.tikeabackend.DTO.infraestructura.ZonaRequest;
import pucp.edu.pe.tikea.tikeabackend.DTO.infraestructura.ZonaResponse;
import pucp.edu.pe.tikea.tikeabackend.model.infraestructura.Establecimiento;
import pucp.edu.pe.tikea.tikeabackend.model.infraestructura.Zona;
import pucp.edu.pe.tikea.tikeabackend.repository.infraestructura.EstablecimientoRepository;
import pucp.edu.pe.tikea.tikeabackend.repository.infraestructura.ZonaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ZonaService {
    private final ZonaRepository zonaRepository;
    private final EstablecimientoRepository establecimientoRepository;

    public ZonaService(ZonaRepository zonaRepository, EstablecimientoRepository establecimientoRepository) {
        this.zonaRepository = zonaRepository;
        this.establecimientoRepository = establecimientoRepository;
    }

    public ZonaResponse crearZona(ZonaRequest dto){

        Establecimiento establecimiento = establecimientoRepository.findById(dto.getIdEstablecimiento())
                .orElseThrow(() -> new RuntimeException("Establecimiento no encontrado"));

        Zona zona = new  Zona();

        zona.setEstablecimiento(establecimiento);
        zona.setNombreZona( dto.getNombreZona());
        zona.setAforo( dto.getAforo());
        zona.setTipo(dto.getTipo());
        zona.setDistribucionAsientos(dto.getDistribucionAsientos());
        zona.setRestriccionesSeguridad(dto.getRestriccionesSeguridad());
        zona.setAsientosDisponibles(dto.getAsientosDisponibles());
        zona.setAsientosOcupados(dto.getAsientosOcupados());
        zona.setPrecio(dto.getPrecio());

        zona = zonaRepository.save(zona);

        return toDTO(zona);
    }



    public List<ZonaResponse> listarZonas() {
        return zonaRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    private ZonaResponse toDTO(Zona zona) {
        ZonaResponse dto = new ZonaResponse();

        dto.setIdZona(zona.getIdZona());
        dto.setIdEstablecimiento(zona.getEstablecimiento().getIdEstablecimiento());

        dto.setNombreZona(zona.getNombreZona());
        dto.setAforo(zona.getAforo());
        dto.setTipo(zona.getTipo());
        dto.setDistribucionAsientos(zona.getDistribucionAsientos());
        dto.setRestriccionesSeguridad(zona.getRestriccionesSeguridad());
        dto.setAsientosDisponibles(zona.getAsientosDisponibles());
        dto.setAsientosOcupados(zona.getAsientosOcupados());
        dto.setPrecio(zona.getPrecio());

        dto.setFechaCreacion(zona.getFechaCreacion());
        dto.setFechaUltimaModificacion(zona.getFechaUltimaModificacion());
        dto.setActivo(zona.getActivo());

        return dto;
    }

    public List<ZonaResponse> listarZonasActivasPorEstablecimiento(Integer idEstablecimiento) {

        List<Zona> zonas = zonaRepository
                .findByEstablecimiento_IdEstablecimientoAndActivo(idEstablecimiento, 1);

        return zonas.stream()
                .map(this::toDTO)
                .toList();
    }

}

