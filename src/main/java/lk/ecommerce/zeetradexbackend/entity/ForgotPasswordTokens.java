package lk.ecommerce.zeetradexbackend.entity;

import jakarta.persistence.*;
import lk.ecommerce.zeetradexbackend.enums.VerificationType;

@Entity
@Table(name = "forgot_password_tokens")
public class ForgotPasswordTokens {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    private String otp;

    private VerificationType verificationType;

    private String sendTo;

    //one user have one token
    @OneToOne
    private User user;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public VerificationType getVerificationType() {
        return verificationType;
    }

    public void setVerificationType(VerificationType verificationType) {
        this.verificationType = verificationType;
    }

    public String getSendTo() {
        return sendTo;
    }

    public void setSendTo(String sendTo) {
        this.sendTo = sendTo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
