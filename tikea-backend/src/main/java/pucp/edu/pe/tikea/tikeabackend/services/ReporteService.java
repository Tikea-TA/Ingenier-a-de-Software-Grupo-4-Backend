package pucp.edu.pe.tikea.tikeabackend.services;
// src/main/java/pucp/edu/pe/tikea/tikeabackend/services/ReporteService.java


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pucp.edu.pe.tikea.tikeabackend.DTO.ReporteEventoDTO;
import pucp.edu.pe.tikea.tikeabackend.DTO.ReporteRequestDTO;

import pucp.edu.pe.tikea.tikeabackend.repository.TicketEspecificoRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class ReporteService {
    
    @Autowired
    private TicketEspecificoRepository ticketEspecificoRepository;
    // -------------------------------------------------


    public List<ReporteEventoDTO> generarReporteEventos(ReporteRequestDTO requestDTO) {

        Timestamp inicio = Timestamp.valueOf(
                LocalDateTime.of(requestDTO.getFechaInicio(), LocalTime.MIDNIGHT)
        );
        Timestamp fin = Timestamp.valueOf(
                LocalDateTime.of(requestDTO.getFechaFin(), LocalTime.MAX)
        );

        // Esta llamada ahora invoca la consulta JPQL actualizada
        List<ReporteEventoDTO> reporte = ticketEspecificoRepository.generarReporteAgrupadoPorEvento(inicio, fin);

        return reporte;
    }
}