package lk.ecommerce.zeetradexbackend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "forgot_password_tokens")
public class ForgotPasswordTokens {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String otp;
    private String verificationType;
    private String sendTo;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
