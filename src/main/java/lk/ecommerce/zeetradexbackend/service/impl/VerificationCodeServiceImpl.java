package lk.ecommerce.zeetradexbackend.service.impl;

import lk.ecommerce.zeetradexbackend.entity.User;
import lk.ecommerce.zeetradexbackend.entity.VerificationCode;
import lk.ecommerce.zeetradexbackend.enums.VerificationType;
import lk.ecommerce.zeetradexbackend.repo.VerificationCodeRepo;
import lk.ecommerce.zeetradexbackend.service.VerificationCodeService;
import lk.ecommerce.zeetradexbackend.util.OtpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VerificationCodeServiceImpl implements VerificationCodeService {

    @Autowired
    private VerificationCodeRepo verificationCodeRepo;

    @Override
    public VerificationCode sendVerificationCode(User user, VerificationType verificationType) {
        VerificationCode verificationCode1= new VerificationCode();
        verificationCode1.setOtp(OtpUtils.generateOtp());
        verificationCode1.setVerificationType(verificationType.name());
        verificationCode1.setUser(user);

        return verificationCodeRepo.save(verificationCode1);


    }

    @Override
    public VerificationCode getVerificationCode(Long id) throws Exception {
        Optional<VerificationCode> verificationCode = verificationCodeRepo.findById(id);
        if (verificationCode.isPresent()) {
            return verificationCode.get();
        }
        throw  new Exception("verification Code not Found");
    }

    @Override
    public VerificationCode getVerificationCodeByUser(Long userId) {
        return verificationCodeRepo.findByUserId(userId);
    }

    @Override
    public void deleteVerificationCodeById(VerificationCode verificationCode) {
       verificationCodeRepo.delete(verificationCode);
    }
}
