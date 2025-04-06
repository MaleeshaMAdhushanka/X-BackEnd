package lk.ecommerce.zeetradexbackend.controller;

import lk.ecommerce.zeetradexbackend.entity.Coin;
import lk.ecommerce.zeetradexbackend.entity.User;
import lk.ecommerce.zeetradexbackend.entity.Watchlists;
import lk.ecommerce.zeetradexbackend.service.CoinService;
import lk.ecommerce.zeetradexbackend.service.UserService;
import lk.ecommerce.zeetradexbackend.service.WatchlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/watchlist")
public class WatchlistController {

    @Autowired
    private WatchlistService watchlistService;

    @Autowired
    private UserService userService;

    @Autowired
    private CoinService coinService;


    @GetMapping("/user")
    public ResponseEntity<Watchlists> getUserWatchlist(
            @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserProfileByJwt(jwt);

        try {
            Watchlists watchlists = watchlistService.findUserWatchlist(user.getId());
            return ResponseEntity.ok(watchlists);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @PostMapping("/create")
    public ResponseEntity<Watchlists> createWatchlist(
            @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserProfileByJwt(jwt);
      Watchlists createdWatchlist =  watchlistService.createWatchlist(user);
      return  ResponseEntity.status(HttpStatus.CREATED).body(createdWatchlist);


    }

    @GetMapping("/{watchlistId}")
    public ResponseEntity<Watchlists> getWatchlistById(
            @PathVariable Long watchlistId
    ) throws Exception {

        Watchlists watchlists = watchlistService.findById(watchlistId);
        return ResponseEntity.ok(watchlists);
    }

    @PatchMapping("/add/coin/{coinId}")
    public ResponseEntity<Coin> addItemToWatchlist(
            @RequestHeader("Authorization") String jwt,
            @PathVariable String coinId) throws Exception {

       User user = userService.findUserProfileByJwt(jwt);
       Coin coin = coinService.findById(coinId);
       Coin addedCoin = watchlistService.addItemToWatchlist(coin, user);
       return ResponseEntity.ok(addedCoin);

    }






}
