package gimnasios.com.mapper;

import gimnasios.com.domain.Payment;
import gimnasios.com.dto.PaymentDto;
import gimnasios.com.dto.PaymentRequestDto;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {
    public Payment toPagoEntity(PaymentRequestDto dto){
        return Payment.builder().importePago(dto.getTotal())
                .conceptoPago(dto.getPaymentConcept())
                .build();
    }
    public PaymentDto toPagoDto (Payment entidad){
        return PaymentDto.builder().paymentId(entidad.getIdPago())
                .paymentConcept(entidad.getConceptoPago())
                .total(entidad.getImportePago())
                .afiliadoId(entidad.getAfiliado().getAfiliadoId())
                .build();
    }
    public Payment toPaymentEntitySincePaymentrequestDto(Payment entity,PaymentRequestDto dto){
        if(dto.getTotal() != null){entity.setImportePago(dto.getTotal());}
        if(dto.getPaymentConcept() != null && !dto.getPaymentConcept().isBlank()){entity.setConceptoPago(dto.getPaymentConcept());}
        return entity;
    }
}
