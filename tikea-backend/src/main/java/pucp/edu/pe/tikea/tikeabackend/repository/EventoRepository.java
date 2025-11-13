package pucp.edu.pe.tikea.tikeabackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pucp.edu.pe.tikea.tikeabackend.model.infraestructura.Evento;

public interface EventoRepository extends JpaRepository<Evento, Integer> {
    
}
