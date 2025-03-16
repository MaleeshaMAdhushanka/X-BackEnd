package lk.ecommerce.zeetradexbackend.repo;

import lk.ecommerce.zeetradexbackend.entity.Coin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoinServiceRepo extends JpaRepository<Coin, String> {

}
