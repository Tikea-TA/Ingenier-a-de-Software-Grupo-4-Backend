package pucp.edu.pe.tikea.tikeabackend.services.venta;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pucp.edu.pe.tikea.tikeabackend.DTO.venta.ReservaRequestDTO;
import pucp.edu.pe.tikea.tikeabackend.DTO.venta.ReservaResponse;
import pucp.edu.pe.tikea.tikeabackend.model.usuarios.Cliente;
import pucp.edu.pe.tikea.tikeabackend.model.usuarios.Taquillero;
import pucp.edu.pe.tikea.tikeabackend.model.venta.Reserva;
import pucp.edu.pe.tikea.tikeabackend.repository.venta.ReservaRepository;
import pucp.edu.pe.tikea.tikeabackend.repository.usuarios.TaquilleroRepository;
import pucp.edu.pe.tikea.tikeabackend.repository.usuarios.cliente.ClienteRepository;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class ReservaService {
    private final ReservaRepository reservaRepository;
    private final ClienteRepository clienteRepository;
    private final TaquilleroRepository taquilleroRepository;

    public ReservaService(ReservaRepository reservaRepository, ClienteRepository clienteRepository, TaquilleroRepository taquilleroRepository) {
        this.reservaRepository = reservaRepository;
        this.clienteRepository = clienteRepository;
        this.taquilleroRepository = taquilleroRepository;
    }

    public ReservaResponse crearReserva(ReservaRequestDTO dto){
        Cliente cliente = clienteRepository.findById(dto.getClienteId()).
                orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        Taquillero taquillero = null;

        if(dto.getTaquilleroId() != null){
            taquillero= taquilleroRepository.findById(dto.getTaquilleroId()).
                    orElseThrow(() -> new RuntimeException("Taquillero no encontrado"));

        }

        Reserva reserva = new Reserva();
        reserva.setCliente(cliente);
        reserva.setTaquillero(taquillero);
        reserva.setFechaHoraExpiracion(dto.getFehcaHoraExpiracion());
        reserva.setEstado(dto.getEstado());
        reserva.setFechaHoraCreacion(LocalDateTime.now());
        reserva.setActivo(1);

        reserva = reservaRepository.save(reserva);

        return toDTO(reserva);

    }

    public ReservaResponse modificarReserva(Integer idReserva, ReservaRequestDTO dto) {


        Reserva reserva = reservaRepository.findById(idReserva)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));


        if (dto.getClienteId() != null) {
            Cliente cliente = clienteRepository.findById(dto.getClienteId())
                    .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
            reserva.setCliente(cliente);
        }

        if (dto.getTaquilleroId() != null) {
            Taquillero taquillero = taquilleroRepository.findById(dto.getTaquilleroId())
                    .orElseThrow(() -> new RuntimeException("Taquillero no encontrado"));
            reserva.setTaquillero(taquillero);
        }

        if (dto.getFehcaHoraExpiracion() != null) {
            reserva.setFechaHoraExpiracion(dto.getFehcaHoraExpiracion());
        }

        if (dto.getEstado() != null) {
            reserva.setEstado(dto.getEstado());
        }

        reserva = reservaRepository.save(reserva);

        return toDTO(reserva);
    }

    private ReservaResponse toDTO(Reserva reserva){
        ReservaResponse dto = new ReservaResponse();

        dto.setIdReserva(reserva.getIdReserva());
        dto.setIdCliente(reserva.getCliente().getIdUsuario());

        if(reserva.getTaquillero()!=null){
            dto.setIdTaquillero(reserva.getTaquillero().getIdUsuario());
        }
        dto.setFechaHoraCreacion(reserva.getFechaHoraCreacion());
        dto.setFechaHoraExpiracion(reserva.getFechaHoraExpiracion());

        dto.setEstado(reserva.getEstado());
        dto.setActivo(reserva.getActivo());

        return dto;
    }

    public List<ReservaResponse> listarReservasPorCliente(Integer idCliente){
        List<Reserva> reservas = reservaRepository.findByCliente_IdUsuario(idCliente);

        return  reservas.stream().map(this::toDTO).toList();
    }

    public List<ReservaResponse> listarReservasActivas() {
        List<Reserva> reservas = reservaRepository.findByActivo(1);

        return reservas.stream()
                .map(this::toDTO)
                .toList();
    }

    public List<ReservaResponse> listarTodas() {
        List<Reserva> reservas = reservaRepository.findAll();

        return reservas.stream()
                .map(this::toDTO)
                .toList();
    }

    public List<ReservaResponse> listarPorTaquillero(Integer idTaquillero) {
        List<Reserva> reservas = reservaRepository.findByTaquillero_IdUsuario(idTaquillero);

        return reservas.stream()
                .map(this::toDTO)
                .toList();
    }



}
