package lk.ecommerce.zeetradexbackend.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "watchlists")
public class watchlists {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToMany
    @JoinTable(
            name = "watchlist_coins",
            joinColumns = @JoinColumn(name = "watchlist_id"),
            inverseJoinColumns = @JoinColumn(name = "coin_id")
    )
    private List<Coin> coins;
}
