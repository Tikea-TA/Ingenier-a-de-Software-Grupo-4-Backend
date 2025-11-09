package pucp.edu.pe.tikea.tikeabackend.services;

import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pucp.edu.pe.tikea.tikeabackend.model.Cliente;
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
    public List<Cliente> listar() {
        return clienteRepository.findAll().stream().map(this::toDTO).toList();
    }
    // agregar usuario
    ublic ClienteResponseDTO registrar( dto) {
        Cliente c = new Cliente();
        c.setNombre(dto.nombre);
        c.setApellido(dto.apellido);
        c.setCorreo(dto.correo);
        c.setTelefono(dto.telefono);
        c.setNombreUser(dto.nombreUser);
        c.setDni(dto.dni);

        c.setPasswordHash(encoder.encode(dto.password)); // HASH
        c.setEstado(TipoEstado.ACTIVO);
        c.setFechaRegistro(LocalDateTime.now());

        c.setDireccion(dto.direccion);
        c.setPuntosPromociones(dto.puntosPromociones == null ? 0 : dto.puntosPromociones);
        c.setTipoCliente(TipoCliente.valueOf(dto.tipoCliente.toUpperCase()));

        Cliente saved = repo.save(c); // inserta en usuario + cliente (misma PK)
        return toDTO(saved);
    }

    // login
    public Cliente login(String Correo, String Password) {
        Cliente c = clienteRepository.findByCorreoIgnoreCase(Correo)
                .orElseThrow(() -> new IllegalArgumentException("Credenciales inválidas"));
        if(!encoder.matches(Password, c.getPassword())) {
                throw  new IllegalArgumentException("Password incorrecto");
            }
            c.setFechaUltimoAcceso(LocalDateTime.now());
            return clienteRepository.save(c);
    }



}
