package com.ai_stock_intelligience.aiStockIntelligenceBackend.Dto.Response;

public interface HoldingsDTO {

    Long getId();

    Long getShares();

    Double getAvgPrice();

    StocksDTO getStocks();

    interface StocksDTO {
        String getTicker();
        String getCompanyName();
        String getSector();
        String getExchange();
        String getIndustry();
    }
}
