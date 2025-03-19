package lk.ecommerce.zeetradexbackend.service;

import lk.ecommerce.zeetradexbackend.entity.Coin;
import lk.ecommerce.zeetradexbackend.entity.User;
import lk.ecommerce.zeetradexbackend.entity.Watchlists;

public interface WatchlistService {

    //findUserWatchlist

    Watchlists findUserWatchlist(Long userId) throws Exception;



    //create watchlist

      Watchlists createWatchlist(User user);


    //find by Id

    Watchlists findById(Long id) throws Exception;


    //add Item to watch list
    Coin addItemToWatchlist(Coin coin, User user) throws Exception;



}
