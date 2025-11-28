package pucp.edu.pe.tikea.tikeabackend.services.venta;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pucp.edu.pe.tikea.tikeabackend.DTO.venta.ComprobanteDePagoRequest;
import pucp.edu.pe.tikea.tikeabackend.DTO.venta.ComprobanteDePagoResponse;
import pucp.edu.pe.tikea.tikeabackend.model.venta.ComprobanteDePago;
import pucp.edu.pe.tikea.tikeabackend.model.venta.TipoComprobante;
import pucp.edu.pe.tikea.tikeabackend.model.venta.TipoTransferencia;
import pucp.edu.pe.tikea.tikeabackend.model.venta.Transaccion;
import pucp.edu.pe.tikea.tikeabackend.repository.venta.ComprobanteDePagoRepository;
import pucp.edu.pe.tikea.tikeabackend.repository.venta.TransaccionRepository; // Asumo este repositorio

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ComprobanteDePagoService {

    private final ComprobanteDePagoRepository repository;
    private final TransaccionRepository transaccionRepository; // Asumido para manejar la relación

    // --- MAPPERS ---
    private ComprobanteDePagoResponse toDTO(ComprobanteDePago comprobante) {
        if (comprobante == null) return null;

        return new ComprobanteDePagoResponse(
                comprobante.getIdComprobante(),
                comprobante.getTransaccion() != null ? comprobante.getTransaccion().getIdTransaccion() : null,
                comprobante.getNumeroComprobante(),
                comprobante.getTipoComprobante() != null ? comprobante.getTipoComprobante().name() : null,
                comprobante.getSerie(),
                comprobante.getFechaEmision(),
                comprobante.getMontoTotal(),
                comprobante.getMontoBruto(),
                comprobante.getDescuentosAplicados(),
                comprobante.getCanjesAplicados(),
                comprobante.getTotalDeReembolsos(),
                comprobante.getIngresosNetoEfectivo(),
                comprobante.getValidacionSunat(),
                comprobante.getEstado() != null ? comprobante.getEstado().name() : null,
                comprobante.getFechaCreacion(),
                comprobante.getActivo()
        );
    }

    private ComprobanteDePago toEntity(ComprobanteDePagoRequest request, Transaccion transaccion) {
        ComprobanteDePago entity = new ComprobanteDePago();

        // Relaciones
        entity.setTransaccion(transaccion);

        // Campos básicos
        entity.setNumeroComprobante(request.getNumeroComprobante());
        entity.setSerie(request.getSerie());
        entity.setFechaEmision(request.getFechaEmision() != null ? request.getFechaEmision() : LocalDateTime.now());
        entity.setMontoTotal(request.getMontoTotal());
        entity.setMontoBruto(request.getMontoBruto());
        entity.setDescuentosAplicados(request.getDescuentosAplicados() != null ? request.getDescuentosAplicados() : 0.0);
        entity.setCanjesAplicados(request.getCanjesAplicados() != null ? request.getCanjesAplicados() : 0.0);
        entity.setTotalDeReembolsos(request.getTotalDeReembolsos() != null ? request.getTotalDeReembolsos() : 0.0);
        entity.setIngresosNetoEfectivo(request.getIngresosNetoEfectivo() != null ? request.getIngresosNetoEfectivo() : 0.0);
        entity.setValidacionSunat(request.getValidacionSunat());

        // Conversión de Strings a Enums
        try {
            if (request.getTipoComprobante() != null) {
                entity.setTipoComprobante(TipoComprobante.valueOf(request.getTipoComprobante().toUpperCase()));
            }
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Tipo de Comprobante inválido: " + request.getTipoComprobante());
        }

        try {
            if (request.getEstado() != null) {
                entity.setEstado(TipoTransferencia.valueOf(request.getEstado().toUpperCase()));
            }
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Estado de Transferencia inválido: " + request.getEstado());
        }

        // Campos de control
        entity.setFechaCreacion(LocalDateTime.now());
        entity.setActivo(1);

        return entity;
    }


    // --- CRUD Y OPERACIONES PRINCIPALES ---
    public ComprobanteDePagoResponse registrar(ComprobanteDePagoRequest request) {

        if (request.getIdTransaccion() == null) {
            throw new IllegalArgumentException("El ID de la Transacción es obligatorio para registrar un Comprobante de Pago.");
        }

        Transaccion transaccion = transaccionRepository.findById(request.getIdTransaccion())
                .orElseThrow(() -> new RuntimeException("Transacción no encontrada con ID: " + request.getIdTransaccion()));

        ComprobanteDePago comprobante = toEntity(request, transaccion);
        return toDTO(repository.save(comprobante));
    }

    public ComprobanteDePagoResponse obtenerPorId(Integer id) {
        return repository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Comprobante de Pago no encontrado con ID: " + id));
    }

    public List<ComprobanteDePagoResponse> listarTodos() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Actualización parcial (maneja nulls en el DTO)
    public ComprobanteDePagoResponse actualizar(Integer id, ComprobanteDePagoRequest request) {
        ComprobanteDePago c = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comprobante de Pago no encontrado con ID: " + id));

        // Actualizar campos si no son nulos en el request
        if (request.getNumeroComprobante() != null) {
            c.setNumeroComprobante(request.getNumeroComprobante());
        }
        if (request.getTipoComprobante() != null) {
            try {
                c.setTipoComprobante(TipoComprobante.valueOf(request.getTipoComprobante().toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Tipo de Comprobante inválido: " + request.getTipoComprobante());
            }
        }
        if (request.getSerie() != null) {
            c.setSerie(request.getSerie());
        }
        if (request.getFechaEmision() != null) {
            c.setFechaEmision(request.getFechaEmision());
        }
        if (request.getMontoTotal() != null) {
            c.setMontoTotal(request.getMontoTotal());
        }
        if (request.getMontoBruto() != null) {
            c.setMontoBruto(request.getMontoBruto());
        }
        if (request.getDescuentosAplicados() != null) {
            c.setDescuentosAplicados(request.getDescuentosAplicados());
        }
        if (request.getCanjesAplicados() != null) {
            c.setCanjesAplicados(request.getCanjesAplicados());
        }
        if (request.getTotalDeReembolsos() != null) {
            c.setTotalDeReembolsos(request.getTotalDeReembolsos());
        }
        if (request.getIngresosNetoEfectivo() != null) {
            c.setIngresosNetoEfectivo(request.getIngresosNetoEfectivo());
        }
        if (request.getValidacionSunat() != null) {
            c.setValidacionSunat(request.getValidacionSunat());
        }
        if (request.getEstado() != null) {
            try {
                c.setEstado(TipoTransferencia.valueOf(request.getEstado().toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Estado de Transferencia inválido: " + request.getEstado());
            }
        }

        // Actualizar Transacción si se proporciona un nuevo ID
        if (request.getIdTransaccion() != null) {
            Transaccion transaccion = transaccionRepository.findById(request.getIdTransaccion())
                    .orElseThrow(() -> new RuntimeException("Transacción no encontrada con ID: " + request.getIdTransaccion()));
            c.setTransaccion(transaccion);
        }

        return toDTO(repository.save(c));
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

    //                CONSULTAS DEL REPOSITORY (Ahora devuelven DTOs y usan IDs/Enums directamente)

    public Optional<ComprobanteDePagoResponse> buscarPorNumero(String numeroComprobante) {
        return repository.findByNumeroComprobante(numeroComprobante)
                .map(this::toDTO);
    }

    public Optional<ComprobanteDePagoResponse> buscarPorTransaccion(Integer idTransaccion) {
        Transaccion transaccion = transaccionRepository.findById(idTransaccion)
                .orElseThrow(() -> new RuntimeException("Transacción no encontrada con ID: " + idTransaccion));

        return repository.findByTransaccion(transaccion)
                .map(this::toDTO);
    }

    public List<ComprobanteDePagoResponse> listarPorTipo(TipoComprobante tipo) {
        return repository.findByTipoComprobante(tipo).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<ComprobanteDePagoResponse> listarPorEstado(TipoTransferencia estado) {
        return repository.findByEstado(estado).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<ComprobanteDePagoResponse> listarActivos() {
        return repository.findByActivo(1).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<ComprobanteDePagoResponse> listarPorRangoFecha(LocalDateTime inicio, LocalDateTime fin) {
        return repository.findByFechaEmisionBetween(inicio, fin).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<ComprobanteDePagoResponse> listarPorValidacionSunat(String validacionSunat) {
        return repository.findByValidacionSunat(validacionSunat).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<ComprobanteDePagoResponse> listarPorSerie(String serie) {
        return repository.findBySerie(serie).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<ComprobanteDePagoResponse> listarMontoMayorIgual(Double monto) {
        return repository.findByMontoTotalGreaterThanEqual(monto).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<ComprobanteDePagoResponse> listarPorTipoYEstado(TipoComprobante tipo, TipoTransferencia estado) {
        return repository.findByTipoComprobanteAndEstado(tipo, estado).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<ComprobanteDePagoResponse> obtenerConTransaccion(Integer id) {
        return repository.findByIdWithTransaccion(id)
                .map(this::toDTO);
    }
}