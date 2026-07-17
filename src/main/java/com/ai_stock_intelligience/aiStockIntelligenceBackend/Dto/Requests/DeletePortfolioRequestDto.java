package com.ai_stock_intelligience.aiStockIntelligenceBackend.Dto.Requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class DeletePortfolioRequestDto {

    @NotNull(message = "Portfolio Id is required")
    @Positive(message = "Portfolio Id should be greater than 0")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
