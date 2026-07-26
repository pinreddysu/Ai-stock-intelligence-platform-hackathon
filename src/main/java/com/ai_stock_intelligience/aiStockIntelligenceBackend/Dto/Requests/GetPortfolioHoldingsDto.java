package com.ai_stock_intelligience.aiStockIntelligenceBackend.Dto.Requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class GetPortfolioHoldingsDto {

    @NotNull(message = "Portfolio id is required")
    @Positive(message = "Portfolio id must be greater than 0")
    private Long portfolioId;

    public Long getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(Long portfolioId) {
        this.portfolioId = portfolioId;
    }
}
