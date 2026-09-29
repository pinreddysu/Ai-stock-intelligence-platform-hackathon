package com.ai_stock_intelligience.aiStockIntelligenceBackend.Controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class ImportStocksDataClient {

    private final WebClient webClient;

    @Value("${sec.api.header.value}")
    private String headerValues;

    public ImportStocksDataClient(WebClient.Builder builder){
        this.webClient = builder.baseUrl("https://www.sec.gov").build();
    }

    public String getStocksDetailsList(){
        return webClient.get()
                .uri("/files/company_tickers.json")
                .header(
                        "User-Agent",
                        headerValues
                )
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

}
