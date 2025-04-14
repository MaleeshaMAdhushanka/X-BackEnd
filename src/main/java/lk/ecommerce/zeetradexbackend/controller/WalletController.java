package lk.ecommerce.zeetradexbackend.controller;

import lk.ecommerce.zeetradexbackend.entity.*;
import lk.ecommerce.zeetradexbackend.enums.WalletTransactionType;
import lk.ecommerce.zeetradexbackend.response.PaymentResponse;
import lk.ecommerce.zeetradexbackend.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
public class WalletController {

    @Autowired
    private WalletService walletService;
    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;


    @Autowired
    private PaymentService paymentService;

    @Autowired
    private TransactionService transactionService;



    @GetMapping("/api/wallet")
    public ResponseEntity<Wallet> getUserWallet(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);

        Wallet wallet = walletService.getUserWallet(user);

        return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);

    }

    @PutMapping("/api/wallet/{walletId}/transfer")
    public ResponseEntity<Wallet> walletToWalletTransfer(@RequestHeader("Authorization") String jwt,
                                                         @PathVariable Long walletId,
                                                         @RequestBody WalletTransaction req) throws Exception {
        User senderUser = userService.findUserProfileByJwt(jwt);
        Wallet senderWallet = walletService.getUserWallet(senderUser);
        Wallet receiverWallet = walletService.findWalletById(walletId);
        Wallet wallet = walletService.walletToWalletTransfer(senderUser, senderWallet, receiverWallet, req.getAmount());

        transactionService.createTransaction(wallet,
                WalletTransactionType.WALLET_TRANSACTION,
                receiverWallet.getId(),
                req.getPurpose(),
                req.getAmount());




        return new ResponseEntity<>(wallet, HttpStatus.OK);
    }


    @PutMapping("/api/wallet/order/{orderId}/pay")
    public ResponseEntity<Wallet> payOrderPayment(@RequestHeader("Authorization") String jwt,
                                                         @RequestBody Long orderId
    ) throws Exception {
       //Order API
        User user = userService.findUserProfileByJwt(jwt);

        Order order = orderService.getOrderById(orderId);

        Wallet wallet = walletService.payOrderPayment(order, user);

        return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
    }


    @PutMapping("/api/wallet/deposit")
    public ResponseEntity<Wallet> addBalanceToWallet(@RequestHeader("Authorization") String jwt,
                                                 @RequestParam(name = "order_id") Long orderId,
                                                  @RequestParam(name = "payment_id") String paymentId
    ) throws Exception {
        //Order API
        User user = userService.findUserProfileByJwt(jwt);

        Wallet wallet = walletService.getUserWallet(user);
        PaymentOrder order = paymentService.getPaymentOrderById(orderId);

        Boolean status= paymentService.ProccedPaymentOrder(order, paymentId);

//        PaymentResponse res = new PaymentResponse();
//        res.setPayment_url("deposit success");

        if (status) {
            if (wallet.getBalance() == null){
                wallet.setBalance(BigDecimal.valueOf(0));
            }
            wallet = walletService.addBalance(wallet, order.getAmount());
        }



        return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
    }









}
