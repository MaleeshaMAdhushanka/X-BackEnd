package lk.ecommerce.zeetradexbackend.service;

import lk.ecommerce.zeetradexbackend.entity.User;
import lk.ecommerce.zeetradexbackend.entity.Withdrawal;

import java.util.List;

public interface WithdrawService {

    //we need method req withdraw
    //we need method proceed withdraw
    //get user withdraw history
    //get all withdraw request for admin

    //which user made this request
    Withdrawal requestWithdraw(Long amount, User user);

    //either is accepted by admin or  decline
    Withdrawal proceedWithdraw(Long  withdrawalId, boolean accept) throws Exception;

    List<Withdrawal> getUserWithdrawHistory(User user);


    List<Withdrawal> getAllWithdrawRequest();

}
