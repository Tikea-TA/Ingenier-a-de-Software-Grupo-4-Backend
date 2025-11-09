package pucp.edu.pe.tikea.tikeabackend.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "Cliente")
@Data
@AllArgsConstructor
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "id")
@Getter
@Setter
public class Cliente extends Usuario {
    private String direccion;
    private int puntos_promociones;
    @Column(name = "tipo_cliente")
    private TipoCliente tipoCliente;
}