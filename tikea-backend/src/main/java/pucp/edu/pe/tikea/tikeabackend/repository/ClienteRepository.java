package pucp.edu.pe.tikea.tikeabackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import pucp.edu.pe.tikea.tikeabackend.model.Cliente;
import pucp.edu.pe.tikea.tikeabackend.model.TipoCliente;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente,Integer> {
    Optional<Cliente> findByCorreoIgnoreCase(String correo);

    // Buscar Cliente por nombre de usuario
    Optional<Cliente> findByNombreUserIgnoreCase(String nombreUser);

    // Buscar Cliente por DNI
    Optional<Cliente> findByDNI(String DNI);

    // Buscar Clientes por tipo de cliente
    List<Cliente> findByTipoCliente(TipoCliente tipoCliente);

    // Buscar Clientes activos
    List<Cliente> findByActivo(Integer activo);

    // Buscar Clientes por tipo y activos
    List<Cliente> findByTipoClienteAndActivo(TipoCliente tipoCliente, Integer activo);
}
