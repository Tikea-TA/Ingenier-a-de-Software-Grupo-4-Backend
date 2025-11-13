package pucp.edu.pe.tikea.tikeabackend.services.Cliente;

import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pucp.edu.pe.tikea.tikeabackend.DTO.usuarios.cliente.ClienteModficiacionRequest;
import pucp.edu.pe.tikea.tikeabackend.DTO.usuarios.cliente.ClienteResponse;
import pucp.edu.pe.tikea.tikeabackend.DTO.usuarios.cliente.LoginRequest;
import pucp.edu.pe.tikea.tikeabackend.DTO.RegistroClienteRequest;
import pucp.edu.pe.tikea.tikeabackend.model.usuarios.Cliente;
import pucp.edu.pe.tikea.tikeabackend.model.usuarios.TipoCliente;
import pucp.edu.pe.tikea.tikeabackend.model.usuarios.TipoEstado;
import pucp.edu.pe.tikea.tikeabackend.repository.cliente.ClienteRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final BCryptPasswordEncoder encoder;


    public ClienteService( ClienteRepository clienteRepository, BCryptPasswordEncoder encoder) {
        this.clienteRepository = clienteRepository;
        this.encoder = encoder;
    }
    @Transactional
    public List<ClienteResponse> listar() {
        return clienteRepository.findAll().stream().map(this::toDTO).toList();
    }
    // agregar usuarios
    public ClienteResponse registrar(RegistroClienteRequest dto) {
        Cliente c = new Cliente();
        c.setNombre(dto.getNombre());
        c.setApellidos(dto.getApellidos());
        c.setCorreo(dto.getCorreo());
        c.setTelefono(dto.getTelefono());
        c.setNombreUser(dto.getNombreUsuario());
        c.setDNI(dto.getDni());
        c.setPassword(encoder.encode(dto.getPassword())); // HASH
        c.setEstado(TipoEstado.ACTIVO);
        c.setFechaRegistro(LocalDateTime.now());
        c.setDireccion(dto.getDireccion());
        c.setPuntosPromocionales(dto.getPuntosPromociones() == null ? 0 : dto.getPuntosPromociones());
        c.setTipoCliente(TipoCliente.valueOf(dto.getTipoCliente().toUpperCase()));

        return toDTO(clienteRepository.save(c));
    }
    // login
    public ClienteResponse login(LoginRequest dto) {
        Cliente c = clienteRepository.findByCorreoIgnoreCase(dto.getCorreo())
                .orElseThrow(() -> new IllegalArgumentException("Credenciales inválidas"));
        if(!encoder.matches(dto.getPassword(), c.getPassword())) {
                throw  new IllegalArgumentException("Password incorrecto");
            }
            c.setFechaUltimoAcceso(LocalDateTime.now());
            return toDTO(clienteRepository.save(c));
    }
    //Modificar
    // service/ClienteService.java
    @Transactional
    public ClienteResponse actualizar(Integer id, ClienteModficiacionRequest dto) {
        Cliente c = clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("cliente no encontrado: " + id));

        // --- Usuario
        if (dto.getCorreo() != null) c.setCorreo(dto.getCorreo());
        if (dto.getTelefono() != null) c.setTelefono(dto.getTelefono());
        if (dto.getNombreUser() != null) c.setNombreUser(dto.getNombreUser());

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            c.setPassword(encoder.encode(dto.getPassword()));
            c.setRequiereCambioPassword(false); // o true, según lógica
        }

        // --- cliente
        if (dto.getDireccion() != null) c.setDireccion(dto.getDireccion());
        if (dto.getPuntosPromocionales() != null) c.setPuntosPromocionales(dto.getPuntosPromocionales());
        if (dto.getTipoCliente() != null) {
            c.setTipoCliente(TipoCliente.valueOf(dto.getTipoCliente().toUpperCase()));
        }

        c.setFechaUltimaModificacion(LocalDateTime.now());

        return toDTO(clienteRepository.save(c));
    }

    //Eliminar cliente
    @Transactional
    public ClienteResponse inactivar(Integer id) {
        Cliente c = clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("cliente no encontrado: " + id));

        c.setEstado(TipoEstado.INACTIVO);
        c.setFechaUltimaModificacion(LocalDateTime.now());

        return toDTO(clienteRepository.save(c));
    }

    private ClienteResponse toDTO(Cliente c) {
        ClienteResponse dto = new ClienteResponse();
        dto.setIdCliente(c.getIdUsuario());
        dto.setNombre(c.getNombre());
        dto.setApellidos(c.getApellidos());
        dto.setCorreo(c.getCorreo());
        dto.setTelefono(c.getTelefono());
        dto.setNombreUsuario(c.getNombreUser());
        dto.setDNI(c.getDNI());
        dto.setEstado(c.getEstado());
        dto.setDireccion(c.getDireccion());
        dto.setPuntosPromociones(c.getPuntosPromocionales());
        dto.setTipoCliente(c.getTipoCliente());
        return dto;
    }
}
