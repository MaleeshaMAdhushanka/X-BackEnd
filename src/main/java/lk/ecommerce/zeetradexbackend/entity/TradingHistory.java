package lk.ecommerce.zeetradexbackend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "trading_histories")
public class TradingHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double sellingPrice;
    private Double buyingPrice;

    @ManyToOne
    @JoinColumn(name = "coin_id", nullable = false)
    private Coin coin;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
