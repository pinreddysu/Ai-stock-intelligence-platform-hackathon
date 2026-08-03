package com.ai_stock_intelligience.aiStockIntelligenceBackend.Dto.Response;

import java.time.LocalDateTime;
import java.util.List;

public class PortfolioDetailsDto {

    private final Long id;

    private final String portfolioName;

    private final String description;

    private Double portfolioValue;

    private String lastUpdatedAt;

    private String marketTimeStamp;

    private Long portfolioHoldings;

    private Double portfolioInvested;

    private Double gainOrLoss;

    private Double gainOrLossPercent;

    private List<HoldingsDto> holdingsDto;

    public PortfolioDetailsDto(String portfolioName, Long id, String description) {
        this.portfolioName = portfolioName;
        this.id = id;
        this.description = description;
    }

    public static class HoldingsDto{
        private Long holdingId;
        private Long stockId;
        private String ticker;
        private String companyName;
        private String exchange;
        private String sector;
        private String industry;
        private Double shares;
        private Double avgPrice;
        private Double price;
        private Double previousClose;
        private Double dailyChange;
        private Double dailyChangePercent;
        private LocalDateTime marketTimeStamp;
        private LocalDateTime updatedAt;
        private Double currentValue;
        private Double investedAmount;
        private Double gainOrLoss;
        private Double gainOrLossPercent;
        private Double allocation;

        public HoldingsDto(Long holdingId, Long stockId,
                           String ticker, String companyName,
                           String exchange, String sector,
                           String industry, Double shares,
                           Double avgPrice, Double price,
                           Double previousClose, Double dailyChange,
                           Double dailyChangePercent, LocalDateTime marketTimeStamp,
                           LocalDateTime  updatedAt) {
            this.holdingId = holdingId;
            this.stockId = stockId;
            this.ticker = ticker.trim();
            this.companyName = companyName;
            this.exchange = exchange;
            this.sector = sector;
            this.industry = industry;
            this.shares = shares;
            this.avgPrice = avgPrice;
            this.price = price;
            this.previousClose = previousClose;
            this.dailyChange = dailyChange;
            this.dailyChangePercent = dailyChangePercent;
            this.marketTimeStamp = marketTimeStamp;
            this.updatedAt = updatedAt;
        }

        public Double getAvgPrice() {
            return avgPrice;
        }

        public String getTicker() {
            return ticker;
        }

        public Double getPrice() {
            return price;
        }

        public Double getShares() {
            return shares;
        }

        public LocalDateTime getUpdatedAt() {
            return updatedAt;
        }

        public LocalDateTime getMarketTimeStamp() {
            return marketTimeStamp;
        }

        public Double getDailyChangePercent() {
            return dailyChangePercent;
        }

        public Double getDailyChange() {
            return dailyChange;
        }

        public Double getPreviousClose() {
            return previousClose;
        }

        public String getIndustry() {
            return industry;
        }

        public String getSector() {
            return sector;
        }

        public String getExchange() {
            return exchange;
        }

        public String getCompanyName() {
            return companyName;
        }

        public Long getStockId() {
            return stockId;
        }

        public Long getHoldingId() {
            return holdingId;
        }

        public Double getGainOrLossPercent() {
            return gainOrLossPercent;
        }

        public void setGainOrLossPercent(Double gainOrLossPercent) {
            this.gainOrLossPercent = gainOrLossPercent;
        }

        public Double getGainOrLoss() {
            return gainOrLoss;
        }

        public void setGainOrLoss(Double gainOrLoss) {
            this.gainOrLoss = gainOrLoss;
        }

        public Double getInvestedAmount() {
            return investedAmount;
        }

        public void setInvestedAmount(Double investedAmount) {
            this.investedAmount = investedAmount;
        }

        public Double getCurrentValue() {
            return currentValue;
        }

        public void setCurrentValue(Double currentValue) {
            this.currentValue = currentValue;
        }

        public Double getAllocation() {
            return allocation;
        }

        public void setAllocation(Double allocation) {
            this.allocation = allocation;
        }
    }

    public List<HoldingsDto> getHoldings() {
        return holdingsDto;
    }

    public void setHoldingsDto(List<HoldingsDto> holdingsDto) {
        this.holdingsDto = holdingsDto;
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

    public Double getPortfolioInvested() {
        return portfolioInvested;
    }

    public void setPortfolioInvested(Double portfolioInvested) {
        this.portfolioInvested = portfolioInvested;
    }

    public Double getGainOrLoss() {
        return gainOrLoss;
    }

    public void setGainOrLoss(Double gainOrLoss) {
        this.gainOrLoss = gainOrLoss;
    }

    public Double getGainOrLossPercent() {
        return gainOrLossPercent;
    }

    public void setGainOrLossPercent(Double gainOrLossPercent) {
        this.gainOrLossPercent = gainOrLossPercent;
    }
}
