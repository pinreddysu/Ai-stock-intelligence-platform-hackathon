package com.ai_stock_intelligience.aiStockIntelligenceBackend.Controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class ImportStockPriceClient {

    private final WebClient webClient;

    @Value("${fmp.api.key}")
    private String apikey;

    public ImportStockPriceClient(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("https://financialmodelingprep.com").build();
    }

    public String importStockPrices(String ticker){
        return webClient.get()
                .uri(uriBuilder ->
                    uriBuilder.path("stable/quote")
                            .queryParam("symbol", ticker.trim())
                            .queryParam("apikey", apikey)
                            .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }


}
