package lk.ecommerce.zeetradexbackend.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
@Entity
@Table(name = "withdrawals")
public class Withdrawal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String status;
    private Double amount;
    private LocalDateTime date;


}
