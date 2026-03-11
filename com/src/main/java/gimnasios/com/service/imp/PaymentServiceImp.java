package gimnasios.com.service.imp;

import gimnasios.com.domain.Afiliado;
import gimnasios.com.domain.Payment;
import gimnasios.com.dto.PaymentDto;
import gimnasios.com.dto.PaymentRequestDto;
import gimnasios.com.exception.RecursoNoEncontradoException;
import gimnasios.com.mapper.PaymentMapper;
import gimnasios.com.repository.AfiliadoRepository;
import gimnasios.com.repository.PaymentRepository;
import gimnasios.com.service.PaymentService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PaymentServiceImp implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final AfiliadoRepository afiliadoRepository;
    private final PaymentMapper paymentMapper;

    //builder pattern design
    public PaymentServiceImp(PaymentRepository paymentRepository, AfiliadoRepository afiliadoRepository, PaymentMapper paymentMapper){
        this.paymentRepository = paymentRepository;
        this.afiliadoRepository = afiliadoRepository;
        this.paymentMapper = paymentMapper;
    }

    @Override
    @Transactional
    public PaymentDto createPayment(PaymentRequestDto dto) {
        log.info("Someone is trying to create a new payment");
        //UtilPayment.checkPaymentRequestDtoBeforeToCreate(dto)

        Afiliado afi = afiliadoRepository.findById(dto.getAfiliadoId()).orElseThrow(()->{log.error("Someone has tried to create a payment, with a non-exist affiliate id  {}", dto);
            return new RecursoNoEncontradoException("Affiliate with id: " + dto.getAfiliadoId() + " doesn't exist");});

        Payment paymentEntity = paymentMapper.toPagoEntity(dto);
        paymentEntity.setAfiliado(afi);

        return paymentMapper.toPagoDto(paymentRepository.save(paymentEntity));
    }

    @Override
    @Transactional
    public PaymentDto updatePayment(Long id,PaymentRequestDto dto) {
        log.info("Someone is trying to update a payment");
        //UtilPayment.checkPaymentRequestDtoBeforeToUpdate(dto);

        Payment paymentEntity = foundPaymentOrThrowsException(id);

        paymentEntity = paymentMapper.toPaymentEntitySincePaymentrequestDto(paymentEntity,dto);

        return paymentMapper.toPagoDto(paymentRepository.save(paymentEntity));
    }

    private Payment foundPaymentOrThrowsException(Long id){
        Payment paymentEntity = paymentRepository.findById(id)
                .orElseThrow(()->{log.info("Payment with id: {} doesn't exist ",id);
                    return new RecursoNoEncontradoException("Payment with id: "+id+" doesn't exist");});
        return paymentEntity;
    }

    @Override
    @Transactional
    public PaymentDto findPaymentById(Long id) {
        Payment paymentEntity = foundPaymentOrThrowsException(id);
        return paymentMapper.toPagoDto(paymentEntity);
    }

    @Override
    public List<PaymentDto> findPaymentByAffiliateId(Long id) {
        log.info("Fetching payments for affiliate with ID: {}",id);

        List <Payment> paymentList = paymentRepository.findPaymentByAfiliado_afiliadoId(id);
        return paymentList.stream()
                .map(paymentMapper::toPagoDto)
                .toList();
    }

    @Override
    @Transactional
    public void deletePaymentById(Long id) {
        log.info("Attempting to delete payment with ID: {}",id);

        if(!paymentRepository.existsById(id)){
            log.error("Payment with id: {} doesn't exist",id);
            throw new RecursoNoEncontradoException("Payment with id: "+id+" does not exist");
        }
        paymentRepository.deleteById(id);
        log.info("Payment with id {} was deleted successfully",id);
    }
}
