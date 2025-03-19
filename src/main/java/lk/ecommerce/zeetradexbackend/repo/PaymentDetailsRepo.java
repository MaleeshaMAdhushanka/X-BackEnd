package lk.ecommerce.zeetradexbackend.repo;

import lk.ecommerce.zeetradexbackend.entity.PaymentDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentDetailsRepo extends JpaRepository<PaymentDetails, Long> {

    PaymentDetails findByUserId(Long userId);

}
