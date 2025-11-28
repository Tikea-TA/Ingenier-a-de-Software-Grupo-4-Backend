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
        Taquillero taquilleroExistente = repository.findById(taquillero.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Taquillero no encontrado con ID: " + taquillero.getIdUsuario()));

        // Mantener campos obligatorios del existente si no se envían
        if (taquillero.getEstado() == null) {
            taquillero.setEstado(taquilleroExistente.getEstado());
        }

        if (taquillero.getFechaRegistro() == null) {
            taquillero.setFechaRegistro(taquilleroExistente.getFechaRegistro());
        }

        if (taquillero.getNombre() == null) {
            taquillero.setNombre(taquilleroExistente.getNombre());
        }

        if (taquillero.getApellidos() == null) {
            taquillero.setApellidos(taquilleroExistente.getApellidos());
        }

        if (taquillero.getCorreo() == null) {
            taquillero.setCorreo(taquilleroExistente.getCorreo());
        }

        if (taquillero.getNombreUser() == null) {
            taquillero.setNombreUser(taquilleroExistente.getNombreUser());
        }

        if (taquillero.getDNI() == null) {
            taquillero.setDNI(taquilleroExistente.getDNI());
        }

        if (taquillero.getActivo() == null) {
            taquillero.setActivo(taquilleroExistente.getActivo());
        }

        // Si se está actualizando la contraseña, encriptarla
        if (taquillero.getPassword() != null && !taquillero.getPassword().isEmpty()) {
            // Verificar si ya está encriptada
            if (!taquillero.getPassword().startsWith("$2a$") &&
                    !taquillero.getPassword().startsWith("$2b$")) {
                taquillero.setPassword(encoder.encode(taquillero.getPassword()));
                taquillero.setRequiereCambioPassword(false);
            }
        } else {
            // Si no se envía contraseña, mantener la existente
            taquillero.setPassword(taquilleroExistente.getPassword());
        }

        // Actualizar fecha de modificación
        taquillero.setFechaUltimaModificacion(LocalDateTime.now());

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
