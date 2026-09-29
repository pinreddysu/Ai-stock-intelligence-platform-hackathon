package com.ai_stock_intelligience.aiStockIntelligenceBackend.Controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class ImportStockDetailsClient {

    private final WebClient webClient;

    @Value("${alphavantage.api.key}")
    private String apiKey;

    public ImportStockDetailsClient(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("https://www.alphavantage.co").build();
    }

    public String importStockDetails(String ticker){
        return webClient.get()
                .uri(uriBuilder ->
                    uriBuilder.path("/query")
                            .queryParam("function", "OVERVIEW")
                            .queryParam("symbol", ticker.trim())
                            .queryParam("apikey", apiKey)
                            .build()).retrieve().bodyToMono(String.class).block();
    }
}
