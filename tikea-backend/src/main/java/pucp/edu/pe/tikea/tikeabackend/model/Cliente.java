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
@PrimaryKeyJoinColumn(name = "idCliente")
@Getter
@Setter
public class Cliente extends Usuario {

    private String direccion;

    @Column(name = "puntosPromocionales")
    private int puntosPromocionales;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipoCliente")
    private TipoCliente tipoCliente;

}