package lk.ecommerce.zeetradexbackend.service.impl;

import com.razorpay.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lk.ecommerce.zeetradexbackend.entity.PaymentOrder;
import lk.ecommerce.zeetradexbackend.entity.User;
import lk.ecommerce.zeetradexbackend.enums.PaymentMethod;
import lk.ecommerce.zeetradexbackend.enums.PaymentOrderStatus;
import lk.ecommerce.zeetradexbackend.repo.PaymentOrderRepo;
import lk.ecommerce.zeetradexbackend.response.PaymentResponse;
import lk.ecommerce.zeetradexbackend.service.PaymentService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentOrderRepo paymentOrderRepo;

    //ApI keys
    @Value("${stipe.api.key}")
    private String stripeSecretKey;


    @Value("${razorpay.api.key}")
    private String apikey;


    @Value("${razorpay.api.secret}")
    private String apiSecretKey;


    @Override
    public PaymentOrder createOrder(User user, Long amount, PaymentMethod paymentMethod) {
        PaymentOrder paymentOrder = new PaymentOrder();
        paymentOrder.setId(user.getId());
        paymentOrder.setAmount(amount);
        paymentOrder.setPaymentMethod(paymentMethod);

        return paymentOrderRepo.save(paymentOrder);
    }

    @Override
    public PaymentOrder getPaymentOrderById(Long id) throws Exception {
        return paymentOrderRepo.findById(id).orElseThrow(()->new Exception("Payment Order Not Found"));
    }

    @Override
    public Boolean ProccedPaymentOrder(PaymentOrder paymentOrder, String paymentId) throws RazorpayException {
        if (paymentOrder.getStatus().equals(PaymentOrderStatus.PENDING)) {
            if (paymentOrder.getPaymentMethod().equals(PaymentMethod.RAZORPAY)) {
                RazorpayClient razorpay = new RazorpayClient(apikey, apiSecretKey);
                Payment payment = razorpay.payments.fetch(paymentId);

                Integer amount = payment.get("amount");
                String status = payment.get("status");

                if (status.equals("captured")) {

                    paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);
                    return true;
                }
                paymentOrder.setStatus(PaymentOrderStatus.FAILED);

                paymentOrderRepo.save(paymentOrder);

                return false;

            }
            paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);
            paymentOrderRepo.save(paymentOrder);
            return true;
        }
        return false;
    }

    @Override
    public PaymentResponse createRazorPaymentLink(User user, Long amount) throws RazorpayException {

        Long Amount = amount * 100;

        try {
            //create razorpay client with key id and secret
            RazorpayClient razorpay = new RazorpayClient(apikey, apiSecretKey);

            //JOSN object ekak heduwa payment link req parameters
            JSONObject paymentLinkRequest = new JSONObject();
            paymentLinkRequest.put("amount", Amount);
            paymentLinkRequest.put("currency", "INR");

            //Create a JSON object with the customer details
            JSONObject customer = new JSONObject();
            customer.put("name", user.getFullName());

            customer.put("email", user.getEmail());
            paymentLinkRequest.put("customer", customer);

            //Create a josn object with the notification settings
            JSONObject notify = new JSONObject();
            notify.put("email",true);
            paymentLinkRequest.put("notify",notify);

            //set The reminder settings
            paymentLinkRequest.put("reminder_enable",true);

            //set the Callback URL  and method
            paymentLinkRequest.put("callback_url","https://localhost:8080/wallet");
            paymentLinkRequest.put("callback_method","get");

            //Create the payment link using the payment.create() method
           PaymentLink payment = razorpay.paymentLink.create(paymentLinkRequest);

           //response to user
           String paymentLink = payment.get("id");
           String paymentLinkUrl = payment.get("short_url");

           PaymentResponse res = new PaymentResponse();
           res.setPayment_url(paymentLinkUrl);

           return res;




        } catch (RazorpayException e) {
            System.out.println("Error in creating payment link" + e.getMessage());
            throw new RazorpayException(e.getMessage());
        }

    }

    @Override
    public PaymentResponse createStripePaymentLink(User user, Long amount, PaymentOrder orderId) throws StripeException {
        Stripe.apiKey = stripeSecretKey;

        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("https://localhost:8080/wallet?order_id=" +orderId)
                .setCancelUrl("https://localhost:8080/payment/cancel")
                .addLineItem(SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                        .setCurrency("usd")
                                        .setUnitAmount(amount * 100)
                                        .setProductData( SessionCreateParams
                                                .LineItem
                                                .PriceData
                                                .ProductData
                                                .builder()
                                                .setName("Top up wallet")
                                                .build()
                                        ).build()

                        ).build()
                ).build();


        Session session = Session.create(params);
        System.out.println("Session____" + session);

        PaymentResponse res = new PaymentResponse();
        res.setPayment_url(session.getUrl());

        return res;

    }
}
