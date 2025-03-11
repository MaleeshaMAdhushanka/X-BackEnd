package lk.ecommerce.zeetradexbackend.controller;

import lk.ecommerce.zeetradexbackend.entity.User;
import lk.ecommerce.zeetradexbackend.repo.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<User> register(@RequestBody User user){
       User newUser = new User();
       newUser.setEmail(user.getEmail());
       newUser.setPassword(user.getPassword());
//       newUser.se(user.getFullName());


//       User savedUser = userRepo.save(newUser);
        User savedUser = userRepo.save(newUser);

       return  new ResponseEntity<>(savedUser,HttpStatus.CREATED);
    }

}
