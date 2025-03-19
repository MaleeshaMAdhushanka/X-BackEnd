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

@Service
public class WatchlistServiceImpl implements WatchlistService {


    @Autowired
    private WatchlistRepo watchlistRepo;

    @Autowired
    private UserService userService;



    @Autowired
    private CoinService coinService;

    @Override
    public Watchlists findUserWatchlist(Long userId) {
        return null;
    }

    @Override
    public Watchlists createWatchlist(User user) {
        return null;
    }

    @Override
    public Watchlists findById(Long id) {
        return null;
    }

    @Override
    public Coin addItemToWatchlist(Coin coin, User user) {
        return null;
    }
}
