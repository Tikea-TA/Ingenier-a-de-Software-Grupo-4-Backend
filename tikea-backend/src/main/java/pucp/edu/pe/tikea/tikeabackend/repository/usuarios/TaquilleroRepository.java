package pucp.edu.pe.tikea.tikeabackend.repository.usuarios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pucp.edu.pe.tikea.tikeabackend.model.usuarios.Taquillero;

@Repository
public interface TaquilleroRepository extends JpaRepository<Taquillero, Integer> {

}
