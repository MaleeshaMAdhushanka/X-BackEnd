package lk.ecommerce.zeetradexbackend.controller;

import lk.ecommerce.zeetradexbackend.entity.User;
import lk.ecommerce.zeetradexbackend.entity.Wallet;
import lk.ecommerce.zeetradexbackend.entity.WalletTransaction;
import lk.ecommerce.zeetradexbackend.service.UserService;
import lk.ecommerce.zeetradexbackend.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {

    @Autowired
    private WalletService walletService;

    private UserService userService;


    @GetMapping("/api/wallet")
    public ResponseEntity<Wallet> getUserWallet(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);

        Wallet wallet = walletService.getUserWallet(user);

        return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);

    }


    public ResponseEntity<Wallet> walletToWalletTransfer(@RequestHeader("Authorization") String jwt,
                                                         @RequestBody Long walletId,
                                                         @RequestBody WalletTransaction req) throws Exception {
        User senderUser = userService.findUserProfileByJwt(jwt);
        Wallet senderWallet = walletService.getUserWallet(senderUser);
        Wallet receiverWallet = walletService.findWalletById(walletId);
        Wallet wallet = walletService.walletToWalletTransfer(senderUser, senderWallet, receiverWallet, req.getAmount());
        return null;
    }



}
