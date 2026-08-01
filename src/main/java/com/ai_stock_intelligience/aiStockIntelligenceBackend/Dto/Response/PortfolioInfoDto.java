package com.ai_stock_intelligience.aiStockIntelligenceBackend.Dto.Response;

public class PortfolioInfoDto {

    private final Long id;

    private final String portfolioName;

    private final String description;

    private Double portfolioValue;

    private String lastUpdatedAt;

    private String marketTimeStamp;

    private Long portfolioHoldings;

    public PortfolioInfoDto(String portfolioName, Long id, String description) {
        this.portfolioName = portfolioName;
        this.id = id;
        this.description = description;
    }

    public String getPortfolioName() {
        return portfolioName;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
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
