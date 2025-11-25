package pucp.edu.pe.tikea.tikeabackend.repository.solicitud;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pucp.edu.pe.tikea.tikeabackend.model.solicitud.SolicitudCambio;
import pucp.edu.pe.tikea.tikeabackend.model.solicitud.TipoOperacionCambio;
import pucp.edu.pe.tikea.tikeabackend.model.usuarios.Cliente;
import pucp.edu.pe.tikea.tikeabackend.model.venta.Reserva;

import java.time.LocalDateTime;
import java.util.List;

public interface SolicitudCambioRepository extends JpaRepository<SolicitudCambio, Integer>{
    // Buscar todas las solicitudes de un cliente
    List<SolicitudCambio> findByCliente(Cliente cliente);

    // Buscar solicitudes por reserva
    List<SolicitudCambio> findByReserva(Reserva reserva);

    // Buscar por tipo de operación (CAMBIO, UPGRADE, etc.)
    List<SolicitudCambio> findByTipoOperacion(TipoOperacionCambio tipoOperacion);

    // Buscar solicitudes donde se validó disponibilidad
    List<SolicitudCambio> findByDisponibilidadValidada(Boolean disponibilidadValidada);

    // Buscar solicitudes activas
    @Query("SELECT s FROM SolicitudCambio s WHERE s.activo = 1")
    List<SolicitudCambio> findAllActive();

    // Buscar solicitudes activas de un cliente
    @Query("SELECT s FROM SolicitudCambio s WHERE s.activo = 1 AND s.cliente = :cliente")
    List<SolicitudCambio> findActiveByCliente(@Param("cliente") Cliente cliente);

    // Buscar solicitudes dentro de un rango de fechas
    List<SolicitudCambio> findByFechaBetween(LocalDateTime inicio, LocalDateTime fin);

    // Buscar activas dentro de un rango de fechas
    @Query("""
           SELECT s FROM SolicitudCambio s 
           WHERE s.activo = 1 
           AND s.fecha BETWEEN :inicio AND :fin
           """)
    List<SolicitudCambio> findActiveByFechaBetween(
            @Param("inicio") LocalDateTime inicio,
            @Param("fin") LocalDateTime fin
    );
}
