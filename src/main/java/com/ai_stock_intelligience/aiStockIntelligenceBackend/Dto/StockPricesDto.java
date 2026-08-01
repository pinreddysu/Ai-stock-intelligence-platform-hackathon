package com.ai_stock_intelligience.aiStockIntelligenceBackend.Dto;


public class StockPricesDto {

    private String symbol;

    private Double price;

    private Double changePercentage;

    private Double change;

    private Double previousClose;

    private Long timestamp;

    public String getSymbol() {
        return symbol;
    }

    public Double getPrice() {
        return price;
    }

    public Double getChangePercentage() {
        return changePercentage;
    }

    public Double getChange() {
        return change;
    }

    public Double getPreviousClose() {
        return previousClose;
    }

    public Long getTimestamp() {
        return timestamp;
    }
}
