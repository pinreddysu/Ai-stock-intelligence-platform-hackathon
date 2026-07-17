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

    @Column(name = "portfolioid", nullable = false)
    private Long portfolioId;

    @Column(name = "stockid", nullable = false, insertable = false, updatable = false)
    private Long stockId;

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

    public PortfolioHoldings(Long id, Long portfolioId, Long stockId, Long shares,
                             Double avgPrice, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.portfolioId = portfolioId;
        this.stockId = stockId;
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

    public Long getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(Long portfolioId) {
        this.portfolioId = portfolioId;
    }

    public Long getStockId() {
        return stockId;
    }

    public void setStockId(Long stockId) {
        this.stockId = stockId;
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
}
