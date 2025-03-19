package lk.ecommerce.zeetradexbackend.service;

import lk.ecommerce.zeetradexbackend.entity.PaymentDetails;
import lk.ecommerce.zeetradexbackend.entity.User;

public interface PaymentDetailsService {
    //add payment details
    public PaymentDetails addPaymentDetails(String accountNumber,
                                            String accountHolderName,
                                            String ifcs,
                                            String bankName,
                                            User user);

    //get payment details of user
    public PaymentDetails getUsersPaymentDetails(User user);

}
