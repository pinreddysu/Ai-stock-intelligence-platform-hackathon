package com.ai_stock_intelligience.aiStockIntelligenceBackend.Dto.Requests;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public class DeletePortfoliosListRequestDto {

    @NotEmpty(message = "Please provide a list")
    private List<Long> ids;

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }
}
