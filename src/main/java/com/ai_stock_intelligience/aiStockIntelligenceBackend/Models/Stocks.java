package com.ai_stock_intelligience.aiStockIntelligenceBackend.Models;


import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "stocks")
@EntityListeners(AuditingEntityListener.class)
public class Stocks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ticker", nullable = false, unique = true)
    private String ticker;

    @Column(name = "companyname", nullable = false)
    private String companyName;

    @Column(name = "sector", nullable = false)
    private String sector;

    @Column(name = "industry", nullable = false)
    private String industry;

    @Column(name = "exchange", nullable = false)
    private String exchange;

    @Column(name = "createdat", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updatedat", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Stocks() {
    }

    public Stocks(Long id, String ticker, String companyName, String sector,
                  String exchange, String industry, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.ticker = ticker;
        this.companyName = companyName;
        this.sector = sector;
        this.exchange = exchange;
        this.industry = industry;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
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
