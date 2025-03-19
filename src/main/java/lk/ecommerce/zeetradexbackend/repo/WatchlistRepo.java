package lk.ecommerce.zeetradexbackend.repo;

import lk.ecommerce.zeetradexbackend.entity.Watchlists;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WatchlistRepo extends JpaRepository<Watchlists, Long> {
    //findUser watchlist
    Watchlists findByUserId(Long userId);



}
