package com.ai_stock_intelligience.aiStockIntelligenceBackend.Repository;

import com.ai_stock_intelligience.aiStockIntelligenceBackend.Dto.HoldingsDTO;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Models.PortfolioHoldings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PortfolioHoldingsRepository extends JpaRepository<PortfolioHoldings, Long> {

    boolean existsByPortfolioIdAndStockId(Long portfolioId, Long stockId);

    List<HoldingsDTO> findByPortfolioId(Long id);

}
