package gimnasios.com.service;

import gimnasios.com.dto.PaymentDto;
import gimnasios.com.dto.PaymentRequestDto;

import java.util.List;

public interface PaymentService {

    PaymentDto createPayment(PaymentRequestDto dto);
    PaymentDto updatePayment(Long id,PaymentRequestDto dto);
    PaymentDto findPaymentById(Long id);
    List<PaymentDto> findPaymentByAffiliateId(Long id);
    void deletePaymentById(Long id);
}
