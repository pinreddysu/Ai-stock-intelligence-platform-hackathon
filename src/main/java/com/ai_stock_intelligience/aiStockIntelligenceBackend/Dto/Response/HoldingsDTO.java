package com.ai_stock_intelligience.aiStockIntelligenceBackend.Dto.Response;

public interface HoldingsDTO {

    Long getId();

    Long getShares();

    Double getAvgPrice();

    String getStocksTicker();

    String getStocksCompanyName();

    String getStocksSector();

    String getStocksExchange();

    String getStocksIndustry();
}
