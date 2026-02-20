package gimnasios.com.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Setter @Getter
@Builder
@AllArgsConstructor
@Table (name = "afiliados")
@Entity
@NoArgsConstructor
@Inheritance ( strategy = InheritanceType.JOINED )
public  abstract class Afiliado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long afiliadoId;

    @Column(name = "nombre_completo")
    private String nombreCompleto;

    @Column (name = "email")
    private String email;

    @Column (name = "dni")
    private String dni;

    @Column (name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "sucursal_id") // Define la columna FK en la tabla
    private Sucursal sucursal;
}
