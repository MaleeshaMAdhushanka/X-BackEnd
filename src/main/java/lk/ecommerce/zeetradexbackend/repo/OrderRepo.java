package lk.ecommerce.zeetradexbackend.repo;

import lk.ecommerce.zeetradexbackend.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepo extends JpaRepository<Order, Long> {

   List<Order>  findByUserId(Long userId);


}
