package pucp.edu.pe.tikea.tikeabackend.services.infraestructura;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pucp.edu.pe.tikea.tikeabackend.DTO.infraestructura.*;
import pucp.edu.pe.tikea.tikeabackend.DTO.usuarios.ProductorResponse;
import pucp.edu.pe.tikea.tikeabackend.DTO.usuarios.GestorResponse;
import pucp.edu.pe.tikea.tikeabackend.model.infraestructura.Evento;
import pucp.edu.pe.tikea.tikeabackend.model.infraestructura.CategoriaEvento;
import pucp.edu.pe.tikea.tikeabackend.model.infraestructura.EstadoEvento;
import pucp.edu.pe.tikea.tikeabackend.model.infraestructura.Establecimiento;
import pucp.edu.pe.tikea.tikeabackend.model.usuarios.Productor;
import pucp.edu.pe.tikea.tikeabackend.model.usuarios.Gestor;
import lombok.RequiredArgsConstructor;
import jakarta.persistence.EntityManager;
import pucp.edu.pe.tikea.tikeabackend.repository.infraestructura.EventoRepository;
import pucp.edu.pe.tikea.tikeabackend.repository.infraestructura.EstablecimientoRepository;
import pucp.edu.pe.tikea.tikeabackend.repository.usuarios.ProductorRepository;
import pucp.edu.pe.tikea.tikeabackend.repository.usuarios.GestorRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class EventoService {

    private final EventoRepository eventoRepository;
    private final EstablecimientoRepository establecimientoRepository;
    private final ProductorRepository productorRepository;
    private final GestorRepository gestorRepository;
    private final EntityManager entityManager;

    // =============== CREATE ===============
    public EventoResponse registrarEvento(EventoRegistroRequest request) {
        // Validar que el Establecimiento existe
        Establecimiento establecimiento = establecimientoRepository.findById(request.getIdEstablecimiento())
                .orElseThrow(() -> new RuntimeException("Establecimiento no encontrado con ID: " + request.getIdEstablecimiento()));

        // Validar que el Productor existe
        Productor productor = productorRepository.findById(request.getIdProductor())
                .orElseThrow(() -> new RuntimeException("Productor no encontrado con ID: " + request.getIdProductor()));

        // Validar que el Gestor existe
        Gestor gestor = gestorRepository.findById(request.getIdGestor())
                .orElseThrow(() -> new RuntimeException("Gestor no encontrado con ID: " + request.getIdGestor()));

        // Validar que el nombre no esté registrado
        if (eventoRepository.findByNombreEventoIgnoreCase(request.getNombreEvento()).isPresent()) {
            throw new RuntimeException("El evento ya está registrado con el nombre: " + request.getNombreEvento());
        }

        // Crear la entidad Evento
        Evento evento = new Evento();
        evento.setEstablecimiento(establecimiento);
        evento.setProductor(productor);
        evento.setGestor(gestor);
        evento.setNombreEvento(request.getNombreEvento());
        evento.setCategoria(request.getCategoria());
        evento.setFecha(request.getFecha());
        evento.setHoraInicio(request.getHoraInicio());
        evento.setHoraFin(request.getHoraFin());
        evento.setEstado(request.getEstado());
        evento.setAforoTotal(request.getAforoTotal());
        evento.setDocumentacionAdjunta(request.getDocumentacionAdjunta());
        evento.setBanner(request.getBanner());
        evento.setEstado(EstadoEvento.PENDIENTE_VALIDACION);

        // Guardar en la BD
        Evento eventoGuardado = eventoRepository.save(evento);

        // Refrescar la entidad para obtener el valor de activo de la BD
        eventoRepository.flush();
        entityManager.refresh(eventoGuardado);

        // Retornar como DTO
        return convertirAResponseDTO(eventoGuardado);
    }

    // =============== READ ===============
    public EventoResponse obtenerEvento(Integer idEvento) {
        Evento evento = eventoRepository.findById(idEvento)
                .orElseThrow(() -> new RuntimeException("Evento no encontrado con ID: " + idEvento));

        return convertirAResponseDTO(evento);
    }

    public List<EventoResponse> obtenerTodosLosEventos() {
        return eventoRepository.findAll()
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    public List<EventoResponse> obtenerEventosPorEstablecimiento(Integer idEstablecimiento) {
        Establecimiento establecimiento = establecimientoRepository.findById(idEstablecimiento)
                .orElseThrow(() -> new RuntimeException("Establecimiento no encontrado con ID: " + idEstablecimiento));

        return eventoRepository.findByEstablecimiento(establecimiento)
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    public List<EventoResponse> obtenerEventosPorProductor(Integer idProductor) {
        Productor productor = productorRepository.findById(idProductor)
                .orElseThrow(() -> new RuntimeException("Productor no encontrado con ID: " + idProductor));

        return eventoRepository.findByProductor(productor)
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    public List<EventoResponse> obtenerEventosPorGestor(Integer idGestor) {
        Gestor gestor = gestorRepository.findById(idGestor)
                .orElseThrow(() -> new RuntimeException("Gestor no encontrado con ID: " + idGestor));

        return eventoRepository.findByGestor(gestor)
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    public List<EventoResponse> obtenerEventosPorCategoria(CategoriaEvento categoria) {
        return eventoRepository.findByCategoria(categoria)
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    public List<EventoResponse> obtenerEventosPorEstado(EstadoEvento estado) {
        return eventoRepository.findByEstado(estado)
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    public List<EventoResponse> obtenerEventosPorFecha(LocalDate fecha) {
        return eventoRepository.findByFecha(fecha)
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    public List<EventoResponse> obtenerEventosPorRangoFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        return eventoRepository.findByFechaBetween(fechaInicio, fechaFin)
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    public List<EventoResponse> obtenerEventosActivos() {
        return eventoRepository.findByActivo(1)
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    // =============== UPDATE ===============
    public EventoResponse actualizarEvento(Integer idEvento, EventoModificacionRequest request) {
        Evento evento = eventoRepository.findById(idEvento)
                .orElseThrow(() -> new RuntimeException("Evento no encontrado con ID: " + idEvento));

        // Actualizar campos si no son null
        if (request.getNombreEvento() != null) {
            evento.setNombreEvento(request.getNombreEvento());
        }
        if (request.getCategoria() != null) {
            evento.setCategoria(request.getCategoria());
        }
        if (request.getFecha() != null) {
            evento.setFecha(request.getFecha());
        }
        if (request.getHoraInicio() != null) {
            evento.setHoraInicio(request.getHoraInicio());
        }
        if (request.getHoraFin() != null) {
            evento.setHoraFin(request.getHoraFin());
        }
        if (request.getEstado() != null) {
            evento.setEstado(request.getEstado());
        }
        if (request.getAforoTotal() > 0) {
            evento.setAforoTotal(request.getAforoTotal());
        }

        // Guardar cambios
        Evento eventoActualizado = eventoRepository.save(evento);

        return convertirAResponseDTO(eventoActualizado);
    }

    // =============== DELETE ===============
    public void eliminarEvento(Integer idEvento) {
        Evento evento = eventoRepository.findById(idEvento)
                .orElseThrow(() -> new RuntimeException("Evento no encontrado con ID: " + idEvento));

        eventoRepository.delete(evento);
    }

    // =============== MÉTODOS AUXILIARES ===============
    private EventoResponse convertirAResponseDTO(Evento evento) {
        EventoResponse dto = new EventoResponse();
        dto.setIdEvento(evento.getIdEvento());
        dto.setNombreEvento(evento.getNombreEvento());
        dto.setCategoria(evento.getCategoria());
        dto.setFecha(evento.getFecha());
        dto.setHoraInicio(evento.getHoraInicio());
        dto.setHoraFin(evento.getHoraFin());
        dto.setEstado(evento.getEstado());
        dto.setAforoTotal(evento.getAforoTotal());
        dto.setActivo(evento.getActivo());
        dto.setFechaVerificacion(evento.getFechaVerificacion());

        // Convertir Establecimiento a EstablecimientoResponse
        if (evento.getEstablecimiento() != null) {
            Establecimiento est = establecimientoRepository.findByIdWithGestor(evento.getEstablecimiento().getIdEstablecimiento())
                    .orElse(evento.getEstablecimiento());

            EstablecimientoResponse establecimientoDTO = new EstablecimientoResponse();
            establecimientoDTO.setIdEstablecimiento(est.getIdEstablecimiento());
            establecimientoDTO.setNombreEstablecimiento(est.getNombreEstablecimiento());
            establecimientoDTO.setDireccionEstablecimiento(est.getDireccionEstablecimiento());
            establecimientoDTO.setTipo(est.getTipo());
            establecimientoDTO.setCapacidadMaxima(est.getCapacidadMaxima());
            establecimientoDTO.setEstado(est.getEstado());
            establecimientoDTO.setFechaVerificacion(est.getFechaVerificacion());
            establecimientoDTO.setFechaCreacion(est.getFechaCreacion());
            establecimientoDTO.setFechaUltimaModificacion(est.getFechaUltimaModificacion());
            establecimientoDTO.setActivo(est.getActivo());

            // Convertir Gestor del Establecimiento
            if (est.getGestor() != null) {
                GestorResponse gestorEstDTO = new GestorResponse();
                gestorEstDTO.setIdUsuario(est.getGestor().getIdUsuario());
                gestorEstDTO.setNombre(est.getGestor().getNombre());
                gestorEstDTO.setApellidos(est.getGestor().getApellidos());
                gestorEstDTO.setCorreo(est.getGestor().getCorreo());
                gestorEstDTO.setTelefono(est.getGestor().getTelefono());
                gestorEstDTO.setNombreUsuario(est.getGestor().getNombreUser());
                gestorEstDTO.setDni(est.getGestor().getDNI());
                gestorEstDTO.setEstado(est.getGestor().getEstado());
                gestorEstDTO.setActivo(est.getGestor().getActivo());
                gestorEstDTO.setAreaGestion(est.getGestor().getTipoArea());
                establecimientoDTO.setGestor(gestorEstDTO);
            }

            dto.setEstablecimiento(establecimientoDTO);
        }

        // Convertir Productor a ProductorResponse
        if (evento.getProductor() != null) {
            ProductorResponse productorDTO = new ProductorResponse();
            productorDTO.setIdProductor(evento.getProductor().getIdUsuario());
            productorDTO.setNombre(evento.getProductor().getNombre());
            productorDTO.setApellidos(evento.getProductor().getApellidos());
            productorDTO.setCorreo(evento.getProductor().getCorreo());
            productorDTO.setTelefono(evento.getProductor().getTelefono());
            productorDTO.setNombreUsuario(evento.getProductor().getNombreUser());
            productorDTO.setEstado(evento.getProductor().getEstado());
            productorDTO.setActivo(evento.getProductor().getActivo());
            productorDTO.setRazonSocial(evento.getProductor().getRazonSocial());
            productorDTO.setDireccionFisica(evento.getProductor().getDireccionFisica());
            productorDTO.setTipoEstadoProductor(evento.getProductor().getTipoEstadoProductor());
            productorDTO.setLocalesRegistrados(evento.getProductor().getLocalesRegistrados());
            productorDTO.setEventosRegistrados(evento.getProductor().getEventosRegistrados());
            productorDTO.setPromocionesCreadas(evento.getProductor().getPromocionesCreadas());
            productorDTO.setFechaVerificacion(evento.getProductor().getFechaVerificacion());
            productorDTO.setDNI(evento.getProductor().getDNI());
            productorDTO.setRUC(evento.getProductor().getRUC());

            // Convertir Gestor del Productor
            GestorResponse gestorDTO = null;
            if (evento.getProductor().getGestor() != null) {
                gestorDTO = new GestorResponse();
                gestorDTO.setIdUsuario(evento.getProductor().getGestor().getIdUsuario());
                gestorDTO.setNombre(evento.getProductor().getGestor().getNombre());
                gestorDTO.setApellidos(evento.getProductor().getGestor().getApellidos());
                gestorDTO.setCorreo(evento.getProductor().getGestor().getCorreo());
                gestorDTO.setTelefono(evento.getProductor().getGestor().getTelefono());
                gestorDTO.setNombreUsuario(evento.getProductor().getGestor().getNombreUser());
                gestorDTO.setDni(evento.getProductor().getGestor().getDNI());
                gestorDTO.setEstado(evento.getProductor().getGestor().getEstado());
                gestorDTO.setActivo(evento.getProductor().getGestor().getActivo());
                gestorDTO.setAreaGestion(evento.getProductor().getGestor().getTipoArea());
            }

            productorDTO.setGestor(gestorDTO);
            dto.setProductor(productorDTO);
        }

        // Convertir Gestor a GestorResponse
        if (evento.getGestor() != null) {
            GestorResponse gestorDTO = new GestorResponse();
            gestorDTO.setIdUsuario(evento.getGestor().getIdUsuario());
            gestorDTO.setNombre(evento.getGestor().getNombre());
            gestorDTO.setApellidos(evento.getGestor().getApellidos());
            gestorDTO.setCorreo(evento.getGestor().getCorreo());
            gestorDTO.setTelefono(evento.getGestor().getTelefono());
            gestorDTO.setNombreUsuario(evento.getGestor().getNombreUser());
            gestorDTO.setDni(evento.getGestor().getDNI());
            gestorDTO.setEstado(evento.getGestor().getEstado());
            gestorDTO.setActivo(evento.getGestor().getActivo());
            gestorDTO.setAreaGestion(evento.getGestor().getTipoArea());
            dto.setGestor(gestorDTO);
        }

        return dto;
    }
    public List<ReporteEventoDetalle> generarReporteDetalladoPorFechaEvento(ReporteRequestEvento requestDTO) {
        LocalDate inicio = requestDTO.getFechaInicio();
        LocalDate fin = requestDTO.getFechaFin();
        Integer idProductor = requestDTO.getIdProductor();
        // Llama a la nueva consulta del repositorio
        return eventoRepository.generarReporteDetalladoPorFechaEvento(inicio, fin,idProductor);
    }

    public byte[] obtenerBannerPorId(Integer idEvento) {
        // Ya no usamos findById, usamos la query específica
        return eventoRepository.findBannerByIdNative(idEvento);
    }

}