package gimnasios.com.service.imp;

import gimnasios.com.mapper.PaymentMapper;
import gimnasios.com.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class PaymentServiceImpTest {
    @Mock
    PaymentRepository paymentRepository;
    @Mock
    PaymentMapper paymentMapper;
    @Mock
    Afiliado


    @BeforeEach
    void setUp() {
    }

    @Test
    void createPayment() {
    }

    @Test
    void updatePayment() {
    }

    @Test
    void findPaymentById() {
    }

    @Test
    void findPaymentByAffiliateId() {
    }

    @Test
    void deletePaymentById() {
    }
}