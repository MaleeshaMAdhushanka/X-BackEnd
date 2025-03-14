package lk.ecommerce.zeetradexbackend.service;

import lk.ecommerce.zeetradexbackend.entity.TwoFactorOTP;
import lk.ecommerce.zeetradexbackend.entity.User;

public interface TwoFactorOtpService {

    TwoFactorOTP createTwoFactorOtp(User user, String otp, String jwt);

    TwoFactorOTP findByUser(Long  userId);

    TwoFactorOTP findById(String id);

    //verify it (OTP should be match)
    //1 OTP come form database 2 OTP is come User (Compare return)
    boolean verifyTwoFactorOtp(TwoFactorOTP twoFactorOtp, String otp);

    void deleteTwoFactorOtp(TwoFactorOTP twoFactorOtp);
}
