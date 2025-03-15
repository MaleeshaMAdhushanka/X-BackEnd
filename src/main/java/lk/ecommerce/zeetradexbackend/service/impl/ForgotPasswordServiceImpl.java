package lk.ecommerce.zeetradexbackend.service.impl;

import lk.ecommerce.zeetradexbackend.entity.ForgotPasswordTokens;
import lk.ecommerce.zeetradexbackend.entity.User;
import lk.ecommerce.zeetradexbackend.enums.VerificationType;
import lk.ecommerce.zeetradexbackend.repo.ForgotPasswordRepo;
import lk.ecommerce.zeetradexbackend.service.ForgotPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ForgotPasswordServiceImpl implements ForgotPasswordService {
    @Autowired
    private ForgotPasswordRepo forgotPasswordRepo;

    @Override
    public ForgotPasswordTokens createToken(User user, String id, String otp, VerificationType verificationType, String sendTo) {

        ForgotPasswordTokens token = new ForgotPasswordTokens();

        token.setUser(user);
        token.setSendTo(sendTo);
        token.setVerificationType(verificationType);
        token.setOtp(otp);
        token.setId(id);
        return forgotPasswordRepo.save(token);
    }

    @Override
    public ForgotPasswordTokens findById(String id) {
        Optional<ForgotPasswordTokens> tokens = forgotPasswordRepo.findById(id);
        return tokens.orElse(null);
    }

    @Override
    public ForgotPasswordTokens findByUser(Long userId) {
        return forgotPasswordRepo.findByUserId(userId);
    }

    @Override
    public void deleteToken(ForgotPasswordTokens token) {
         forgotPasswordRepo.delete(token);

    }
}
