package store.order.common.exchange.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import store.order.common.exchange.dto.ExchangeResponse;

@Service
public class ExchangeClient {

    private final RestClient client;

    public ExchangeClient() {
        client = RestClient.builder()
                .baseUrl("http://localhost:8000")
                .build();
    }

    public ExchangeResponse getExchange(String from, String to) {
        return client.get()
            .uri("/exchanges/{from}/{to}", from, to)
            .retrieve()
            .body(ExchangeResponse.class);
    }
}
