package pucp.edu.pe.tikea.tikeabackend.services.usuarios.cliente;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pucp.edu.pe.tikea.tikeabackend.model.usuarios.Taquillero;
import pucp.edu.pe.tikea.tikeabackend.model.infraestructura.PuntoDeVenta;
import pucp.edu.pe.tikea.tikeabackend.model.usuarios.TipoEstado;
import pucp.edu.pe.tikea.tikeabackend.repository.usuarios.TaquilleroRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaquilleroService {

    private final TaquilleroRepository repository;
    private final BCryptPasswordEncoder encoder;

    @Transactional
    public Taquillero registrar(Taquillero taquillero) {
        if (taquillero.getPassword() != null && !taquillero.getPassword().isEmpty()) {
            // Verificar si ya está encriptada (por si acaso)
            if (!taquillero.getPassword().startsWith("$2a$") &&
                    !taquillero.getPassword().startsWith("$2b$")) {
                taquillero.setPassword(encoder.encode(taquillero.getPassword()));
            }
        }

        // Establecer valores por defecto si no están definidos
        if (taquillero.getEstado() == null) {
            taquillero.setEstado(TipoEstado.ACTIVO);
        }
        if (taquillero.getFechaRegistro() == null) {
            taquillero.setFechaRegistro(LocalDateTime.now());
        }

        return repository.save(taquillero); // activo = 1 por defecto
    }

    public Optional<Taquillero> obtenerPorId(Integer id) {
        return repository.findById(id);
    }

    public List<Taquillero> listarTodos() {
        return repository.findAll();
    }

    @Transactional
    public Taquillero actualizar(Taquillero taquillero) {
        Taquillero taquilleroDb = repository.findById(taquillero.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Taquillero no encontrado con ID: " + taquillero.getIdUsuario()));

        if (taquillero.getNombre() != null) taquilleroDb.setNombre(taquillero.getNombre());
        if (taquillero.getApellidos() != null) taquilleroDb.setApellidos(taquillero.getApellidos());
        if (taquillero.getCorreo() != null) taquilleroDb.setCorreo(taquillero.getCorreo());
        if (taquillero.getNombreUser() != null) taquilleroDb.setNombreUser(taquillero.getNombreUser());
        if (taquillero.getDNI() != null) taquilleroDb.setDNI(taquillero.getDNI());

        if (taquillero.getTelefono() != null) {
            taquilleroDb.setTelefono(taquillero.getTelefono());
        }

        if (taquillero.getEstado() != null) taquilleroDb.setEstado(taquillero.getEstado());
        if (taquillero.getActivo() != null) taquilleroDb.setActivo(taquillero.getActivo());

        if (taquillero.getPuntoDeVenta() != null) {
            taquilleroDb.setPuntoDeVenta(taquillero.getPuntoDeVenta());
        }

        if (taquillero.getRol() != null) {
            taquilleroDb.setRol(taquillero.getRol());
        }

        if (taquillero.getLocalesAsignados() > 0) {
            taquilleroDb.setLocalesAsignados(taquillero.getLocalesAsignados());
        }

        // Fechas de asignación
        if (taquillero.getFechaInicioAsignacion() != null) {
            taquilleroDb.setFechaInicioAsignacion(taquillero.getFechaInicioAsignacion());
        }
        if (taquillero.getFechaFinAsignacion() != null) {
            taquilleroDb.setFechaFinAsignacion(taquillero.getFechaFinAsignacion());
        }

        if (taquillero.getPassword() != null && !taquillero.getPassword().isEmpty()) {
            if (!taquillero.getPassword().startsWith("$2a$") &&
                    !taquillero.getPassword().startsWith("$2b$")) {
                taquilleroDb.setPassword(encoder.encode(taquillero.getPassword()));
                taquilleroDb.setRequiereCambioPassword(false);
            }
        }

        taquilleroDb.setFechaUltimaModificacion(LocalDateTime.now());

        return repository.save(taquilleroDb);
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
