package lk.ecommerce.zeetradexbackend.service.impl;

import lk.ecommerce.zeetradexbackend.entity.PaymentDetails;
import lk.ecommerce.zeetradexbackend.entity.User;
import lk.ecommerce.zeetradexbackend.repo.PaymentDetailsRepo;
import lk.ecommerce.zeetradexbackend.service.PaymentDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentDetailsServiceImpl implements PaymentDetailsService {

    @Autowired
    private PaymentDetailsRepo paymentDetailsRepo;


    @Override
    public PaymentDetails addPaymentDetails(String accountNumber, String accountHolderName, String ifcs, String bankName, User user) {
        PaymentDetails paymentDetails = new PaymentDetails();
        paymentDetails.setAccountNumber(accountNumber);
        paymentDetails.setAccountHolderName(accountHolderName);
        paymentDetails.setIfsc(ifcs);
        paymentDetails.setBankName(bankName);
        paymentDetails.setUser(user);

        return paymentDetailsRepo.save(paymentDetails);
    }

    @Override
    public PaymentDetails getUsersPaymentDetails(User user) {
        return paymentDetailsRepo.findByUserId(user.getId());
    }
}
