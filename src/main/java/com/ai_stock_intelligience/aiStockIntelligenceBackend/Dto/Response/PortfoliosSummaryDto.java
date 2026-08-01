package com.ai_stock_intelligience.aiStockIntelligenceBackend.Dto.Response;

import java.time.LocalDateTime;

public interface PortfoliosSummaryDto {

    String getPortfolioName();
    Long getPortfolioId();
    String getDescription();
    Long getStockId();
    Double getShares();
    Double getAvgPrice();
    LocalDateTime getUpdatedAt();
    String getTicker();
    Double getPrice();
    LocalDateTime getMarketTimeStamp();
}
