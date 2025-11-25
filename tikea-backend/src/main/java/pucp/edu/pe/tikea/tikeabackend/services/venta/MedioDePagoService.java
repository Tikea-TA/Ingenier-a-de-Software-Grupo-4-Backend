package pucp.edu.pe.tikea.tikeabackend.services.venta;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pucp.edu.pe.tikea.tikeabackend.model.infraestructura.TipoEstadoAproDes;
import pucp.edu.pe.tikea.tikeabackend.model.venta.MedioDePago;
import pucp.edu.pe.tikea.tikeabackend.model.venta.TipoMedioDePago;
import pucp.edu.pe.tikea.tikeabackend.repository.venta.MedioDePagoRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedioDePagoService {

    private final MedioDePagoRepository medioDePagoRepository;

    // Registrar medio de pago
    @Transactional
    public MedioDePago registrar(MedioDePago medioDePago) {
        medioDePago.setActivo(1); // aseguramos que esté activo
        return medioDePagoRepository.save(medioDePago);
    }

    // Obtener por ID (solo activos)
    public MedioDePago obtenerPorId(Integer id) {
        return medioDePagoRepository.findByIdMedioDePagoAndActivo(id, 1)
                .orElseThrow(() -> new RuntimeException("Medio de pago no encontrado o inactivo"));
    }

    // Listar activos
    public List<MedioDePago> listarActivos() {
        return medioDePagoRepository.findByActivo(1);
    }

    // Actualizar medio de pago
    @Transactional
    public MedioDePago actualizar(Integer id, MedioDePago datos) {

        MedioDePago medio = obtenerPorId(id);

        medio.setTipoPago(datos.getTipoPago());
        medio.setEstado(datos.getEstado());
        medio.setComision(datos.getComision());
        medio.setPasarelaIntegracion(datos.getPasarelaIntegracion());
        medio.setValidacionSunat(datos.getValidacionSunat());

        return medioDePagoRepository.save(medio);
    }

    // Eliminado lógico
    @Transactional
    public void eliminar(Integer id) {
        MedioDePago medio = obtenerPorId(id);
        medio.setActivo(0);
        medioDePagoRepository.save(medio);
    }

    // CONSULTAS PERSONALIZADAS

    public List<MedioDePago> buscarPorTipo(TipoMedioDePago tipo) {
        return medioDePagoRepository.findByTipoPago(tipo);
    }

    public List<MedioDePago> buscarPorEstado(TipoEstadoAproDes estado) {
        return medioDePagoRepository.findByEstado(estado);
    }

    public List<MedioDePago> buscarPorPasarela(String pasarela) {
        return medioDePagoRepository.findByPasarelaIntegracion(pasarela);
    }

    public List<MedioDePago> buscarActivosPorTipo(TipoMedioDePago tipo) {
        return medioDePagoRepository.findByTipoPagoAndActivo(tipo, 1);
    }

    public List<MedioDePago> buscarActivosPorEstado(TipoEstadoAproDes estado) {
        return medioDePagoRepository.findByEstadoAndActivo(estado, 1);
    }

    public List<MedioDePago> buscarPorValidacionSunat(boolean validacion) {
        return medioDePagoRepository.findByValidacionSunat(validacion);
    }
}
