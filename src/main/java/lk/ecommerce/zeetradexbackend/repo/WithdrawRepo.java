package lk.ecommerce.zeetradexbackend.repo;

import lk.ecommerce.zeetradexbackend.entity.Withdrawal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WithdrawRepo extends JpaRepository<Withdrawal, Long> {
    //get the withdrawal history
    List<Withdrawal>findByUserId(Long userId);
}
