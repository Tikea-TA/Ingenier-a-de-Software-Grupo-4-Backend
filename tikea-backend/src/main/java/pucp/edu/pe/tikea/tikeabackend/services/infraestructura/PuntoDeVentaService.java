package pucp.edu.pe.tikea.tikeabackend.services.infraestructura;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pucp.edu.pe.tikea.tikeabackend.model.infraestructura.PuntoDeVenta;
import pucp.edu.pe.tikea.tikeabackend.model.infraestructura.TipoEstadoAproDes;
import pucp.edu.pe.tikea.tikeabackend.model.infraestructura.TipoPuntoVenta;
import pucp.edu.pe.tikea.tikeabackend.repository.infraestructura.PuntoDeVentaRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PuntoDeVentaService {

    private final PuntoDeVentaRepository puntoDeVentaRepository;

    // REGISTRAR
    public PuntoDeVenta registrar(PuntoDeVenta punto) {
        return puntoDeVentaRepository.save(punto);
    }

    // MODIFICAR
    public PuntoDeVenta modificar(Integer id, PuntoDeVenta nuevosDatos) {
        PuntoDeVenta pv = puntoDeVentaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Punto de Venta no encontrado"));

        pv.setNombrePuntoVenta(nuevosDatos.getNombrePuntoVenta());
        pv.setTipo(nuevosDatos.getTipo());
        pv.setEstado(nuevosDatos.getEstado());
        pv.setFechaInicioOperacion(nuevosDatos.getFechaInicioOperacion());
        pv.setFechaFinOperacion(nuevosDatos.getFechaFinOperacion());

        return puntoDeVentaRepository.save(pv);
    }

    // ELIMINACIÓN LÓGICA
    public void eliminarLogico(Integer id) {
        PuntoDeVenta pv = puntoDeVentaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Punto de Venta no encontrado"));

        pv.setActivo(0);
        puntoDeVentaRepository.save(pv);
    }

    // ACTIVAR
    public void activar(Integer id) {
        PuntoDeVenta pv = puntoDeVentaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Punto de Venta no encontrado"));

        pv.setActivo(1);
        puntoDeVentaRepository.save(pv);
    }

    // BUSCAR POR ID
    public PuntoDeVenta buscarPorId(Integer id) {
        return puntoDeVentaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Punto de Venta no encontrado"));
    }

    // LISTAR TODOS ACTIVOS
    public List<PuntoDeVenta> listarActivos() {
        return puntoDeVentaRepository.findAllActive();
    }

    // LISTAR TODOS
    public List<PuntoDeVenta> listarTodos() {
        return puntoDeVentaRepository.findAll();
    }

    // BUSCAR POR TIPO
    public List<PuntoDeVenta> buscarPorTipo(TipoPuntoVenta tipo) {
        return puntoDeVentaRepository.findByTipo(tipo);
    }

    // BUSCAR ACTIVOS POR TIPO
    public List<PuntoDeVenta> buscarActivosPorTipo(TipoPuntoVenta tipo) {
        return puntoDeVentaRepository.findActiveByTipo(tipo);
    }

    // BUSCAR POR ESTADO
    public List<PuntoDeVenta> buscarPorEstado(TipoEstadoAproDes estado) {
        return puntoDeVentaRepository.findByEstado(estado);
    }

    // BUSCAR ACTIVOS POR ESTADO
    public List<PuntoDeVenta> buscarActivosPorEstado(TipoEstadoAproDes estado) {
        return puntoDeVentaRepository.findActiveByEstado(estado);
    }

    // BUSCAR POR RANGO DE FECHAS
    public List<PuntoDeVenta> buscarPorRangoFechas(LocalDateTime inicio, LocalDateTime fin) {
        return puntoDeVentaRepository.findByFechaInicioOperacionBetween(inicio, fin);
    }

    // BUSCAR ACTIVOS POR RANGO DE OPERACIÓN
    public List<PuntoDeVenta> buscarActivosPorRangoOperacion(LocalDateTime inicio, LocalDateTime fin) {
        return puntoDeVentaRepository.findActiveByRangoOperacion(inicio, fin);
    }
}