package com.ai_stock_intelligience.aiStockIntelligenceBackend.Repository;

import com.ai_stock_intelligience.aiStockIntelligenceBackend.Dto.Response.HoldingsDTO;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Models.PortfolioHoldings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface PortfolioHoldingsRepository extends JpaRepository<PortfolioHoldings, Long> {

    boolean existsByUserPortfolioIdAndStocksIdAndUserPortfolioUserIdAndDeletedAtIsNull(Long portfolioId, Long stockId, Long userId);

    List<HoldingsDTO> findByUserPortfolioIdAndUserPortfolioUserId(Long portfolioId, Long userId );

    PortfolioHoldings findByIdAndUserPortfolioUserIdAndDeletedAtIsNull(Long id, Long userId);

    List<PortfolioHoldings> findAllByIdInAndUserPortfolioUserIdAndDeletedAtIsNull(List<Long> portfolioHoldingsList, Long currentUserId);
}
