package com.bobotang.hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.util.*;

@RestController
public class HelloController {

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/api/hello")
    public String hello() {
        return "Hello World!!";
    }

    @GetMapping("/hello")
    public String helloPath() {
        return "Hello World!! (from /hello endpoint)";
    }

    @GetMapping("/api/stock/{symbol}")
    public Map<String, Object> getStock(@PathVariable String symbol) {
        String url = "https://query1.finance.yahoo.com/v7/finance/quote?symbols=" + symbol.toUpperCase();
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        try {
            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
            Map body = response.getBody();
            if (body == null) return errorResponse(symbol);
            Map quoteResponse = (Map) body.get("quoteResponse");
            if (quoteResponse == null) return errorResponse(symbol);
            List results = (List) quoteResponse.get("result");
            if (results == null || results.isEmpty()) return errorResponse(symbol);
            Map quote = (Map) results.get(0);
            Map<String, Object> stock = new HashMap<>();
            stock.put("symbol", quote.get("symbol"));
            stock.put("name", quote.getOrDefault("shortName", quote.get("longName")));
            stock.put("price", quote.get("regularMarketPrice"));
            stock.put("change", quote.get("regularMarketChangePercent"));
            return stock;
        } catch (Exception e) {
            return errorResponse(symbol);
        }
    }

    private Map<String, Object> errorResponse(String symbol) {
        Map<String, Object> err = new HashMap<>();
        err.put("symbol", symbol.toUpperCase());
        err.put("name", "N/A");
        err.put("price", "N/A");
        err.put("change", "N/A");
        err.put("error", "Unable to fetch data");
        return err;
    }
}