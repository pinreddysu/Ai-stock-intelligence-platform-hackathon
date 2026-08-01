package com.ai_stock_intelligience.aiStockIntelligenceBackend.Dto.Response;

public class PortfolioValueUpdateDto {

    private Long id;

    private Double portfolioValue;

    private String lastUpdatedAt;

    private String marketTimeStamp;

    private Long portfolioHoldings;

    public PortfolioValueUpdateDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPortfolioValue() {
        return portfolioValue;
    }

    public void setPortfolioValue(Double portfolioValue) {
        this.portfolioValue = portfolioValue;
    }

    public String getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public void setLastUpdatedAt(String lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public String getMarketTimeStamp() {
        return marketTimeStamp;
    }

    public void setMarketTimeStamp(String marketTimeStamp) {
        this.marketTimeStamp = marketTimeStamp;
    }

    public Long getPortfolioHoldings() {
        return portfolioHoldings;
    }

    public void setPortfolioHoldings(Long portfolioHoldings) {
        this.portfolioHoldings = portfolioHoldings;
    }
}
