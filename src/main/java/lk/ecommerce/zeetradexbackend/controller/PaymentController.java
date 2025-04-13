package lk.ecommerce.zeetradexbackend.controller;

import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;
import lk.ecommerce.zeetradexbackend.entity.PaymentOrder;
import lk.ecommerce.zeetradexbackend.entity.User;
import lk.ecommerce.zeetradexbackend.enums.PaymentMethod;
import lk.ecommerce.zeetradexbackend.response.PaymentResponse;
import lk.ecommerce.zeetradexbackend.service.PaymentService;
import lk.ecommerce.zeetradexbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private UserService userService;

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/{paymentMethod}/amount/{amount}")
    public ResponseEntity<PaymentResponse> paymentHandler(
            @PathVariable PaymentMethod paymentMethod,
            @PathVariable Long amount,
            @RequestHeader("Authorization") String jwt) throws Exception, RazorpayException, StripeException {

        User user = userService.findUserProfileByJwt(jwt);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        PaymentOrder order = paymentService.createOrder(user, amount, paymentMethod);
        //defining payment response as null
        PaymentResponse paymentResponse;

        if (paymentMethod.equals(PaymentMethod.RAZORPAY)) {
            paymentResponse = paymentService.createRazorPaymentLink(user, amount);
        } else {
            paymentResponse = paymentService.createStripePaymentLink(user, amount, order);
        }
        return new ResponseEntity<>(paymentResponse, HttpStatus.CREATED);
    }

}
