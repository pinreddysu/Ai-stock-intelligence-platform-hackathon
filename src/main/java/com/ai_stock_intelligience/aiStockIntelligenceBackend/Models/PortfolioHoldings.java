package com.ai_stock_intelligience.aiStockIntelligenceBackend.Models;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Table(name = "portfolioholdings")
@EntityListeners(AuditingEntityListener.class)
@Entity
public class PortfolioHoldings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "shares", nullable = false)
    private Long shares;

    @Column(name = "avgprice", nullable = false)
    private Double avgPrice;

    @Column(name = "createdat", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updatedat", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne(optional = false)
    @JoinColumn(name = "stockid")
    private Stocks stocks;

    @ManyToOne(optional = false)
    @JoinColumn(name = "portfolioid")
    private UserPortfolio userPortfolio;

    public PortfolioHoldings() {
    }

    public PortfolioHoldings(Long id, Long shares,
                             Double avgPrice, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.shares = shares;
        this.avgPrice = avgPrice;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getShares() {
        return shares;
    }

    public void setShares(Long shares) {
        this.shares = shares;
    }

    public Double getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(Double avgPrice) {
        this.avgPrice = avgPrice;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public UserPortfolio getUserPortfolio() {
        return userPortfolio;
    }

    public void setUserPortfolio(UserPortfolio userPortfolio) {
        this.userPortfolio = userPortfolio;
    }

    public Stocks getStocks() {
        return stocks;
    }

    public void setStocks(Stocks stocks) {
        this.stocks = stocks;
    }
}
