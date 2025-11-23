package pucp.edu.pe.tikea.tikeabackend.controler.Ventas;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import pucp.edu.pe.tikea.tikeabackend.DTO.Venta.ReservaRequestDTO;
import pucp.edu.pe.tikea.tikeabackend.DTO.Venta.ReservaResponse;
import pucp.edu.pe.tikea.tikeabackend.services.Ventas.ReservaService;

import java.util.List;
@RestController
@RequestMapping("/api/reservas")
public class ReservaController {
    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @PostMapping("/crear")
    public ReservaResponse crearReserva(@Valid @RequestBody ReservaRequestDTO dto) {
        return reservaService.crearReserva(dto);
    }

    @PutMapping("/{idReserva}")
    public ReservaResponse modificarReserva(@PathVariable Integer idReserva, @RequestBody ReservaRequestDTO dto) {
            return reservaService.modificarReserva(idReserva, dto);
    }

    @GetMapping
    public List<ReservaResponse> listarTodas() {
        return reservaService.listarTodas();
    }

    @GetMapping("/activas")
    public List<ReservaResponse> listarActivas() {
        return  reservaService.listarReservasActivas();
    }

    @GetMapping("/cliente/{idCliente}")
    public List<ReservaResponse> listarPorCliente(@PathVariable Integer idCliente) {
        return reservaService.listarReservasPorCliente(idCliente);
    }

    @GetMapping("/taquillero/{idTaquillero}")
    public List<ReservaResponse> listarPorTaquillero(@PathVariable Integer idTaquillero) {
        return reservaService.listarPorTaquillero(idTaquillero);
    }
}
