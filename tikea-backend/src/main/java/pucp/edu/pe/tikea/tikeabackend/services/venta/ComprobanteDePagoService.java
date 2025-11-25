package pucp.edu.pe.tikea.tikeabackend.services.venta;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pucp.edu.pe.tikea.tikeabackend.model.venta.ComprobanteDePago;
import pucp.edu.pe.tikea.tikeabackend.model.venta.TipoComprobante;
import pucp.edu.pe.tikea.tikeabackend.model.venta.TipoTransferencia;
import pucp.edu.pe.tikea.tikeabackend.model.venta.Transaccion;
import pucp.edu.pe.tikea.tikeabackend.repository.venta.ComprobanteDePagoRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ComprobanteDePagoService {

    private final ComprobanteDePagoRepository repository;

    public ComprobanteDePagoService(ComprobanteDePagoRepository repository) {
        this.repository = repository;
    }

    //                    CRUD BÁSICO

    public ComprobanteDePago registrar(ComprobanteDePago comprobante) {
        return repository.save(comprobante);
    }

    public Optional<ComprobanteDePago> obtenerPorId(Integer id) {
        return repository.findById(id);
    }

    public List<ComprobanteDePago> listarTodos() {
        return repository.findAll();
    }

    public ComprobanteDePago actualizar(ComprobanteDePago comprobante) {
        return repository.save(comprobante);
    }

    //                   ELIMINACIÓN LÓGICA

    @Transactional
    public boolean eliminarLogico(Integer idComprobante) {
        Optional<ComprobanteDePago> opt = repository.findById(idComprobante);
        if (opt.isEmpty()) return false;

        ComprobanteDePago c = opt.get();
        c.setActivo(0);
        repository.save(c);
        return true;
    }

    //                CONSULTAS DEL REPOSITORY

    public Optional<ComprobanteDePago> buscarPorNumero(String numeroComprobante) {
        return repository.findByNumeroComprobante(numeroComprobante);
    }

    public Optional<ComprobanteDePago> buscarPorTransaccion(Transaccion transaccion) {
        return repository.findByTransaccion(transaccion);
    }

    public List<ComprobanteDePago> listarPorTipo(TipoComprobante tipo) {
        return repository.findByTipoComprobante(tipo);
    }

    public List<ComprobanteDePago> listarPorEstado(TipoTransferencia estado) {
        return repository.findByEstado(estado);
    }

    public List<ComprobanteDePago> listarActivos() {
        return repository.findByActivo(1);
    }

    public List<ComprobanteDePago> listarPorRangoFecha(LocalDateTime inicio, LocalDateTime fin) {
        return repository.findByFechaEmisionBetween(inicio, fin);
    }

    public List<ComprobanteDePago> listarPorValidacionSunat(String validacionSunat) {
        return repository.findByValidacionSunat(validacionSunat);
    }

    public List<ComprobanteDePago> listarPorSerie(String serie) {
        return repository.findBySerie(serie);
    }

    public List<ComprobanteDePago> listarMontoMayorIgual(Double monto) {
        return repository.findByMontoTotalGreaterThanEqual(monto);
    }

    public List<ComprobanteDePago> listarPorTipoYEstado(TipoComprobante tipo, TipoTransferencia estado) {
        return repository.findByTipoComprobanteAndEstado(tipo, estado);
    }

    public Optional<ComprobanteDePago> obtenerConTransaccion(Integer id) {
        return repository.findByIdWithTransaccion(id);
    }
}
