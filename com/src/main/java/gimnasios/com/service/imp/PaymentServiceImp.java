package gimnasios.com.service.imp;

import gimnasios.com.domain.Afiliado;
import gimnasios.com.domain.Payment;
import gimnasios.com.dto.AfiliadoDto;
import gimnasios.com.dto.PaymentDto;
import gimnasios.com.dto.PaymentRequestDto;
import gimnasios.com.exception.RecursoNoEncontradoException;
import gimnasios.com.mapper.PaymentMapper;
import gimnasios.com.repository.AfiliadoRepository;
import gimnasios.com.repository.PaymentRepository;
import gimnasios.com.service.PaymentService;
import gimnasios.com.util.UtilPayment;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PaymentServiceImp implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final AfiliadoServiceImp afiliadoServiceImp;
    private final PaymentMapper paymentMapper;

    //builder pattern design
    public PaymentServiceImp(PaymentRepository paymentRepository, AfiliadoServiceImp afiliadoServiceImp, PaymentMapper paymentMapper){
        this.paymentRepository = paymentRepository;
        this.afiliadoServiceImp = afiliadoServiceImp;
        this.paymentMapper = paymentMapper;
    }

    @Override
    @Transactional
    public PaymentDto createPayment(PaymentRequestDto dto) {
        log.info("Someone is trying to create a new payment");
        UtilPayment.checkPaymentRequestDtoBeforeToCreate(dto); //make this and build tests

        Afiliado afi = afiliadoServiceImp.getAffiliateEntityById(dto.getAfiliadoId());
        Payment paymentEntity = paymentMapper.toPagoEntity(dto);
        paymentEntity.setAfiliado(afi);

        return paymentMapper.toPagoDto(paymentRepository.save(paymentEntity));
    }

    @Override
    @Transactional
    public PaymentDto updatePayment(Long id,PaymentRequestDto dto) {
        log.info("Someone is trying to update a payment");
        Payment paymentEntity = foundPaymentOrThrowsException(id);

        UtilPayment.checkPaymentRequestDtoBeforeToUpdate(dto);

        paymentEntity = paymentMapper.toPaymentEntitySincePaymentrequestDto(paymentEntity,dto);

        //to update a payment affiliate
        if(dto.getAfiliadoId() != null && !dto.getAfiliadoId().equals(paymentEntity.getAfiliado().getAfiliadoId())){
            Afiliado afiEntity = afiliadoServiceImp.getAffiliateEntityById(dto.getAfiliadoId());
            paymentEntity.setAfiliado(afiEntity);
        }

        return paymentMapper.toPagoDto(paymentRepository.save(paymentEntity));
    }

    private Payment foundPaymentOrThrowsException(Long id){
        return paymentRepository.findPaymentWithAfiliadoByIdPago((id))
                .orElseThrow(()->{log.info("Payment with id: {} doesn't exist ",id);
                    return new RecursoNoEncontradoException("Payment with id: "+id+" doesn't exist");});
    }

    @Override()
    @Transactional()
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
