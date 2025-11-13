package pucp.edu.pe.tikea.tikeabackend.model.usuarios;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cliente")
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