package com.ai_stock_intelligience.aiStockIntelligenceBackend.Dto.Requests;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public class AddHoldingsToPortfolioDto {

    @NotNull(message = "Portfolio id is required")
    @Positive(message = "Portfolio id must be greater than 0")
    private Long portfolioId;

    @NotNull(message = "Selecting a stock is required")
    @Positive(message = "Select a valid stock")
    private Long stockId;

    @PositiveOrZero(message = "Shares has to be greater than or equal to 0")
    private Double shares;

    @PositiveOrZero(message = "Average Price has to be greater than or equal to 0")
    private Double avgPrice;

    @NotEmpty(message = "Stock Ticker is required")
    private String ticker;

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public Long getStockId() {
        return stockId;
    }

    public void setStockId(Long stockId) {
        this.stockId = stockId;
    }

    public Long getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(Long portfolioId) {
        this.portfolioId = portfolioId;
    }

    public Double getShares() {
        return shares;
    }

    public void setShares(Double shares) {
        this.shares = shares;
    }

    public Double getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(Double avgPrice) {
        this.avgPrice = avgPrice;
    }
}
