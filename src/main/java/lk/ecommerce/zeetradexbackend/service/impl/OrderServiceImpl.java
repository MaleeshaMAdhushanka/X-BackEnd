package lk.ecommerce.zeetradexbackend.service.impl;

import lk.ecommerce.zeetradexbackend.entity.Coin;
import lk.ecommerce.zeetradexbackend.entity.Order;
import lk.ecommerce.zeetradexbackend.entity.OrderItem;
import lk.ecommerce.zeetradexbackend.entity.User;
import lk.ecommerce.zeetradexbackend.enums.OrderStatus;
import lk.ecommerce.zeetradexbackend.enums.OrderType;
import lk.ecommerce.zeetradexbackend.repo.OrderItemRepo;
import lk.ecommerce.zeetradexbackend.repo.OrderRepo;
import lk.ecommerce.zeetradexbackend.service.OrderService;
import lk.ecommerce.zeetradexbackend.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private WalletService walletService;

    @Autowired
    private OrderItemRepo orderItemRepo;

    @Override
    public Order createOrder(User user, OrderItem orderItem, OrderType orderType) {
      double price = orderItem.getCoin().getCurrentPrice()*orderItem.getQuantity();

      Order order = new Order();
      order.setUser(user);
      order.setOrderType(orderType);
      order.setOrderItem(orderItem);
      order.setPrice(BigDecimal.valueOf(price));
      order.setTimestamp(LocalDateTime.now());
      order.setStatus(OrderStatus.PENDING);


      return orderRepo.save(order);

    }

    @Override
    public Order getOrderById(Long orderId) throws Exception {
        return orderRepo.findById(orderId).orElseThrow(()-> new Exception("Order not found"));

    }

    @Override
    public List<Order> getAllOrdersOfUser(Long userId, OrderType orderType, String assetSymbol) {
        return orderRepo.findByUserId(userId);
    }


    private OrderItem createOrderItem(Coin coin, double quantity, double buyPrice, double sellPrice) {
        OrderItem orderItem = new OrderItem();
        orderItem.setCoin(coin);
        orderItem.setQuantity(quantity);
        orderItem.setBuyPrice(buyPrice);
        orderItem.setSellPrice(sellPrice);
        return orderItem;
    }

    @Override
    public Order processOrder(Coin coin, double quantity, OrderType orderType, User user) {
        return null;
    }
}
