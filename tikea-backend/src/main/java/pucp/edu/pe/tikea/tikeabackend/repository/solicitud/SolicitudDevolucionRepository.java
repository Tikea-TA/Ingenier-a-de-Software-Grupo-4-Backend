package pucp.edu.pe.tikea.tikeabackend.repository.solicitud;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pucp.edu.pe.tikea.tikeabackend.model.solicitud.SolicitudDevolucion;
import pucp.edu.pe.tikea.tikeabackend.model.usuarios.Cliente;
import pucp.edu.pe.tikea.tikeabackend.model.infraestructura.TicketEspecifico;

import java.util.List;

public interface SolicitudDevolucionRepository extends JpaRepository<SolicitudDevolucion, Integer> {

    // Buscar solicitudes por cliente
    List<SolicitudDevolucion> findByCliente(Cliente cliente);

    // Buscar por ticket específico
    List<SolicitudDevolucion> findByTicketEspecifico(TicketEspecifico ticketEspecifico);

    // Buscar por aplicación de política
    List<SolicitudDevolucion> findByPoliticaDevolucion(Boolean politicaDevolucion);

    // Buscar por reincorporación en stock
    List<SolicitudDevolucion> findByReincorporacionStock(Boolean reincorporacionStock);

    // Solicitudes activas
    @Query("SELECT s FROM SolicitudDevolucion s WHERE s.activo = 1")
    List<SolicitudDevolucion> findAllActive();

    // Solicitudes activas por cliente
    @Query("""
           SELECT s FROM SolicitudDevolucion s 
           WHERE s.activo = 1 AND s.cliente = :cliente
           """)
    List<SolicitudDevolucion> findActiveByCliente(@Param("cliente") Cliente cliente);

    // Solicitudes activas por ticket
    @Query("""
           SELECT s FROM SolicitudDevolucion s 
           WHERE s.activo = 1 AND s.ticketEspecifico = :ticket
           """)
    List<SolicitudDevolucion> findActiveByTicketEspecifico(
            @Param("ticket") TicketEspecifico ticket);
}
