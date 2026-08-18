package com.ai_stock_intelligience.aiStockIntelligenceBackend.Dto.Response;

public class StockMoreDetailsDto {

    private Long id;

    private String ticker;

    private String companyName;

    private String sector;

    private String industry;

    private String exchange;

    private Double prices;

    private Double previousClose;

    private Double change;

    private Double changePercent;

    private String marketTimeStamp;

    private String lastUpdatedAt;

    public StockMoreDetailsDto(Long id, String ticker, String companyName,
                               String sector, String industry, String exchange,
                               Double prices, Double previousClose, Double change,
                               Double changePercent, String marketTimeStamp, String lastUpdatedAt) {
        this.id = id;
        this.ticker = ticker;
        this.companyName = companyName;
        this.sector = sector;
        this.industry = industry;
        this.exchange = exchange;
        this.prices = prices;
        this.previousClose = previousClose;
        this.change = change;
        this.changePercent = changePercent;
        this.marketTimeStamp = marketTimeStamp;
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public String getTicker() {
        return ticker;
    }

    public Long getId() {
        return id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getSector() {
        return sector;
    }

    public String getIndustry() {
        return industry;
    }

    public String getExchange() {
        return exchange;
    }

    public Double getPrices() {
        return prices;
    }

    public Double getPreviousClose() {
        return previousClose;
    }

    public Double getChange() {
        return change;
    }

    public Double getChangePercent() {
        return changePercent;
    }

    public String getMarketTimeStamp() {
        return marketTimeStamp;
    }

    public String getLastUpdatedAt() {
        return lastUpdatedAt;
    }
}
