package com.ai_stock_intelligience.aiStockIntelligenceBackend.Repository;

import com.ai_stock_intelligience.aiStockIntelligenceBackend.Dto.Response.HoldingsDTO;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Models.PortfolioHoldings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface PortfolioHoldingsRepository extends JpaRepository<PortfolioHoldings, Long> {

    boolean existsByUserPortfolioIdAndStocksIdAndUserPortfolioUserIdAndDeletedAtIsNull(Long portfolioId, Long stockId, Long userId);

    List<HoldingsDTO> findByUserPortfolioIdAndUserPortfolioUserId(Long portfolioId, Long userId );

    PortfolioHoldings findByIdAndUserPortfolioUserIdAndDeletedAtIsNull(Long id, Long userId);

    List<PortfolioHoldings> findAllByIdInAndUserPortfolioUserIdAndDeletedAtIsNull(List<Long> portfolioHoldingsList, Long currentUserId);

    @Query(
            """
            SELECT p FROM PortfolioHoldings p INNER JOIN p.userPortfolio up
            WHERE (LOWER(p.stocks.ticker) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR LOWER(p.stocks.companyName) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR LOWER(p.stocks.exchange) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR LOWER(p.stocks.sector) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR LOWER(p.stocks.industry) LIKE LOWER(CONCAT('%', :keyword, '%')))
            AND up.user.id = :currentUserId AND up.id = :portfolioId AND
            up.deletedAt IS NULL AND p.deletedAt IS NULL"""
    )
    List<HoldingsDTO> filterSearchHoldings(@Param("portfolioId") Long portfolioId, @Param("keyword") String keyword,
                                           @Param("currentUserId") Long currentUserId);
}
