package gimnasios.com.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@AllArgsConstructor
@Table(name = "sucursales")
@Entity
@NoArgsConstructor
public class Sucursal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long idSucursal;
    
    @Column(name = "horario_apertura")
    private LocalDateTime horarioApertura;
    
    @Column (name = "nombre_sede")
    private String nombreSede;
}
