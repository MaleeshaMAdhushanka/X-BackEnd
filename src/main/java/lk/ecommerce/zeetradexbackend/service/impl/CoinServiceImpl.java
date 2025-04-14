package lk.ecommerce.zeetradexbackend.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lk.ecommerce.zeetradexbackend.entity.Coin;
import lk.ecommerce.zeetradexbackend.repo.CoinServiceRepo;
import lk.ecommerce.zeetradexbackend.service.CoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CoinServiceImpl implements CoinService {

    @Autowired
    private CoinServiceRepo coinServiceRepo;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private  RestTemplate restTemplate;

//    @Value("${coingecko.api.key}")
//    private String API_KEY;


    @Override
    public List<Coin> getCoinList(int page) throws Exception {
        String url = "https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&per_page=10&page="+page;
        //url ready now fetch data
//        RestTemplate restTemplate = new RestTemplate();


        try {
            HttpHeaders headers = new HttpHeaders();
//            headers.set("x-cg-demo-api-key", API_KEY);
            HttpEntity<String> entity = new HttpEntity<>("parameters",headers);

            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            List<Coin> coinList = objectMapper.readValue(response.getBody(), new TypeReference<List<Coin>>() {});

            return coinList;

        } catch (HttpClientErrorException | HttpServerErrorException e) {
            throw new Exception(e.getMessage());
        }

    }

    @Override
    public String getMarketChart(String coinId, int days) throws Exception {

        String url = "https://api.coingecko.com/api/v3/coins/"+coinId+"/market_chart?vs_currency=usd&days=" + days;
        //url ready now fetch data
//        RestTemplate restTemplate = new RestTemplate();


        try {
            HttpHeaders headers = new HttpHeaders();
//            headers.set("x-cg-demo-api-key", API_KEY);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            return response.getBody();

        } catch (HttpClientErrorException | HttpServerErrorException e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public String getCoinDetails(String coinId) throws Exception {
        String url = "https://api.coingecko.com/api/v3/coins/"+coinId;
        //url ready now fetch data
//        RestTemplate restTemplate = new RestTemplate();


        try {
            HttpHeaders headers = new HttpHeaders();
//            headers.set("x-cg-demo-api-key", API_KEY);

            HttpEntity<String> entity = new HttpEntity<>("parameters",headers);

            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            //whenever this endpoint tiger save coin data in our database

            JsonNode jsonNode = objectMapper.readTree(response.getBody());
            Coin coin = new Coin();
            coin.setId(jsonNode.get("id").asText());
            coin.setName(jsonNode.get("name").asText());
            coin.setSymbol(jsonNode.get("symbol").asText());
            coin.setImage(jsonNode.get("image").get("large").asText());

            JsonNode marketData = jsonNode.get("market_data");

            coin.setCurrentPrice(marketData.get("current_price").get("usd").asDouble());
            coin.setMarketCap(marketData.get("market_cap").get("usd").asLong());
            coin.setMarketCapRank(marketData.get("market_cap_rank").asInt());
            coin.setTotalVolume(marketData.get("total_volume").get("usd").asLong());
            coin.setHigh24h(marketData.get("high_24h").get("usd").asDouble());
            coin.setLow24h(marketData.get("low_24h").get("usd").asDouble());
            coin.setPriceChange24h(marketData.get("price_change_24h").asDouble());
            coin.setPriceChangePercentage24h(marketData.get("price_change_percentage_24h").asDouble());

            coin.setMarketCapChange24h(marketData.get("market_cap_change_24h").asLong());

            coin.setMarketCapChangePercentage24h(marketData.get("market_cap_change_percentage_24h").asLong());




            //get null point exception fixing it
//            JsonNode marketCapChange24hNode =marketData.get("market_cap_change_24h").get("usd");
//            if (marketCapChange24hNode != null) {
//                coin.setMarketCapChange24h(marketCapChange24hNode.asLong());
//
//            }
//            JsonNode marketCapChange24hNode = marketData.get("market_cap_change_24h");
//            if (marketCapChange24hNode != null && marketCapChange24hNode.has("usd")) {
//            }


            coin.setTotalSupply(marketData.get("total_supply").asLong());
            coinServiceRepo.save(coin);
            return response.getBody();

        } catch (HttpClientErrorException | HttpServerErrorException e) {
            System.out.println("error-----" +e.getMessage());
            throw new Exception(e.getMessage());
        }

    }

    @Override
    public Coin findById(String coinId) throws Exception {
        Optional<Coin>optionalCoin = coinServiceRepo.findById(coinId);
        if (optionalCoin.isEmpty())throw new Exception("coin Not found");

      return optionalCoin.get();

    }

    @Override
    public String searchCoin(String keyword) throws Exception {
        String url = "https://api.coingecko.com/api/v3/search?query="+keyword;
        //url ready now fetch data
//        RestTemplate restTemplate = new RestTemplate();


        try {
            HttpHeaders headers = new HttpHeaders();
//            headers.set("x-cg-demo-api-key", API_KEY);

            HttpEntity<String> entity = new HttpEntity<>("parameters",headers);

            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);


            return response.getBody();

        } catch (HttpClientErrorException | HttpServerErrorException e) {
            throw new Exception(e.getMessage());
        }

    }

    @Override
    public String getTop50CoinsByMarketCapRank() throws Exception {
        String url = "https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&per_page=40&page=1";
        //url ready now fetch data
//        RestTemplate restTemplate = new RestTemplate();


        try {
            HttpHeaders headers = new HttpHeaders();
//            headers.set("x-cg-demo-api-key", API_KEY);
            HttpEntity<String> entity = new HttpEntity<>("parameters",headers);

            //add delay to prevent reate limiting
            Thread.sleep(10000);

            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            if (response.getStatusCode() == HttpStatus.TOO_MANY_REQUESTS) {
                 throw  new Exception("Coin gecko Api rete limit exceeded");
            }

            return response.getBody();

        } catch (HttpClientErrorException.TooManyRequests e) {
            throw new Exception("CoinGecko API rate limit exceeded. Please try again later.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw  new Exception("Request interrupted");
        } catch (Exception e) {
            throw new Exception("Failed to fetch top 50 coins: " + e.getMessage());
        }

    }

    @Override
    public String getTreadingCoins() throws Exception {

        String url = "https://api.coingecko.com/api/v3/search/trending";
        //url ready now fetch data
//        RestTemplate restTemplate = new RestTemplate();


        try {
            HttpHeaders headers = new HttpHeaders();
//            headers.set("x-cg-demo-api-key", API_KEY);

            HttpEntity<String> entity = new HttpEntity<>("parameters",headers);

            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);


            return response.getBody();

        } catch (HttpClientErrorException | HttpServerErrorException e) {
            throw new Exception(e.getMessage());
        }
    }
}
