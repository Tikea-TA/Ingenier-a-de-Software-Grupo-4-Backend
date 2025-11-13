package pucp.edu.pe.tikea.tikeabackend.repository.cliente;

import org.springframework.data.jpa.repository.JpaRepository;
import pucp.edu.pe.tikea.tikeabackend.model.usuarios.Cliente;
import pucp.edu.pe.tikea.tikeabackend.model.usuarios.TipoCliente;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente,Integer> {
    Optional<Cliente> findByCorreoIgnoreCase(String correo);

    // Buscar cliente por nombre de usuario
    Optional<Cliente> findByNombreUserIgnoreCase(String nombreUser);

    // Buscar cliente por DNI
    Optional<Cliente> findByDNI(String DNI);

    // Buscar Clientes por tipo de cliente
    List<Cliente> findByTipoCliente(TipoCliente tipoCliente);

    // Buscar Clientes activos accediendo al campo activo de Usuario
    List<Cliente> findByActivo(Integer activo);

    List<Cliente> findByTipoClienteAndActivo(TipoCliente tipoCliente, Integer activo);
}
