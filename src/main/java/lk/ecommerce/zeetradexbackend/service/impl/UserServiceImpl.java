package lk.ecommerce.zeetradexbackend.service.impl;

import lk.ecommerce.zeetradexbackend.config.JwtProvider;
import lk.ecommerce.zeetradexbackend.entity.TwoFactorAuth;
import lk.ecommerce.zeetradexbackend.entity.User;
import lk.ecommerce.zeetradexbackend.enums.VerificationType;
import lk.ecommerce.zeetradexbackend.repo.UserRepo;
import lk.ecommerce.zeetradexbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class UserServiceImpl implements UserService {

    @Autowired
    public UserRepo userRepo;

    @Override
    public User findUserProfileByJwt(String jwt) throws Exception {
      String email = JwtProvider.getEmailFormToken(jwt);
      User user = userRepo.findByEmail(email);
        if (user == null) {
            throw new Exception("User not found");
        }
        return user;

    }

    @Override
    public User findUserByEmail(String email) throws Exception {
       User user = userRepo.findByEmail(email);
        if (user == null) {
            throw new Exception("User not found");
        }
        return user;
    }

    @Override
    public User findUserById(Long userId) throws Exception {
       Optional<User> user = userRepo.findById(userId);
        if (user.isEmpty()) {
            throw new Exception("User not found");
        }
        return user.get();
    }

    @Override
    public User enableTwoFactorAuthentication(VerificationType verificationType, String sendTo, User user) {
        TwoFactorAuth twoFactorAuth = new TwoFactorAuth();
        twoFactorAuth.setEnabled(true);
        twoFactorAuth.setSendTo(verificationType);


        user.setTwoFactorAuth(twoFactorAuth);

        return userRepo.save(user);
    }


    @Override
    public User updatePassword(User user, String newPassword) {
        user.setPassword(newPassword);
        return userRepo.save(user);
    }
}
