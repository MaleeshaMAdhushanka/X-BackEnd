package lk.ecommerce.zeetradexbackend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
@Entity
@Table(name = "wallet_transactions")
public class WalletTransaction {
    @Id
    private Long id;

    private String type;
    private LocalDateTime date;
    private String transferId;
    private String purpose;
    private Double amount;
}
