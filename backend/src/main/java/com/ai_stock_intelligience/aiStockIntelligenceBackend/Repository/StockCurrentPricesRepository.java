package com.ai_stock_intelligience.aiStockIntelligenceBackend.Repository;

import com.ai_stock_intelligience.aiStockIntelligenceBackend.Models.StockCurrentPrices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockCurrentPricesRepository extends JpaRepository<StockCurrentPrices, Long> {

    StockCurrentPrices findByStocksId(Long id);
}
