package lk.ecommerce.zeetradexbackend.service;

import lk.ecommerce.zeetradexbackend.entity.Coin;
import lk.ecommerce.zeetradexbackend.entity.Order;
import lk.ecommerce.zeetradexbackend.entity.OrderItem;
import lk.ecommerce.zeetradexbackend.entity.User;
import lk.ecommerce.zeetradexbackend.enums.OrderType;

import java.util.List;

public interface OrderService {

    Order createOrder(User user, OrderItem orderItem, OrderType orderType);

    Order getOrderById(Long orderId) throws Exception;

    List<Order> getAllOrdersOfUser(Long userId, OrderType orderType, String assetSymbol);

    Order processOrder(Coin coin, double quantity, OrderType orderType, User user) throws Exception;
}
