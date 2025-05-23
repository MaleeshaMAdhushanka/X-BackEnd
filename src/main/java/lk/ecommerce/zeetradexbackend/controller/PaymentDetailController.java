package lk.ecommerce.zeetradexbackend.controller;

import lk.ecommerce.zeetradexbackend.entity.PaymentDetails;
import lk.ecommerce.zeetradexbackend.entity.User;
import lk.ecommerce.zeetradexbackend.service.PaymentDetailsService;
import lk.ecommerce.zeetradexbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PaymentDetailController {

    @Autowired
    private UserService userService;
    @Autowired
    private PaymentDetailsService paymentDetailsService;


    @PostMapping("/payment-details")
    public ResponseEntity<PaymentDetails> addPaymentDetails(
            @RequestBody PaymentDetails paymentDetailsRequest,
            @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserProfileByJwt(jwt);

        PaymentDetails paymentDetails = paymentDetailsService.addPaymentDetails(
                paymentDetailsRequest.getAccountNumber(),
                paymentDetailsRequest.getAccountHolderName(),
                paymentDetailsRequest.getIfsc(),
                paymentDetailsRequest.getBankName(),
                user
        );
        return new  ResponseEntity<>(paymentDetails,HttpStatus.CREATED);
    }

    @GetMapping("/payment-details")
    public ResponseEntity<PaymentDetails> getUserPaymentDetails(
            @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserProfileByJwt(jwt);

     PaymentDetails paymentDetails =  paymentDetailsService.getUsersPaymentDetails(user);

     return  new ResponseEntity<>(paymentDetails, HttpStatus.CREATED);

    }



}
