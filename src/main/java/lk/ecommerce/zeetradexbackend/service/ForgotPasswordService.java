package lk.ecommerce.zeetradexbackend.service;

import lk.ecommerce.zeetradexbackend.entity.ForgotPasswordTokens;
import lk.ecommerce.zeetradexbackend.entity.User;
import lk.ecommerce.zeetradexbackend.enums.VerificationType;

public interface ForgotPasswordService {

    ForgotPasswordTokens createToken(User user, String id, String otp, VerificationType verificationType, String sendTo);

    ForgotPasswordTokens findById(String id);

    ForgotPasswordTokens findByUser(Long userId);

    void deleteToken(ForgotPasswordTokens token);

}
