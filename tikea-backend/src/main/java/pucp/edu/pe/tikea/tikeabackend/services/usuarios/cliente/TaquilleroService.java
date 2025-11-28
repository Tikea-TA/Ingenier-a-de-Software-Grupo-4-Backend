package pucp.edu.pe.tikea.tikeabackend.services.usuarios.cliente;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pucp.edu.pe.tikea.tikeabackend.DTO.usuarios.TaquilleroRequest;
import pucp.edu.pe.tikea.tikeabackend.DTO.usuarios.TaquilleroResponse;
import pucp.edu.pe.tikea.tikeabackend.model.usuarios.Taquillero;
import pucp.edu.pe.tikea.tikeabackend.model.infraestructura.PuntoDeVenta;
import pucp.edu.pe.tikea.tikeabackend.model.usuarios.TipoEstado;
import pucp.edu.pe.tikea.tikeabackend.repository.usuarios.TaquilleroRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TaquilleroService {

    private final TaquilleroRepository repository;
    private final BCryptPasswordEncoder encoder;

    public TaquilleroService(TaquilleroRepository repository, BCryptPasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    private TaquilleroResponse toDTO(Taquillero taquillero) {
        TaquilleroResponse response = new TaquilleroResponse();

        // Campos de Usuario
        response.setIdTaquillero(taquillero.getIdUsuario());
        response.setNombre(taquillero.getNombre());
        response.setApellidos(taquillero.getApellidos());
        response.setDNI(taquillero.getDNI());
        response.setCorreo(taquillero.getCorreo());
        response.setTelefono(taquillero.getTelefono());
        response.setNombreUsuario(taquillero.getNombreUser());
        response.setEstado(taquillero.getEstado());

        // Campos específicos de Taquillero (Actualizados según el DTO proporcionado)
        response.setRol(taquillero.getRol());
        response.setLocalesAsignados(taquillero.getLocalesAsignados());
        response.setFechaInicioAsignacion(taquillero.getFechaInicioAsignacion());
        response.setFechaFinAsignacion(taquillero.getFechaFinAsignacion());

        // Mapeo del ID del Punto de Venta
        response.setPuntoDeVentaId(
                taquillero.getPuntoDeVenta() != null ? taquillero.getPuntoDeVenta().getIdPuntoDeVenta() : null
        );

        return response;
    }

    private Taquillero toEntity(TaquilleroRequest dto) {
        PuntoDeVenta puntoDeVenta = null;
        if (dto.getPuntoDeVentaId() != null) {
            puntoDeVenta = new PuntoDeVenta();
            puntoDeVenta.setIdPuntoDeVenta(dto.getPuntoDeVentaId());
        }

        Taquillero nuevoTaquillero = new Taquillero();

        // Campos de Usuario
        nuevoTaquillero.setNombre(dto.getNombre());
        nuevoTaquillero.setApellidos(dto.getApellidos());
        nuevoTaquillero.setCorreo(dto.getCorreo());
        nuevoTaquillero.setNombreUser(dto.getNombreUsuario());
        nuevoTaquillero.setDNI(dto.getDni());
        nuevoTaquillero.setTelefono(dto.getTelefono());

        // Campos de Taquillero
        nuevoTaquillero.setPuntoDeVenta(puntoDeVenta);
        nuevoTaquillero.setRol(dto.getRol());
        nuevoTaquillero.setLocalesAsignados(dto.getLocalesAsignados() != null ? dto.getLocalesAsignados() : 0);
        nuevoTaquillero.setFechaInicioAsignacion(dto.getFechaInicioAsignacion());
        nuevoTaquillero.setFechaFinAsignacion(dto.getFechaFinAsignacion());

        // Valores por defecto
        nuevoTaquillero.setEstado(TipoEstado.ACTIVO);
        nuevoTaquillero.setActivo(1); // 1 para activo
        nuevoTaquillero.setFechaRegistro(LocalDateTime.now());
        nuevoTaquillero.setRequiereCambioPassword(false);


        // Aplicar codificación de contraseña
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            nuevoTaquillero.setPassword(encoder.encode(dto.getPassword()));
        }

        return nuevoTaquillero;
    }

    public TaquilleroResponse registrar(TaquilleroRequest dto) {
        Taquillero nuevoTaquillero = toEntity(dto);
        return toDTO(repository.save(nuevoTaquillero));
    }

    // Se elimina @Transactional
    public Optional<TaquilleroResponse> obtenerPorId(Integer id) {
        return repository.findById(id).map(this::toDTO);
    }

    public List<TaquilleroResponse> listarTodos() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public TaquilleroResponse actualizar(Integer id, TaquilleroRequest dto) {
        Taquillero taquilleroDb = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Taquillero no encontrado con ID: " + id));

        // Actualizar campos de Usuario
        if (dto.getNombre() != null) taquilleroDb.setNombre(dto.getNombre());
        if (dto.getApellidos() != null) taquilleroDb.setApellidos(dto.getApellidos());
        if (dto.getCorreo() != null) taquilleroDb.setCorreo(dto.getCorreo());
        if (dto.getNombreUsuario() != null) taquilleroDb.setNombreUser(dto.getNombreUsuario());
        if (dto.getDni() != null) taquilleroDb.setDNI(dto.getDni());
        if (dto.getTelefono() != null) taquilleroDb.setTelefono(dto.getTelefono());

        // Campos específicos de Taquillero
        if (dto.getPuntoDeVentaId() != null) {
            PuntoDeVenta pvActualizada = new PuntoDeVenta();
            pvActualizada.setIdPuntoDeVenta(dto.getPuntoDeVentaId());
            taquilleroDb.setPuntoDeVenta(pvActualizada);
        }
        if (dto.getRol() != null) taquilleroDb.setRol(dto.getRol());

        if (dto.getLocalesAsignados() != null && dto.getLocalesAsignados() >= 0) {
            taquilleroDb.setLocalesAsignados(dto.getLocalesAsignados());
        }

        // Fechas de asignación
        if (dto.getFechaInicioAsignacion() != null) {
            taquilleroDb.setFechaInicioAsignacion(dto.getFechaInicioAsignacion());
        }
        if (dto.getFechaFinAsignacion() != null) {
            taquilleroDb.setFechaFinAsignacion(dto.getFechaFinAsignacion());
        }

        // Actualización de contraseña si se proporciona
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            if (!dto.getPassword().startsWith("$2a$") && !dto.getPassword().startsWith("$2b$")) {
                taquilleroDb.setPassword(encoder.encode(dto.getPassword()));
                taquilleroDb.setRequiereCambioPassword(false);
            }
        }

        taquilleroDb.setFechaUltimaModificacion(LocalDateTime.now());

        return toDTO(repository.save(taquilleroDb));
    }

    // Se elimina @Transactional
    public TaquilleroResponse eliminarLogico(Integer id) {
        Taquillero t = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Taquillero no encontrado para eliminación: " + id));

        t.setActivo(0); // 0 para inactivo
        t.setEstado(TipoEstado.INACTIVO);
        t.setFechaUltimaModificacion(LocalDateTime.now());

        return toDTO(repository.save(t));
    }

    //                     CONSULTAS

    public List<TaquilleroResponse> listarPorPuntoVenta(PuntoDeVenta puntoDeVenta) {
        return repository.findByPuntoDeVenta(puntoDeVenta).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<TaquilleroResponse> listarPorRol(String rol) {
        return repository.findByRolIgnoreCase(rol).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<TaquilleroResponse> listarConLocalesAsignadosMayorIgual(int cantidad) {
        return repository.findByLocalesAsignadosGreaterThanEqual(cantidad).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<TaquilleroResponse> listarInicioAsignacionDespues(LocalDateTime fecha) {
        return repository.findByFechaInicioAsignacionAfter(fecha).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<TaquilleroResponse> listarFinAsignacionAntes(LocalDateTime fecha) {
        return repository.findByFechaFinAsignacionBefore(fecha).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<TaquilleroResponse> buscarPorPuntoVentaYRol(PuntoDeVenta puntoDeVenta, String rol) {
        return repository.findByPuntoDeVentaAndRolIgnoreCase(puntoDeVenta, rol).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<TaquilleroResponse> buscarAsignacionVigente(LocalDateTime fechaActual) {
        return repository.findAsignacionVigente(fechaActual).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<TaquilleroResponse> buscarPrimeroPorPuntoDeVentaYRol(PuntoDeVenta puntoDeVenta, String rol) {
        return repository.findFirstByPuntoDeVentaAndRolIgnoreCase(puntoDeVenta, rol)
                .map(this::toDTO);
    }
}