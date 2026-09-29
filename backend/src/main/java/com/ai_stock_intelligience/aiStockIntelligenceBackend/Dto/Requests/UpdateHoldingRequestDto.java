package com.ai_stock_intelligience.aiStockIntelligenceBackend.Dto.Requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class UpdateHoldingRequestDto {

    @NotNull(message = "Holding Id is required")
    @Positive(message = "Holding Id should be greater than 0")
    private Long id;

    @Positive(message = "Shares have to be greater than 0")
    private Double shares;

    @Positive(message = "Price has to be greater than 0")
    private Double avgPrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(Double avgPrice) {
        this.avgPrice = avgPrice;
    }

    public Double getShares() {
        return shares;
    }

    public void setShares(Double shares) {
        this.shares = shares;
    }
}
