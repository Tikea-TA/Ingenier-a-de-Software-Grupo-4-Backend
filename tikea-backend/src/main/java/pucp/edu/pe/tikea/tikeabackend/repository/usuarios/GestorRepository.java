package pucp.edu.pe.tikea.tikeabackend.repository.usuarios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pucp.edu.pe.tikea.tikeabackend.model.TipoEstado;
import pucp.edu.pe.tikea.tikeabackend.model.usuarios.Gestor;
import pucp.edu.pe.tikea.tikeabackend.model.usuarios.TipoArea;

import java.util.List;
import java.util.Optional;

@Repository
public interface GestorRepository extends JpaRepository<Gestor, Integer> {

    // Buscar un Gestor por correo (case-insensitive)
    Optional<Gestor> findByCorreoIgnoreCase(String correo);

    // Buscar un Gestor por nombre de usuario
    Optional<Gestor> findByNombreUserIgnoreCase(String nombreUser);

    // Buscar un Gestor por DNI
    Optional<Gestor> findByDNI(String DNI);

    // Buscar Gestores por tipo de área (enum)
    List<Gestor> findByTipoArea(TipoArea tipoArea);

    // Buscar Gestores activos
    List<Gestor> findByEstado(TipoEstado estado);

    // Buscar Gestores activos de un área específica
    List<Gestor> findByEstadoAndTipoArea(TipoEstado estado, TipoArea tipoArea);

    // Buscar un Gestor por nombre
    List<Gestor> findByNombreIgnoreCase(String nombre);

    // Contar Gestores por tipo de área
    long countByTipoArea(TipoArea tipoArea);
}
