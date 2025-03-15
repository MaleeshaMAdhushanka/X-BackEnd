package lk.ecommerce.zeetradexbackend.controller;

import jakarta.mail.MessagingException;
import lk.ecommerce.zeetradexbackend.config.JwtProvider;
import lk.ecommerce.zeetradexbackend.entity.TwoFactorOTP;
import lk.ecommerce.zeetradexbackend.entity.User;
import lk.ecommerce.zeetradexbackend.response.AuthResponse;
import lk.ecommerce.zeetradexbackend.repo.UserRepo;
import lk.ecommerce.zeetradexbackend.service.CustomeUserDetailsService;
import lk.ecommerce.zeetradexbackend.service.EmailService;
import lk.ecommerce.zeetradexbackend.service.TwoFactorOtpService;
import lk.ecommerce.zeetradexbackend.util.OtpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CachingUserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CustomeUserDetailsService customeUserDetailsService;

    @Autowired
    private TwoFactorOtpService twoFactorOtpService;

    @Autowired
    private EmailService emailService;

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
    public ResponseEntity<AuthResponse> login(@RequestBody User user) throws Exception {

        String userName = user.getEmail();
        String password = user.getPassword();


        Authentication auth = authenticate(userName, password);
        
        SecurityContextHolder.getContext().setAuthentication(auth);

        //after creating jwt token we need to send it to the fontend
        String jwt= JwtProvider.generateToken(auth);

        User authUser = userRepo.findByEmail(userName);

        if (user.getTwoFactorAuth().isEnabled()) {
            AuthResponse res = new AuthResponse();
            res.setMessage("Two factor auth is enabled");
            res.setTwoFactorEnabled(true);
            //generate kara otp eka

            String otp= OtpUtils.generateOtp();

            //enble nam create otp- old otp delete karala
            TwoFactorOTP oldTwoFactorOTP = twoFactorOtpService.findByUser(authUser.getId());
            if (oldTwoFactorOTP != null) {
                twoFactorOtpService.deleteTwoFactorOtp(oldTwoFactorOTP);
            }

            TwoFactorOTP newTwoFactorOTP = twoFactorOtpService.createTwoFactorOtp(
                    authUser, otp, jwt);

            emailService.sendVerificationOtpEmail(userName, otp);


            res.setSession(newTwoFactorOTP.getId());
            return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
        }

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
            throw new BadCredentialsException("invalid username");
        }
        //wrong password it will get error
        if (!password.equals(userDetails.getPassword())) {
            throw new BadCredentialsException("invalid password");
        }
        //password also correct
        //return authentication
        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

    }
     @PostMapping("/two-factor/otp/{otp}")
    public ResponseEntity<AuthResponse> verifySignInOtp(
            @PathVariable String otp,
            @RequestParam String id) throws Exception {

        TwoFactorOTP twoFactorOTP=twoFactorOtpService.findById(id);
        if (twoFactorOtpService.verifyTwoFactorOtp(twoFactorOTP, otp)) {
              AuthResponse res = new AuthResponse();
              res.setMessage("Two factor authentication verified");
              res.setTwoFactorEnabled(true);
              res.setJwt(twoFactorOTP.getJwt());
              return new ResponseEntity<>(res, HttpStatus.OK);
        }
        throw new Exception("invalid otp");
    }


}
