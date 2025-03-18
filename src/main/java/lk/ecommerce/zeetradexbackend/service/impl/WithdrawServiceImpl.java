package lk.ecommerce.zeetradexbackend.service.impl;

import lk.ecommerce.zeetradexbackend.entity.Order;
import lk.ecommerce.zeetradexbackend.entity.User;
import lk.ecommerce.zeetradexbackend.entity.Wallet;
import lk.ecommerce.zeetradexbackend.entity.Withdrawal;
import lk.ecommerce.zeetradexbackend.enums.WithdrawalStatus;
import lk.ecommerce.zeetradexbackend.repo.WithdrawRepo;
import lk.ecommerce.zeetradexbackend.service.UserService;
import lk.ecommerce.zeetradexbackend.service.WalletService;
import lk.ecommerce.zeetradexbackend.service.WithdrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class WithdrawServiceImpl implements WithdrawService {


    @Autowired
    private WithdrawRepo withdrawRepo;

    @Override
    public Withdrawal requestWithdraw(Long amount, User user) {
        Withdrawal withdrawal = new Withdrawal();
        withdrawal.setAmount(amount);
        withdrawal.setUser(user);
        withdrawal.setStatus(WithdrawalStatus.PENDING);

        return withdrawRepo.save(withdrawal);

    }

    @Override
    public Withdrawal proceedWithdraw(Long withdrawalId, boolean accept) throws Exception {

        Optional<Withdrawal> withdrawal = withdrawRepo.findById(withdrawalId);

        if (withdrawal.isEmpty()) {
           throw  new Exception("Withdrawal not found");
        }
        Withdrawal withdrawal1 = withdrawal.get();
        withdrawal1.setDate(LocalDateTime.now());

        if (accept) {
            withdrawal1.setStatus(WithdrawalStatus.SUCCESS);
        }
        else {
            withdrawal1.setStatus(WithdrawalStatus.PENDING);
        }
        return withdrawRepo.save(withdrawal1);

    }

    @Override
    public List<Withdrawal> getUserWithdrawHistory(User user) {
        return withdrawRepo.findByUserId(user.getId());
    }

    @Override
    public List<Withdrawal> getAllWithdrawRequest() {
        return withdrawRepo.findAll();
    }
}
