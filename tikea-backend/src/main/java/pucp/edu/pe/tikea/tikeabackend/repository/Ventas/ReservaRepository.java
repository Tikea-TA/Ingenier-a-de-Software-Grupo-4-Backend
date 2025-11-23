package pucp.edu.pe.tikea.tikeabackend.repository.Ventas;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pucp.edu.pe.tikea.tikeabackend.model.venta.Reserva;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Integer>{

    List<Reserva> findByCliente_IdCliente(Integer idCliente);
    List<Reserva> findByActivo(Integer activo);
    List<Reserva> findByTaquillero_IdTaquillero(Integer idTaquillero);
}
