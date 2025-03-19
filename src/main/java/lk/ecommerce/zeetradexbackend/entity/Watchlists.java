package lk.ecommerce.zeetradexbackend.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

//user can have save coin their watch list an watch them also remove alwell
@Entity
@Table(name = "watchlists")
public class Watchlists {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //one user have only one watch list
    @OneToOne
    private User user;

    //this
    @ManyToMany
    @JoinTable(
            name = "watchlist_coins",
            joinColumns = @JoinColumn(name = "watchlist_id"),
            inverseJoinColumns = @JoinColumn(name = "coin_id")
    )
    private List<Coin> coins = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Coin> getCoins() {
        return coins;
    }

    public void setCoins(List<Coin> coins) {
        this.coins = coins;
    }
}
