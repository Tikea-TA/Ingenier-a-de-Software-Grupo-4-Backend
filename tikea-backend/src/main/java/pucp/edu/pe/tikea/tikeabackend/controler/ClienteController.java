package pucp.edu.pe.tikea.tikeabackend.controler;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import pucp.edu.pe.tikea.tikeabackend.DTO.ClienteModficiacionRequest;
import pucp.edu.pe.tikea.tikeabackend.DTO.ClienteResponse;
import pucp.edu.pe.tikea.tikeabackend.DTO.LoginRequest;
import pucp.edu.pe.tikea.tikeabackend.DTO.RegistroClienteRequest;
import pucp.edu.pe.tikea.tikeabackend.services.ClienteService;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }
    @GetMapping
    public List<ClienteResponse> listar() {
        return clienteService.listar();
    }

    @PostMapping("/registro")
    public ClienteResponse registrar(@Valid @RequestBody RegistroClienteRequest dto) {
        return clienteService.registrar(dto);
    }

    @PostMapping("/login")
    public ClienteResponse login(@Valid @RequestBody LoginRequest dto) {
        return clienteService.login(dto);
    }
    @PutMapping("/{id}")
    public ClienteResponse actualizar(@PathVariable Integer id,
                                         @RequestBody ClienteModficiacionRequest dto) {
        return clienteService.actualizar(id, dto);
    }
    @DeleteMapping("/{id}")
    public ClienteResponse eliminarLogic(@PathVariable Integer id) {
        return clienteService.inactivar(id);
    }
}
