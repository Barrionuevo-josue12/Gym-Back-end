package gimnasios.com.util;

import gimnasios.com.dto.PaymentRequestDto;
import gimnasios.com.exception.ReglaDeNegocioException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UtilPayment {
    public static void checkPaymentRequestDtoBeforeToCreate(PaymentRequestDto dto){
        if(!GimnasioUtil.stringOk(dto.getPaymentConcept())){
            log.error("Payment concept inserted into payment is incorrect {}",dto);
            throw new ReglaDeNegocioException("Payment concept inserted is incorrect. "+dto);
        }
        if(dto.getTotal() == null  || dto.getTotal() <= 0){
            log.error("Total into payment is incorrect: {}",dto);
            throw new ReglaDeNegocioException("Total payment is incorrect: "+dto);
        }
        if(dto.getAfiliadoId() == null){
            log.error("affiliate id is null {}",dto);
            throw new ReglaDeNegocioException("The payment has a null affiliate id: "+dto);
        }
    }
    public static void checkPaymentRequestDtoBeforeToUpdate (PaymentRequestDto dto){
        if(allAtributtesAreNull(dto)) {
            log.error("The payment request is completely incorrect {}", dto);
            throw new ReglaDeNegocioException("The payment request has all its atributtes as null");
        }
    }
    private static boolean allAtributtesAreNull(PaymentRequestDto dto){
        return (dto.getAfiliadoId() == null && dto.getPaymentConcept() == null
        && dto.getTotal() == null);
    }
}
