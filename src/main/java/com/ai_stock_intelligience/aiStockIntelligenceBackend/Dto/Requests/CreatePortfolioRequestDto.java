package com.ai_stock_intelligience.aiStockIntelligenceBackend.Dto.Requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreatePortfolioRequestDto {

    @NotBlank(message = "Portfolio name is required")
    @Size(min = 2, max = 50, message = "Portfolio name must be between 2 and 50 characters")
    private String portfolioName;

    @Size(max = 255)
    private String description;

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
}
