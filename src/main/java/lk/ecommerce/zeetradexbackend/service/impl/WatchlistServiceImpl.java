package lk.ecommerce.zeetradexbackend.service.impl;

import lk.ecommerce.zeetradexbackend.entity.Coin;
import lk.ecommerce.zeetradexbackend.entity.User;
import lk.ecommerce.zeetradexbackend.entity.Watchlists;
import lk.ecommerce.zeetradexbackend.repo.WatchlistRepo;
import lk.ecommerce.zeetradexbackend.service.CoinService;
import lk.ecommerce.zeetradexbackend.service.UserService;
import lk.ecommerce.zeetradexbackend.service.WatchlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WatchlistServiceImpl implements WatchlistService {


    @Autowired
    private WatchlistRepo watchlistRepo;

//    @Autowired
//    private UserService userService;
//
//
//
//    @Autowired
//    private CoinService coinService;

    @Override
    public Watchlists findUserWatchlist(Long userId) throws Exception {
      Watchlists watchlists =  watchlistRepo.findByUserId(userId);
        if (watchlists == null) {
            throw  new Exception("Watchlist not found");
        }
        return watchlists;
    }

    @Override
    public Watchlists createWatchlist(User user) {
        Watchlists watchlists = new Watchlists();
        watchlists.setUser(user);

        return watchlistRepo.save(watchlists);
    }

    @Override
    public Watchlists findById(Long id) throws Exception {
       Optional<Watchlists> watchlistsOptional = watchlistRepo.findById(id);

        if (watchlistsOptional.isEmpty()) {
            throw new Exception("watchlist not found");
        }

        return watchlistsOptional.get();
       
    }

    //I check first if the coin is already present  in the watchlist well remove it
    //if not present edit
    @Override
    public Coin addItemToWatchlist(Coin coin, User user) throws Exception {

       Watchlists watchlists =  findUserWatchlist(user.getId());

        if (watchlists.getCoins().contains(coin)) {
            watchlists.getCoins().remove(coin);
        }
        else {
            watchlists.getCoins().add(coin);
        }
         watchlistRepo.save(watchlists);
        return coin;
    }
}
