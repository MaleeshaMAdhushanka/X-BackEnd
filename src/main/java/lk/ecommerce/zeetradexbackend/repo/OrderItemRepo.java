package lk.ecommerce.zeetradexbackend.repo;

import lk.ecommerce.zeetradexbackend.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepo  extends JpaRepository<OrderItem, Long> {
}
