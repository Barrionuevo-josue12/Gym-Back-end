package gimnasios.com.service.imp;

import gimnasios.com.domain.Afiliado;
import gimnasios.com.domain.AfiliadoCorporativo;
import gimnasios.com.domain.Payment;
import gimnasios.com.dto.PaymentDto;
import gimnasios.com.dto.PaymentRequestDto;
import gimnasios.com.mapper.PaymentMapper;
import gimnasios.com.repository.AfiliadoRepository;
import gimnasios.com.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImpTest {
    @Mock
    PaymentRepository paymentRepository;
    @Mock
    PaymentMapper paymentMapper;
    @Mock
    AfiliadoServiceImp afiliadoServiceImp;
    @InjectMocks
    PaymentServiceImp paymentServiceImp;

    private PaymentRequestDto paymentRequestDto;
    private AfiliadoCorporativo affiliate;
    private PaymentDto paymentDto;
    private Payment paymentEntity;

    @BeforeEach
    void setUp() {
        affiliate = AfiliadoCorporativo.builder()
                .dni("4545")
                .email("pepe@gmail.com")
                .nombreCompleto("pepe salas")
                .afiliadoId(21L)
                .fechaNacimiento(LocalDate.now())
                .cuit("2335")
                .nombreEmpresa("Mercado Libre")
                .build();
        paymentRequestDto = PaymentRequestDto.builder()
                .afiliadoId(21L)
                .total(2390D)
                .paymentConcept("Compra de Avena").build();
        paymentDto = PaymentDto.builder()
                .paymentId(1L)
                .total(2390D)
                .paymentConcept("Compra de Avena")
                .afiliadoId(21L).build();
        paymentEntity = Payment.builder()
                .afiliado(affiliate)
                .idPago(1L)
                .importePago(2390D)
                .conceptoPago("Compra de Avena")
                .build();
    }
    /*
    public PaymentDto createPayment(PaymentRequestDto dto) {
        log.info("Someone is trying to create a new payment");
        UtilPayment.checkPaymentRequestDtoBeforeToCreate(dto); //make this and build tests

        Afiliado afi = afiliadoServiceImp.getAffiliateEntityById(dto.getAfiliadoId());
        Payment paymentEntity = paymentMapper.toPagoEntity(dto);
        paymentEntity.setAfiliado(afi);

        return paymentMapper.toPagoDto(paymentRepository.save(paymentEntity));
    }
     */

    @Test
    @DisplayName("Create a Payment")
    void createPayment() {
        //given
        Long affiliateId = 21L;
        when(afiliadoServiceImp.getAffiliateEntityById(affiliateId)).thenReturn(affiliate);
        when(paymentMapper.toPagoEntity(any(PaymentRequestDto.class))).thenReturn(paymentEntity);
        when(paymentRepository.save(paymentEntity)).thenReturn(paymentEntity);
        when(paymentMapper.toPagoDto(any(Payment.class))).thenReturn(paymentDto);

        //when
        PaymentDto dto = paymentServiceImp.createPayment(paymentRequestDto);

        //then
        assertThat(dto.getPaymentId()).isNotNull();
        assertThat(dto.getPaymentConcept()).isEqualTo(paymentRequestDto.getPaymentConcept());
        assertThat(dto.getTotal()).isEqualTo(paymentRequestDto.getTotal());

        verify(paymentMapper).toPagoEntity(any(PaymentRequestDto.class));
        verify(paymentRepository).save(any(Payment.class));
        verify(paymentMapper).toPagoDto(any(Payment.class));
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