package lk.ecommerce.zeetradexbackend.service;

import lk.ecommerce.zeetradexbackend.entity.Wallet;
import lk.ecommerce.zeetradexbackend.enums.WalletTransactionType;

public interface TransactionService {
   void createTransaction(Wallet wallet, WalletTransactionType type, Long receiverId, String purpose ,Long amount );
}
