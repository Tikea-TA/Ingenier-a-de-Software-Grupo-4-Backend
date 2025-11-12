package pucp.edu.pe.tikea.tikeabackend.repository;




import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pucp.edu.pe.tikea.tikeabackend.DTO.ReporteEventoDTO;
import pucp.edu.pe.tikea.tikeabackend.model.TicketEspecifico; // Importamos la entidad correcta

import java.sql.Timestamp;
import java.util.List;


public interface TicketEspecificoRepository extends JpaRepository<TicketEspecifico, Integer> {


    @Query("SELECT new pucp.edu.pe.tikea.tikeabackend.dto.ReporteEventoDTO( " +
            "t.evento.idEvento, " +
            "t.evento.nombreEvento, " +
            "COUNT(t.idTicketEspecifico), " +

            "SUM(t.precioCompra - COALESCE(t.descuentoAplicado, 0.0)) " +
            // ----------------------------------
            ") " +
            "FROM TicketEspecifico t " +
            "WHERE t.fechaEmision BETWEEN :fechaInicio AND :fechaFin " +
            "GROUP BY t.evento.idEvento, t.evento.nombreEvento")
    List<ReporteEventoDTO> generarReporteAgrupadoPorEvento(
            @Param("fechaInicio") Timestamp fechaInicio,
            @Param("fechaFin") Timestamp fechaFin
    );
}