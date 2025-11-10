package pucp.edu.pe.tikea.tikeabackend.services;

import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pucp.edu.pe.tikea.tikeabackend.DTO.ClienteModficiacionRequest;
import pucp.edu.pe.tikea.tikeabackend.DTO.ClienteResponse;
import pucp.edu.pe.tikea.tikeabackend.DTO.LoginRequest;
import pucp.edu.pe.tikea.tikeabackend.DTO.RegistroClienteRequest;
import pucp.edu.pe.tikea.tikeabackend.model.Cliente;
import pucp.edu.pe.tikea.tikeabackend.model.TipoCliente;
import pucp.edu.pe.tikea.tikeabackend.model.TipoEstado;
import pucp.edu.pe.tikea.tikeabackend.repository.ClienteRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
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
        c.setApellido(dto.getApellido());
        c.setCorreo(dto.getCorreo());
        c.setTelefono(dto.getTelefono());
        c.setNombreUser(dto.getNombreUser());
        c.setDNI(dto.getDni());
        c.setPassword(encoder.encode(dto.getPassword())); // HASH
        c.setEstado(TipoEstado.ACTIVO);
        c.setFechaRegistro(LocalDateTime.now());
        c.setDireccion(dto.getDireccion());
        c.setPuntos_promociones(dto.getPuntosPromociones() == null ? 0 : dto.getPuntosPromociones());
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
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado: " + id));

        // --- Usuario
        if (dto.getCorreo() != null) c.setCorreo(dto.getCorreo());
        if (dto.getTelefono() != null) c.setTelefono(dto.getTelefono());
        if (dto.getNombreUser() != null) c.setNombreUser(dto.getNombreUser());

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            c.setPassword(encoder.encode(dto.getPassword()));
            c.setRequiereCambioPassword(false); // o true, según lógica
        }

        // --- Cliente
        if (dto.getDireccion() != null) c.setDireccion(dto.getDireccion());
        if (dto.getPuntosPromociones() != null) c.setPuntos_promociones(dto.getPuntosPromociones());
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
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado: " + id));

        c.setEstado(TipoEstado.INACTIVO);
        c.setFechaUltimaModificacion(LocalDateTime.now());

        return toDTO(clienteRepository.save(c));
    }

    private ClienteResponse toDTO(Cliente c) {
        ClienteResponse dto = new ClienteResponse();
        dto.setIdCliente(c.getIdUsuario());
        dto.setNombre(c.getNombre());
        dto.setDni(c.getApellido());
        dto.setCorreo(c.getCorreo());
        dto.setTelefono(c.getTelefono());
        dto.setNombreUser(c.getNombreUser());
        dto.setDni(c.getDNI());
        dto.setEstado(c.getEstado());
        dto.setDireccion(c.getDireccion());
        dto.setPuntosPromociones(c.getPuntos_promociones());
        dto.setTipoCliente(c.getTipoCliente());
        return dto;
    }
}
