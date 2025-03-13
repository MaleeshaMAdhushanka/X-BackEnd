package lk.ecommerce.zeetradexbackend.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "wallets")
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double balance;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "wallet")
    private List<WalletTransaction> transactions;
}
