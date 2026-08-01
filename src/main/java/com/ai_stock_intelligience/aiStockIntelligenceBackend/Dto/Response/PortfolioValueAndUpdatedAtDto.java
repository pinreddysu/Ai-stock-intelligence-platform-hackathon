package com.ai_stock_intelligience.aiStockIntelligenceBackend.Dto.Response;

import java.time.LocalDateTime;

public class PortfolioValueAndUpdatedAtDto {

    private final Double prices;

    private final LocalDateTime updatedAt;

    private final LocalDateTime marketTimeStamp;

    public PortfolioValueAndUpdatedAtDto(Double prices, LocalDateTime updatedAt, LocalDateTime marketTimeStamp) {
        this.prices = prices;
        this.updatedAt = updatedAt;
        this.marketTimeStamp = marketTimeStamp;
    }

    public Double getPrices() {
        return prices;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public LocalDateTime getMarketTimeStamp() {
        return marketTimeStamp;
    }
}
