package com.ai_stock_intelligience.aiStockIntelligenceBackend.Models;

import jakarta.persistence.*;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "stockscurrentprices")
public class StockCurrentPrices {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name="stockid", unique = true)
    private Stocks stocks;

    @Column(name = "price")
    private Double prices;

    @Column(name = "previousclose")
    private Double previousClose;

    @Column(name = "change")
    private Double change;

    @Column(name = "changepercent")
    private Double changePercentage;

    @Column(name = "updatedat")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(name = "markettimestamp")
    private LocalDateTime marketTimeStamp;

    public StockCurrentPrices() {
    }

    public StockCurrentPrices(Long id, Stocks stocks, Double prices, Double previousClose,
                              Double change, Double changePercentage, LocalDateTime updatedAt,
                              LocalDateTime marketTimeStamp) {
        this.id = id;
        this.stocks = stocks;
        this.prices = prices;
        this.previousClose = previousClose;
        this.change = change;
        this.changePercentage = changePercentage;
        this.updatedAt = updatedAt;
        this.marketTimeStamp = marketTimeStamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Stocks getStocks() {
        return stocks;
    }

    public void setStocks(Stocks stocks) {
        this.stocks = stocks;
    }

    public Double getPrices() {
        return prices;
    }

    public void setPrices(Double prices) {
        this.prices = prices;
    }

    public Double getPreviousClose() {
        return previousClose;
    }

    public void setPreviousClose(Double previousClose) {
        this.previousClose = previousClose;
    }

    public Double getChange() {
        return change;
    }

    public void setChange(Double change) {
        this.change = change;
    }

    public Double getChangePercentage() {
        return changePercentage;
    }

    public void setChangePercentage(Double changePercentage) {
        this.changePercentage = changePercentage;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getMarketTimeStamp() {
        return marketTimeStamp;
    }

    public void setMarketTimeStamp(LocalDateTime marketTimeStamp) {
        this.marketTimeStamp = marketTimeStamp;
    }
}
