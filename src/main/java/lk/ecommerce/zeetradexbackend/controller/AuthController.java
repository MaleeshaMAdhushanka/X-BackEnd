package lk.ecommerce.zeetradexbackend.controller;

import lk.ecommerce.zeetradexbackend.config.JwtProvider;
import lk.ecommerce.zeetradexbackend.entity.User;
import lk.ecommerce.zeetradexbackend.response.AuthResponse;
import lk.ecommerce.zeetradexbackend.repo.UserRepo;
import lk.ecommerce.zeetradexbackend.service.CustomeUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CachingUserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CustomeUserDetailsService customeUserDetailsService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> registerUser(@RequestBody User user) {

        User isUserExist = userRepo.findByEmail(user.getEmail());

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

        //after creating jwt token we need to send it to the fontend
        String jwt= JwtProvider.generateToken(auth);

        AuthResponse res = new AuthResponse();
        res.setJwt(jwt);
        res.setStatus(true);
        res.setMessage("register success");

        return new ResponseEntity<>(res, HttpStatus.CREATED);

        
    }
    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> login(@RequestBody User user) {

        String userName = user.getEmail();
        String password = user.getPassword();


        Authentication auth = authenticate(userName, password);
        
        SecurityContextHolder.getContext().setAuthentication(auth);

        //after creating jwt token we need to send it to the fontend
        String jwt= JwtProvider.generateToken(auth);

        AuthResponse res = new AuthResponse();
        res.setJwt(jwt);
        res.setStatus(true);
        res.setMessage("login success");

        return new ResponseEntity<>(res, HttpStatus.CREATED);


    }

    //check user isExist
    private Authentication authenticate(String userName, String password) {
        UserDetails userDetails = customeUserDetailsService.loadUserByUsername(userName);

        if (userDetails == null) {
            throw new BadCredentialsException("invalid username or password");
        }
        if (!password.equals(userDetails.getPassword())) {
            throw new BadCredentialsException("invalid password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());


    }
}
