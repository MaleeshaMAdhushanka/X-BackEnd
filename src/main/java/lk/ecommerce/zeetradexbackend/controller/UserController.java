package lk.ecommerce.zeetradexbackend.controller;


import lk.ecommerce.zeetradexbackend.request.ForgotPasswordTokenRequest;
import lk.ecommerce.zeetradexbackend.entity.ForgotPasswordTokens;
import lk.ecommerce.zeetradexbackend.entity.User;
import lk.ecommerce.zeetradexbackend.entity.VerificationCode;
import lk.ecommerce.zeetradexbackend.enums.VerificationType;
import lk.ecommerce.zeetradexbackend.request.ResetPasswordRequest;
import lk.ecommerce.zeetradexbackend.response.ApiResponse;
import lk.ecommerce.zeetradexbackend.response.AuthResponse;
import lk.ecommerce.zeetradexbackend.service.EmailService;
import lk.ecommerce.zeetradexbackend.service.ForgotPasswordService;
import lk.ecommerce.zeetradexbackend.service.UserService;
import lk.ecommerce.zeetradexbackend.service.VerificationCodeService;
import lk.ecommerce.zeetradexbackend.util.OtpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private VerificationCodeService verificationCodeService;

    @Autowired
    private ForgotPasswordService forgotPasswordService;


    @GetMapping("/api/users/profile")
    public ResponseEntity<User> getUserProfile(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);


        return new ResponseEntity<User>(user, HttpStatus.OK);

    }
    // enabling two-factor auth

    //sending the otp

    @PostMapping("/api/users/verification/{verificationType}/send-otp")
    public ResponseEntity<String> sendVerificationOtp(@RequestHeader("Authorization") String jwt, @PathVariable VerificationType verificationType) throws Exception {


        User user = userService.findUserProfileByJwt(jwt);

        VerificationCode verificationCode = verificationCodeService.getVerificationCodeByUser(user.getId());


        if (verificationCode == null) {
            verificationCode = verificationCodeService.sendVerificationCode(user, verificationType);
        }
        if (verificationType.equals(VerificationType.EMAIL)) {
            emailService.sendVerificationOtpEmail(user.getEmail(), verificationCode.getOtp());
        }

        return new ResponseEntity<>("Verification Otp Send Successfully", HttpStatus.OK);
    }

    //verify otp
    @PatchMapping("/api/users/enable-two-factor/verify-otp/{otp}")
    public ResponseEntity<User> enableTwoFactorAuthentication(
            @PathVariable String otp,
            @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);

        VerificationCode verificationCode = verificationCodeService.getVerificationCodeByUser(user.getId());

        String sendTo = verificationCode.getVerificationType().equals(VerificationType.EMAIL) ?
                verificationCode.getEmail() : verificationCode.getMobile();

        boolean isVerified = verificationCode.getOtp().equals(otp);
        if (isVerified) {
            User updateUser = userService.enableTwoFactorAuthentication(VerificationType.valueOf(verificationCode.getVerificationType()), sendTo, user);

            verificationCodeService.deleteVerificationCodeById(verificationCode);

            return new ResponseEntity<>(updateUser, HttpStatus.OK);
        }
        throw new Exception("wrong otp");
    }


    @PostMapping("/auth/users/reset-password/send-otp")
    public ResponseEntity<AuthResponse> sendForgotPasswordOtp(@RequestBody ForgotPasswordTokenRequest req) throws Exception {

        User user = userService.findUserByEmail(req.getSendTo());
        String otp = OtpUtils.generateOtp();
        UUID uuid = UUID.randomUUID();
        String id = uuid.toString();

        ForgotPasswordTokens tokens = forgotPasswordService.findByUser(user.getId());
        if (tokens == null) {
            tokens = forgotPasswordService.createToken(user, id, otp, req.getVerificationType(), req.getSendTo());
        }
        if (req.getVerificationType().equals(VerificationType.EMAIL)) {
            emailService.sendVerificationOtpEmail(user.getEmail(), tokens.getOtp());
        }
        //now we need return the response password, otp send successfully

        AuthResponse response = new AuthResponse();
        response.setSession(tokens.getId());
        response.setMessage("Password reset otp send successfully");


        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/auth/users/reset-password/verify-otp/{otp}")
    public ResponseEntity<ApiResponse> resetPassword(
            @RequestParam String id,
            @RequestBody ResetPasswordRequest req,
            @RequestHeader("Authorization") String jwt) throws Exception {


        ForgotPasswordTokens forgotPasswordTokens = forgotPasswordService.findById(id);

        boolean isVerified = forgotPasswordTokens.getOtp().equals(req.getOtp());
        if (isVerified) {
            userService.updatePassword(forgotPasswordTokens.getUser(), req.getPassword());
            ApiResponse res = new ApiResponse();
            res.setMessage("password update successfully");

            return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
        }
        throw new Exception("wrong otp");

    }

}




