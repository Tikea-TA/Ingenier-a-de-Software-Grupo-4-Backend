package pucp.edu.pe.tikea.tikeabackend.services.usuarios.cliente;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pucp.edu.pe.tikea.tikeabackend.model.usuarios.Taquillero;
import pucp.edu.pe.tikea.tikeabackend.model.infraestructura.PuntoDeVenta;
import pucp.edu.pe.tikea.tikeabackend.repository.usuarios.TaquilleroRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TaquilleroService {

    private final TaquilleroRepository repository;

    public TaquilleroService(TaquilleroRepository repository) {
        this.repository = repository;
    }

    public Taquillero registrar(Taquillero taquillero) {
        return repository.save(taquillero); // activo = 1 por defecto
    }

    public Optional<Taquillero> obtenerPorId(Integer id) {
        return repository.findById(id);
    }

    public List<Taquillero> listarTodos() {
        return repository.findAll();
    }

    public Taquillero actualizar(Taquillero taquillero) {
        return repository.save(taquillero);
    }

    //                   ELIMINACIÓN LÓGICA

    @Transactional
    public boolean eliminarLogico(Integer id) {
        Optional<Taquillero> opt = repository.findById(id);

        if (opt.isEmpty()) return false;

        Taquillero t = opt.get();
        t.setActivo(0);
        repository.save(t);

        return true;
    }

    //                     CONSULTAS

    public List<Taquillero> listarPorPuntoVenta(PuntoDeVenta puntoDeVenta) {
        return repository.findByPuntoDeVenta(puntoDeVenta);
    }

    public List<Taquillero> listarPorRol(String rol) {
        return repository.findByRolIgnoreCase(rol);
    }

    public List<Taquillero> listarConLocalesAsignadosMayorIgual(int cantidad) {
        return repository.findByLocalesAsignadosGreaterThanEqual(cantidad);
    }

    public List<Taquillero> listarInicioAsignacionDespues(LocalDateTime fecha) {
        return repository.findByFechaInicioAsignacionAfter(fecha);
    }

    public List<Taquillero> listarFinAsignacionAntes(LocalDateTime fecha) {
        return repository.findByFechaFinAsignacionBefore(fecha);
    }

    public List<Taquillero> buscarPorPuntoVentaYRol(PuntoDeVenta puntoDeVenta, String rol) {
        return repository.findByPuntoDeVentaAndRolIgnoreCase(puntoDeVenta, rol);
    }

    public List<Taquillero> buscarAsignacionVigente(LocalDateTime fechaActual) {
        return repository.findAsignacionVigente(fechaActual);
    }

    public Optional<Taquillero> buscarPrimeroPorPuntoDeVentaYRol(PuntoDeVenta puntoDeVenta, String rol) {
        return repository.findFirstByPuntoDeVentaAndRolIgnoreCase(puntoDeVenta, rol);
    }
}
