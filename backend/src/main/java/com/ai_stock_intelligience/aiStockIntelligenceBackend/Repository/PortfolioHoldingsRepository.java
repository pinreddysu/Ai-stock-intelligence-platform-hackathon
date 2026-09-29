package com.ai_stock_intelligience.aiStockIntelligenceBackend.Repository;

import com.ai_stock_intelligience.aiStockIntelligenceBackend.Dto.Response.PortfolioDetailsDto;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Models.PortfolioHoldings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface PortfolioHoldingsRepository extends JpaRepository<PortfolioHoldings, Long> {

    boolean existsByUserPortfolioIdAndStocksIdAndUserPortfolioUserIdAndDeletedAtIsNull(Long portfolioId, Long stockId, Long userId);

    PortfolioHoldings findByIdAndUserPortfolioUserIdAndDeletedAtIsNull(Long id, Long userId);

    List<PortfolioHoldings> findAllByIdInAndUserPortfolioUserIdAndDeletedAtIsNull(List<Long> portfolioHoldingsList, Long currentUserId);

    @Query("""
            SELECT ph.id AS holdingId,
            ph.stocks.id AS stockId,
            ph.stocks.ticker AS ticker,
            ph.stocks.companyName AS companyName,
            ph.stocks.exchange AS exchange,
            ph.stocks.sector AS sector,
            ph.stocks.industry AS industry,
            ph.shares AS shares,
            ph.avgPrice AS avgPrice,
            scp.prices AS price,
            scp.previousClose AS previousClose,
            scp.change AS dailyChange,
            scp.changePercentage AS dailyChangePercent,
            scp.marketTimeStamp AS marketTimeStamp,
            scp.updatedAt AS updatedAt
            FROM PortfolioHoldings ph LEFT OUTER JOIN
            StockCurrentPrices scp
            ON scp.stocks.id = ph.stocks.id
            WHERE ph.userPortfolio.id = :id
            AND ph.userPortfolio.user.id = :currentUserId
            AND ph.deletedAt IS NULL
            AND (LOWER(ph.stocks.ticker) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR LOWER(ph.stocks.companyName) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR LOWER(ph.stocks.exchange) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR LOWER(ph.stocks.sector) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR LOWER(ph.stocks.industry) LIKE LOWER(CONCAT('%', :keyword, '%')))
            ORDER BY ph.stocks.ticker ASC
            """)
    Page<PortfolioDetailsDto.HoldingsDto> getPortfolioHoldings(@Param("id") Long portfolioId,
                                                               @Param("keyword") String keyword,
                                                               Pageable pageable,
                                                               @Param("currentUserId") Long currentUserId);

    @Query("""
            SELECT ph.id AS holdingId,
            ph.stocks.id AS stockId,
            ph.stocks.ticker AS ticker,
            ph.stocks.companyName AS companyName,
            ph.stocks.exchange AS exchange,
            ph.stocks.sector AS sector,
            ph.stocks.industry AS industry,
            ph.shares AS shares,
            ph.avgPrice AS avgPrice,
            scp.prices AS price,
            scp.previousClose AS previousClose,
            scp.change AS dailyChange,
            scp.changePercentage AS dailyChangePercent,
            scp.marketTimeStamp AS marketTimeStamp,
            scp.updatedAt AS updatedAt
            FROM PortfolioHoldings ph LEFT OUTER JOIN
            StockCurrentPrices scp
            ON scp.stocks.id = ph.stocks.id
            WHERE ph.userPortfolio.id = :id
            AND ph.userPortfolio.user.id = :currentUserId
            AND ph.deletedAt IS NULL
            """)
    List<PortfolioDetailsDto.HoldingsDto> getPortfolioHoldings(@Param("id") Long portfolioId,
                                                               @Param("currentUserId") Long currentUserId);

    @Query("""
            SELECT ph.stocks.sector AS sector, SUM(ph.shares * scp.prices) AS sectorValue,
            SUM(ph.shares * scp.prices) / SUM(SUM(ph.shares * scp.prices)) OVER() * 100 AS allocationPercent
            FROM PortfolioHoldings ph INNER JOIN StockCurrentPrices scp ON
            ph.stocks.id = scp.stocks.id WHERE ph.userPortfolio.id = :portfolioId
            AND ph.userPortfolio.user.id = :currentUserId
            AND ph.deletedAt IS NULL GROUP BY ph.stocks.sector
            """)
    List<PortfolioDetailsDto.SectorAllocationDto> getSectorAllocation(@Param("currentUserId") Long currentUserId, @Param("portfolioId") Long portfolioId);

    @Query("""
            SELECT ph.stocks.ticker AS ticker, ph.stocks.companyName as companyName,
            SUM(ph.shares * scp.prices) AS sectorValue,
            SUM(ph.shares * scp.prices) / SUM(SUM(ph.shares * scp.prices)) OVER() * 100 AS allocationPercent,
            (SUM(ph.shares * scp.prices)-SUM(ph.shares * ph.avgPrice)) / SUM(ph.shares * ph.avgPrice) * 100
            AS stockPerformance
            FROM PortfolioHoldings ph INNER JOIN StockCurrentPrices scp ON
            ph.stocks.id = scp.stocks.id WHERE ph.userPortfolio.id = :portfolioId
            AND ph.userPortfolio.user.id = :currentUserId
            AND ph.deletedAt IS NULL GROUP BY ph.stocks.ticker, ph.stocks.companyName, ph.stocks.sector
            ORDER BY SUM(ph.shares * scp.prices) DESC
            """)
    List<PortfolioDetailsDto.MaxStockAllocation> getMaxStockAllocation(@Param("portfolioId") Long portfolioId, @Param("currentUserId") Long currentUserId);
}
