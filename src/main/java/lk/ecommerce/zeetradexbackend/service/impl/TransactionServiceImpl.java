package lk.ecommerce.zeetradexbackend.service.impl;

import lk.ecommerce.zeetradexbackend.entity.Wallet;
import lk.ecommerce.zeetradexbackend.entity.WalletTransaction;
import lk.ecommerce.zeetradexbackend.enums.WalletTransactionType;
import lk.ecommerce.zeetradexbackend.repo.WalletTransactionRepo;
import lk.ecommerce.zeetradexbackend.service.TransactionService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TransactionServiceImpl implements TransactionService {

    private WalletTransactionRepo walletTransactionRepo;
    @Override
    public void createTransaction(Wallet wallet, WalletTransactionType type, Long receiverId, String purpose, Long amount) {
        WalletTransaction transaction = new WalletTransaction();
        transaction.setWallet(wallet);
        transaction.setType(type);
        transaction.setDate(LocalDateTime.now());
        transaction.setTransferId(receiverId != null ? receiverId.toString() : null);
        transaction.setPurpose(purpose);
        transaction.setAmount(amount);

        walletTransactionRepo.save(transaction);
    }
}
