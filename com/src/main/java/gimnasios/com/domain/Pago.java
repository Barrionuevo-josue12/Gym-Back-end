package gimnasios.com.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Builder
@Setter @Getter
@AllArgsConstructor
public class Pago {
    private Long idPago;
    private Double importePago;
    private String conceptoPago;

    @ManyToOne (cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn (name = "afiliado_id")
    private Afiliado afiliado;
}
