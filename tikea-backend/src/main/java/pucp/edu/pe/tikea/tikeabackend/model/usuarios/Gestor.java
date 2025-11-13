package pucp.edu.pe.tikea.tikeabackend.model.usuarios;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Table(name = "Gestor")
@Data
@AllArgsConstructor
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "idGestor")
@Getter
@Setter
@DynamicInsert
public class Gestor extends Usuario {

    @Enumerated(EnumType.STRING)
    @Column(name = "areaGestion")
    private TipoArea tipoArea;
}
