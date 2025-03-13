package lk.ecommerce.zeetradexbackend.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
@Entity
@Table(name = "wallet_transactions")
public class WalletTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    private LocalDateTime date;
    private String transferId;
    private String purpose;
    private Double amount;

    @ManyToOne
    @JoinColumn(name = "wallet_id", nullable = false)
    private Wallet wallet;
}
