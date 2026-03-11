package gimnasios.com.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter @Getter
@AllArgsConstructor
public class PaymentDto {
    private Long paymentId;
    private Double total;
    private String paymentConcept;
    private Long afiliadoId;
}
