package lk.ecommerce.zeetradexbackend.service;

import lk.ecommerce.zeetradexbackend.entity.PaymentOrder;
import lk.ecommerce.zeetradexbackend.entity.User;
import lk.ecommerce.zeetradexbackend.enums.PaymentMethod;
import lk.ecommerce.zeetradexbackend.response.PaymentResponse;

import javax.swing.border.Border;

public interface PaymentService {

     //create order

    PaymentOrder createOrder(User user, Long amount, PaymentMethod paymentMethod);

    //get payment Order by id
    PaymentOrder getPaymentOrderById(Long id);

    //create register payment link

    Boolean ProccedPaymentOrder(PaymentOrder paymentOrder, String paymentId);

    //create Razor payment link

    PaymentResponse createRazorPaymentLink(User user, Long amount);

    //create Stripe payment link

    PaymentResponse createStripePaymentLink(User user, Long amount, Long orderId);



}
