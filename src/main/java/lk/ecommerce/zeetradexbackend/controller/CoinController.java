package lk.ecommerce.zeetradexbackend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lk.ecommerce.zeetradexbackend.entity.Coin;
import lk.ecommerce.zeetradexbackend.service.CoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coins")
public class CoinController {

    @Autowired
    private CoinService coinService;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping
    ResponseEntity<List<Coin>>getCoinList(@RequestParam(required = false, name = "page") int page)  {

        try {
            List<Coin> coinList = coinService.getCoinList(page);
            return new ResponseEntity<>(coinList, HttpStatus.OK);
        } catch (Exception e) {
            // Return a bad request response if something goes wrong
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{coinId}/chart")
    ResponseEntity<JsonNode>getMarketChart(@PathVariable String coinId,
                                           @RequestParam("days") int days )  {

        try {
            String res = coinService.getMarketChart(coinId, days);
            JsonNode jsonNode = objectMapper.readTree(res);

            return new ResponseEntity<>(jsonNode, HttpStatus.OK);
        } catch (Exception e) {
            return  new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/search")
    ResponseEntity<JsonNode>searchCoin(@RequestParam("q") String keyword) {

        try {
            String coin = coinService.searchCoin(keyword);
            JsonNode jsonNode = objectMapper.readTree(coin);

            return ResponseEntity.ok(jsonNode);
        } catch (Exception e) {
            return  new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @GetMapping("/top50")
    ResponseEntity<JsonNode> getTop50CoinsByMarketCapRank() {
        try {
            String coin = coinService.getTop50CoinsByMarketCapRank();

            JsonNode jsonNode = objectMapper.readTree(coin);

            return ResponseEntity.ok(jsonNode);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/trending")
    ResponseEntity<JsonNode> getTradingCoin()  {
        try {
            String coin = coinService.getTreadingCoins();

            JsonNode jsonNode = objectMapper.readTree(coin);

            return ResponseEntity.ok(jsonNode);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/details/{coinId}")
    ResponseEntity<JsonNode> getCoinDetails(@PathVariable String coinId)  {
        try {
            String coin =  coinService.getCoinDetails(coinId);
            JsonNode jsonNode = objectMapper.readTree(coin);

            return ResponseEntity.ok(jsonNode);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }


}
