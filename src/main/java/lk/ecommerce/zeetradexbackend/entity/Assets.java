package lk.ecommerce.zeetradexbackend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "assets")
public class Assets {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private Double quantity;

    private Double buyPrice;

    @ManyToOne
    @JoinColumn(name = "coin_id", nullable = false)
    private Coin coin;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
