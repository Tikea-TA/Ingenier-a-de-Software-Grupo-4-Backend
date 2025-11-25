package pucp.edu.pe.tikea.tikeabackend.repository.venta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pucp.edu.pe.tikea.tikeabackend.model.venta.ComprobanteDePago;
import pucp.edu.pe.tikea.tikeabackend.model.venta.Transaccion;
import pucp.edu.pe.tikea.tikeabackend.model.venta.TipoComprobante;
import pucp.edu.pe.tikea.tikeabackend.model.venta.TipoTransferencia;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ComprobanteDePagoRepository extends JpaRepository<ComprobanteDePago, Integer>{

    // Buscar por número de comprobante
    Optional<ComprobanteDePago> findByNumeroComprobante(String numeroComprobante);

    // Buscar por transacción
    Optional<ComprobanteDePago> findByTransaccion(Transaccion transaccion);

    // Buscar por tipo de comprobante
    List<ComprobanteDePago> findByTipoComprobante(TipoComprobante tipoComprobante);

    // Buscar por estado
    List<ComprobanteDePago> findByEstado(TipoTransferencia estado);

    // Buscar comprobantes activos
    List<ComprobanteDePago> findByActivo(Integer activo);

    // Buscar por fecha de emisión en un rango
    List<ComprobanteDePago> findByFechaEmisionBetween(LocalDateTime inicio, LocalDateTime fin);

    // Buscar por validación SUNAT exacta
    List<ComprobanteDePago> findByValidacionSunat(String validacionSunat);

    // Buscar por serie
    List<ComprobanteDePago> findBySerie(String serie);

    // Buscar por monto total mayor o igual
    List<ComprobanteDePago> findByMontoTotalGreaterThanEqual(Double montoTotal);

    // Buscar por tipo y estado
    List<ComprobanteDePago> findByTipoComprobanteAndEstado(TipoComprobante tipoComprobante, TipoTransferencia estado);

    // Consulta custom para traer comprobante + transacción
    @Query(""" 
           SELECT c FROM ComprobanteDePago c 
           JOIN FETCH c.transaccion 
           WHERE c.idComprobante = :id 
           """)
    Optional<ComprobanteDePago> findByIdWithTransaccion(@Param("id") Integer id);
}
