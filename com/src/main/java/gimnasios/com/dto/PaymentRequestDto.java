package gimnasios.com.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Setter
@Getter
public class PaymentRequestDto {
    private Double total;
    private String paymentConcept;
    private Long afiliadoId;
}
