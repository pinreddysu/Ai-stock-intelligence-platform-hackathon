package com.ai_stock_intelligience.aiStockIntelligenceBackend.Models;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "userportfolio")
@EntityListeners(AuditingEntityListener.class)
public class UserPortfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "portfolioname", nullable = false)
    private String portfolioName;

    @Column(name = "description")
    private String description;

    @Column(name = "createdat", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updatedat", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(name = "deletedat")
    private LocalDateTime deletedAt;

    @ManyToOne(optional = false)
    @JoinColumn(name = "userid")
    private Users user;

    public UserPortfolio() {
    }

    public UserPortfolio(Long id, String portfolioName,
                         String description, LocalDateTime createdAt, LocalDateTime updatedAt,
                         LocalDateTime deletedAt) {
        this.id = id;
        this.portfolioName = portfolioName;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public String getPortfolioName() {
        return portfolioName;
    }

    public void setPortfolioName(String portfolioName) {
        this.portfolioName = portfolioName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }
}
