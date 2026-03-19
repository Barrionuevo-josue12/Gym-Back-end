package gimnasios.com.repository;

import gimnasios.com.domain.Afiliado;
import gimnasios.com.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long> {
    public List<Payment> findPaymentByAfiliado_afiliadoId(Long id);
    public Optional <Payment> findPaymentWithAfiliadoByIdPago(Long id);
}
