package com.ai_stock_intelligience.aiStockIntelligenceBackend.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StockImportDto {

    @JsonProperty("cik_str")
    private Long cik;

    @JsonProperty("ticker")
    private String ticker;

    @JsonProperty("title")
    private String companyName;


    public String getTicker() {
        return ticker;
    }

    public String getCompanyName() {
        return companyName;
    }
}
