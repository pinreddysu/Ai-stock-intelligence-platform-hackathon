package com.ai_stock_intelligience.aiStockIntelligenceBackend.Dto.Response;

import org.springframework.data.domain.Page;

import java.util.List;

public class BasicStockDto {

    private Long totalElements;

    private List<StockList> stockLists;

    public static class StockList{
        private Long id;

        private String ticker;

        private String companyName;

        public StockList(Long id, String ticker, String companyName) {
            this.id = id;
            this.ticker = ticker;
            this.companyName = companyName;
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
    }

    public List<StockList> getStockLists() {
        return stockLists;
    }

    public void setStockLists(List<StockList> stockLists) {
        this.stockLists = stockLists;
    }

    public Long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }
}
