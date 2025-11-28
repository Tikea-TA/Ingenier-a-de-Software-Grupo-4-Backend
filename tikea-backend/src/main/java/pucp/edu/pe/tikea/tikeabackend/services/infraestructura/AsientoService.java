package pucp.edu.pe.tikea.tikeabackend.services.infraestructura;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pucp.edu.pe.tikea.tikeabackend.DTO.infraestructura.AsientoRequest;
import pucp.edu.pe.tikea.tikeabackend.DTO.infraestructura.AsientoResponse;
import pucp.edu.pe.tikea.tikeabackend.model.infraestructura.Asiento;
import pucp.edu.pe.tikea.tikeabackend.model.infraestructura.TipoEstadoAsiento;
import pucp.edu.pe.tikea.tikeabackend.model.infraestructura.Zona;
import pucp.edu.pe.tikea.tikeabackend.repository.infraestructura.AsientoRepository;
import pucp.edu.pe.tikea.tikeabackend.repository.infraestructura.ZonaRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AsientoService {

    private final AsientoRepository asientoRepository;
    private final ZonaRepository zonaRepository;

    //      REGISTRAR
    public AsientoResponse registrar(AsientoRequest request) {

        Zona zona = zonaRepository.findById(request.getIdZona())
                .orElseThrow(() -> new RuntimeException("Zona no encontrada"));

        Asiento asiento = new Asiento();
        asiento.setZona(zona);
        asiento.setFilaAsiento(request.getFilaAsiento());
        asiento.setColumnaAsiento(request.getColumnaAsiento());
        asiento.setEstado(request.getEstado());
        asiento.setActivo(1);
        asiento.setCosto(request.getCosto());
        asientoRepository.save(asiento);

        return convertirAResponse(asiento);
    }

    //      MODIFICAR
    public AsientoResponse modificar(Integer id, AsientoRequest request) {

        Asiento asiento = asientoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Asiento no encontrado"));

        Zona zona = zonaRepository.findById(request.getIdZona())
                .orElseThrow(() -> new RuntimeException("Zona no encontrada"));

        asiento.setZona(zona);
        asiento.setFilaAsiento(request.getFilaAsiento());
        asiento.setColumnaAsiento(request.getColumnaAsiento());
        asiento.setEstado(request.getEstado());

        asientoRepository.save(asiento);

        return convertirAResponse(asiento);
    }

    //   OBTENER POR ID
    public AsientoResponse obtenerPorId(Integer id) {
        Asiento asiento = asientoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Asiento no encontrado"));

        return convertirAResponse(asiento);
    }

    //   LISTAR ACTIVOS
    public List<AsientoResponse> listarActivos() {
        return asientoRepository.findAllActive()
                .stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    //   LISTAR POR ZONA
    public List<AsientoResponse> listarPorZona(Integer idZona) {
        return asientoRepository.findActiveByZona(idZona)
                .stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    //   LISTAR POR ESTADO
    public List<AsientoResponse> listarPorEstado(TipoEstadoAsiento estado) {
        return asientoRepository.findByEstado(estado)
                .stream()
                .filter(Asiento::estActivo)
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    //   ELIMINACIÓN LÓGICA
    public void eliminar(Integer id) {
        Asiento asiento = asientoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Asiento no encontrado"));

        asiento.setActivo(0);
        asientoRepository.save(asiento);
    }

    //  CONVERTIR A RESPONSE
    private AsientoResponse convertirAResponse(Asiento asiento) {
        AsientoResponse response = new AsientoResponse();

        response.setIdAsiento(asiento.getIdAsiento());
        response.setIdZona(asiento.getZona().getIdZona());
        response.setFilaAsiento(asiento.getFilaAsiento());
        response.setColumnaAsiento(asiento.getColumnaAsiento());
        response.setEstado(asiento.getEstado());
        response.setActivo(asiento.getActivo());

        return response;
    }
}
