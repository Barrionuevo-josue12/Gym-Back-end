package gimnasios.com.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter @Getter
@AllArgsConstructor
public class PagoDto {
    private Long idPago;
    private Double importePago;
    private String conceptoPago;
    private Long afiliadoId;
}
