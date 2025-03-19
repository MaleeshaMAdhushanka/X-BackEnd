package lk.ecommerce.zeetradexbackend.controller;

import lk.ecommerce.zeetradexbackend.entity.User;
import lk.ecommerce.zeetradexbackend.entity.Wallet;
import lk.ecommerce.zeetradexbackend.entity.WalletTransaction;
import lk.ecommerce.zeetradexbackend.entity.Withdrawal;
import lk.ecommerce.zeetradexbackend.service.UserService;
import lk.ecommerce.zeetradexbackend.service.WalletService;
import lk.ecommerce.zeetradexbackend.service.WithdrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WithdrawController {

    @Autowired
    private WithdrawService withdrawService;

    @Autowired
    private WalletService walletService;

    @Autowired
    private UserService userService;

//    @Autowired
//    private WalletTransactionService walletTransactionService;

    @PostMapping("/api/withdrawal/{amount}")
    public ResponseEntity<?> withdrawalRequest(
            @PathVariable Long amount,
            @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserProfileByJwt(jwt);
        Wallet userwallet = walletService.getUserWallet(user);

        Withdrawal withdrawal = withdrawService.requestWithdraw(amount, user);

        walletService.addBalance(userwallet, -withdrawal.getAmount());



        return new ResponseEntity<>(withdrawal, HttpStatus.OK);

    }

    @PatchMapping("/api/admin/withdrawal/{id}/proceed/{accept}")
    public ResponseEntity<?> proceedWithdrawal(
            @PathVariable Long id,
            @PathVariable boolean accept,
            @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserProfileByJwt(jwt);


        Withdrawal withdrawal = withdrawService.proceedWithdraw(id, accept);

        Wallet userWallet = walletService.getUserWallet(user);

        //not accept  decline wee need to give return amount to user wallet
        if (!accept) {
            walletService.addBalance(userWallet, withdrawal.getAmount());

        }

        return new ResponseEntity<>(withdrawal, HttpStatus.OK);

    }


    @GetMapping("/api/withdrawal")
    public ResponseEntity<List<Withdrawal>> getWithdrawalHistory(
            @RequestHeader("Authorization") String jwt) throws Exception {

       User user = userService.findUserProfileByJwt(jwt);

      List<Withdrawal> withdrawal = withdrawService.getUserWithdrawHistory(user);

      return  new ResponseEntity<>(withdrawal, HttpStatus.OK);

    }

    @GetMapping("/api/admin/withdrawal")
    public ResponseEntity<List<Withdrawal>> getWithdrawalRequest(
            @RequestHeader("Authorization") String jwt) throws Exception {

       User user =  userService.findUserProfileByJwt(jwt);

    List<Withdrawal> withdrawal =  withdrawService.getAllWithdrawRequest();

    return new ResponseEntity<>(withdrawal, HttpStatus.OK);


    }














}
