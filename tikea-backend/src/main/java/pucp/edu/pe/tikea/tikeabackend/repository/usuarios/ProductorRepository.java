package pucp.edu.pe.tikea.tikeabackend.repository.usuarios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pucp.edu.pe.tikea.tikeabackend.model.usuarios.Productor;
import pucp.edu.pe.tikea.tikeabackend.model.usuarios.Gestor;
import pucp.edu.pe.tikea.tikeabackend.model.usuarios.TipoEstadoProductor;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductorRepository extends JpaRepository<Productor, Integer> {
    // Buscar Productor por ID
    Optional<Productor> findByIdProductor(@Param("idProductor") Integer id);

    // Buscar Productor por RazonSocial
    Optional<Productor> findByRazonSocialIgnoreCase(String razonSocial);

    // Buscar Productor por RUC
    Optional<Productor> findByRUC(String RUC);

    // Buscar Productor por Estado Productor
    List<Productor> findByTipoEstadoProductor(TipoEstadoProductor tipoEstadoProductor);

    // Buscar Productor por nombre de usuario
    Optional<Productor> findByNombreUserIgnoreCase(String nombreUsuario);

    // Buscar Productor por nombre
    Optional<Productor> findByNombreIgnoreCase(String nombre);

    // Buscar Productor por apellidos
    Optional<Productor> findByApellidoIgnoreCase(String apellido);

    // Buscar por DNI
    Optional<Productor> findByDNI(String DNI);

    // Buscar por correo
    Optional<Productor> findByCorreoIgnoreCase(String correo);

    // Buscar todos los Productores de un Gestor especifico
    List<Productor> findByGestor(Gestor gestor);

    // Buscar todos los Productores activos
    List<Productor> findByEstadoAndGestorIsNotNull(String estado);

    // Contar Productores por Gestor
    long countByGestor(Gestor gestor);

    // Buscar Productores registrados de un Gestor específico
    List<Productor> findByTipoEstadoProductorAndGestor(
            TipoEstadoProductor tipoEstadoProductor,
            Gestor gestor
    );
}
