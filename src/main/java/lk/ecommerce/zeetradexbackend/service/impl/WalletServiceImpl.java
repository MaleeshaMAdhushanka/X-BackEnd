package lk.ecommerce.zeetradexbackend.service.impl;

import lk.ecommerce.zeetradexbackend.entity.Order;
import lk.ecommerce.zeetradexbackend.entity.User;
import lk.ecommerce.zeetradexbackend.entity.Wallet;
import lk.ecommerce.zeetradexbackend.enums.OrderType;
import lk.ecommerce.zeetradexbackend.repo.WalletRepo;
import lk.ecommerce.zeetradexbackend.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class WalletServiceImpl implements WalletService {

    @Autowired
    private WalletRepo walletRepo;

    @Override
    public Wallet getUserWallet(User user) {
        Wallet wallet = walletRepo.findByUserId(user.getId());
        if (wallet == null) {
            wallet= new Wallet();
            wallet.setUser(user);
            walletRepo.save(wallet);
        }

        return wallet;
    }

    @Override
    public Wallet addBalance(Wallet wallet, Long money) {
        BigDecimal bigDecimal = wallet.getBalance();
        BigDecimal newBalance = bigDecimal.add(BigDecimal.valueOf(money));

        wallet.setBalance(newBalance);

      return  walletRepo.save(wallet);
    }

    @Override
    public Wallet findWalletById(Long id) throws Exception {
        Optional<Wallet> wallet = walletRepo.findById(id);
        if (wallet.isPresent()) {
            return wallet.get();
        }
        throw  new Exception("Wallet not found");
    }

//    @Override
//    public Wallet walletToWalletTransfer(User sender, Wallet receiver, Wallet receiverWallet, Long amount) throws Exception {
//
//        Wallet senderWallet = getUserWallet(sender);
//
//        if (senderWallet.getBalance().compareTo(BigDecimal.valueOf(amount)) < 0) {
//            throw new Exception("Insufficient balance....");
//        }
//        //now update the sender wallet balance
//        BigDecimal senderBalance = senderWallet
//                .getBalance()
//                .subtract(BigDecimal.valueOf(amount));
//         senderWallet.setBalance(senderBalance);
//
//         walletRepo.save(senderWallet);
//
//         BigDecimal receiverBalance = receiverWallet.getBalance().add(BigDecimal.valueOf(amount));
//
//       //now update the balance
//        receiverWallet.setBalance(receiverBalance);
//        walletRepo.save(receiverWallet);
//        return senderWallet;
//    }
@Override
public Wallet walletToWalletTransfer(User sender, Wallet receiver, Wallet receiverWallet, Long amount) throws Exception {

    Wallet senderWallet = getUserWallet(sender);

    BigDecimal transferAmount = BigDecimal.valueOf(amount);
    BigDecimal currentBalance = senderWallet.getBalance();

    System.out.println("Sender Wallet Balance: " + currentBalance);
    System.out.println("Requested Transfer Amount: " + transferAmount);

    if (currentBalance.compareTo(transferAmount) < 0) {
        throw new Exception("Insufficient balance. You have " + currentBalance + ", but tried to send " + transferAmount);
    }

    // Subtract from sender
    BigDecimal updatedSenderBalance = currentBalance.subtract(transferAmount);
    senderWallet.setBalance(updatedSenderBalance);
    walletRepo.save(senderWallet);

    // Add to receiver
    BigDecimal updatedReceiverBalance = receiverWallet.getBalance().add(transferAmount);
    receiverWallet.setBalance(updatedReceiverBalance);
    walletRepo.save(receiverWallet);

    return senderWallet;
}

    @Override
    public Wallet payOrderPayment(Order order, User user) throws Exception {
        Wallet wallet = getUserWallet(user);

        //create wallet transaction
        if (order.getOrderType().equals(OrderType.BUY)) {
          BigDecimal  newBalance = wallet.getBalance().subtract(order.getPrice());
            if (newBalance.compareTo(order.getPrice()) <0) {
                throw  new Exception("Insufficient Funds for this transaction");
            }
            wallet.setBalance(newBalance);
        }
        //type is sell nam add balance to wallet
        else {
            BigDecimal newBalance = wallet.getBalance().add(order.getPrice());
            wallet.setBalance(newBalance);
        }
        walletRepo.save(wallet);
        return wallet;
    }
}
