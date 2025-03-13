package lk.ecommerce.zeetradexbackend.controller;

import lk.ecommerce.zeetradexbackend.entity.User;
import lk.ecommerce.zeetradexbackend.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserRepo userRepo;

    @PostMapping("/signup")
    public ResponseEntity<User> registerUser(@RequestBody User user) {

        org.springframework.security.core.userdetails.User isUserExist = userRepo.findByEmail(user.getEmail());

        if (isUserExist != null) {
            throw new RuntimeException("User email already exist Used With Another Account");
        }

        User newUser = new User();
        newUser.setEmail(user.getEmail());

        newUser.setPassword(user.getPassword());
        newUser.setEmail(user.getEmail());
        newUser.setFullName(user.getFullName());
        newUser.setMobile(user.getMobile());

        User savedUser = userRepo.save(newUser);

        Authentication auth = new UsernamePasswordAuthenticationToken(
                user.getEmail(),
                user.getPassword()
        );
        SecurityContextHolder.getContext().setAuthentication(auth);

        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);

        
    }
}
