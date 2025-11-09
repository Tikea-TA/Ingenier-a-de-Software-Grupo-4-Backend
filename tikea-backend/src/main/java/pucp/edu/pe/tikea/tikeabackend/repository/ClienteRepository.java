package pucp.edu.pe.tikea.tikeabackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import pucp.edu.pe.tikea.tikeabackend.model.Cliente;
import pucp.edu.pe.tikea.tikeabackend.model.TipoCliente;
import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente,Integer> {
    List<Cliente> findAllClientes();
    Optional<Cliente> findClienteById(@Param("id") Integer id);
    Optional<Cliente> findByCorreoIgnoreCase(String correo);

    String correo(String correo);
}
