package lk.ecommerce.zeetradexbackend.service.impl;

import lk.ecommerce.zeetradexbackend.entity.*;
import lk.ecommerce.zeetradexbackend.enums.OrderStatus;
import lk.ecommerce.zeetradexbackend.enums.OrderType;
import lk.ecommerce.zeetradexbackend.repo.OrderItemRepo;
import lk.ecommerce.zeetradexbackend.repo.OrderRepo;
import lk.ecommerce.zeetradexbackend.service.OrderService;
import lk.ecommerce.zeetradexbackend.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public Order buyAsset(Coin coin, double quantity, User user) throws Exception {
        if (quantity <= 0) {
            throw  new Exception("quantity should be greater > than 0");
        }
        double buyPrice = coin.getCurrentPrice();

        OrderItem orderItem = createOrderItem(coin, quantity, buyPrice, 0);

        Order order = createOrder(user, orderItem, OrderType.BUY);
        orderItem.setOrder(order);

         walletService.payOrderPayment(order, user);

         order.setStatus(OrderStatus.SUCCESS);
         order.setOrderType(OrderType.BUY);
         Order savedOrder = orderRepo.save(order);

         //Creating asset

        return savedOrder;
    }



    @Transactional
    public Order sellAsset(Coin coin, double quantity, User user) throws Exception {
        if (quantity <= 0) {
            throw  new Exception("quantity should be greater > than 0");
        }
        double sellPrice = coin.getCurrentPrice();

        double buyPrice = assetToSell.getPrice();

        OrderItem orderItem = createOrderItem(coin, quantity, buyPrice, sellPrice);

        Order order = createOrder(user, orderItem, OrderType.SELL);
        orderItem.setOrder(order);

        if (assetToSell.getQuantity()>= quantity) {
            walletService.payOrderPayment(order, user);

            order.setStatus(OrderStatus.SUCCESS);
            order.setOrderType(OrderType.SELL);
            Order savedOrder = orderRepo.save(order);

            Assets updatedAsset = assetService.updateAsset(assetToSell.getId(), -quantity);
            if (updatedAsset.getQuantity()*coin.getCurrentPrice() <=1) {
                assetService.deleteAsset(updatedAsset.getId());
            }
            return savedOrder;
        }
        throw  new Exception("Insufficient quantity to sell");


    }

    @Override
    @Transactional
    public Order processOrder(Coin coin, double quantity, OrderType orderType, User user) throws Exception {

        if (orderType.equals(OrderType.BUY)) {
            return buyAsset(coin, quantity, user);
        }
        else if (orderType.equals(OrderType.SELL)){
            return sellAsset(coin, quantity, user);
        }
        throw  new Exception("Invalid order type");
    }
}
