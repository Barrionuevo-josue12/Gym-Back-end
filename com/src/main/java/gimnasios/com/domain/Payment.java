package gimnasios.com.domain;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Setter @Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table (name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPago;
    private Double importePago;
    private String conceptoPago;

    @ManyToOne (cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn (name = "afiliado_id")
    private Afiliado afiliado;
}
