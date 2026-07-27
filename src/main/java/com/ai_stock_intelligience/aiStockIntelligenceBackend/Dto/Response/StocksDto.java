package com.ai_stock_intelligience.aiStockIntelligenceBackend.Dto.Response;

public interface StocksDto {

    Long getId();

    String getTicker();

    String getCompanyName();

    String getSector();

    String getExchange();

    String getIndustry();
}
