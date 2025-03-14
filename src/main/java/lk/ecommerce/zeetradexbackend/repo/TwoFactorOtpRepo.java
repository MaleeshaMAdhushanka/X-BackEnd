package lk.ecommerce.zeetradexbackend.repo;

import lk.ecommerce.zeetradexbackend.entity.TwoFactorOTP;
import org.springframework.data.jpa.repository.JpaRepository;

//DAO layer
public interface TwoFactorOtpRepo extends JpaRepository<TwoFactorOTP, Long> {

    TwoFactorOTP findByUserId(Long userId);



}
