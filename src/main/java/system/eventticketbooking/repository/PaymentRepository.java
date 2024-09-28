package system.eventticketbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import system.eventticketbooking.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
