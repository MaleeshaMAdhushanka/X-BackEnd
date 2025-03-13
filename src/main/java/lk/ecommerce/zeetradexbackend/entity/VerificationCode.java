package lk.ecommerce.zeetradexbackend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "verification_codes")
public class VerificationCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String otp;
    private String email;
    private String mobile;
    private String verificationType;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
