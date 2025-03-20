package lk.ecommerce.zeetradexbackend.service;

import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;
import lk.ecommerce.zeetradexbackend.entity.PaymentOrder;
import lk.ecommerce.zeetradexbackend.entity.User;
import lk.ecommerce.zeetradexbackend.enums.PaymentMethod;
import lk.ecommerce.zeetradexbackend.response.PaymentResponse;

public interface PaymentService {

     //create order

    PaymentOrder createOrder(User user, Long amount, PaymentMethod paymentMethod);

    //get payment Order by id
    PaymentOrder getPaymentOrderById(Long id) throws Exception;

    //create register payment link

    Boolean ProccedPaymentOrder(PaymentOrder paymentOrder, String paymentId) throws RazorpayException;

    //create Razor payment link

    PaymentResponse createRazorPaymentLink(User user, Long amount) throws RazorpayException;

    //create Stripe payment link

    PaymentResponse createStripePaymentLink(User user, Long amount, PaymentOrder orderId) throws StripeException;



}
