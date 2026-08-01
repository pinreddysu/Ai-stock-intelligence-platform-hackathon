package com.ai_stock_intelligience.aiStockIntelligenceBackend.Repository;

import com.ai_stock_intelligience.aiStockIntelligenceBackend.Dto.Response.PortfolioInfoDto;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Dto.Response.PortfoliosSummaryDto;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Models.UserPortfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
public interface UserPortfolioRepository extends JpaRepository<UserPortfolio, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE UserPortfolio u SET u.deletedAt = CURRENT_TIMESTAMP WHERE u.id = :id")
    Long deletePortfolio(@Param("id") Long id);

    List<PortfolioInfoDto> findByUserIdAndDeletedAtIsNull(Long userId);

    UserPortfolio findByIdAndUserIdAndDeletedAtIsNull(Long id, Long userId);

    boolean existsByPortfolioNameAndUserIdAndDeletedAtIsNull(String portfolioName, Long currentUserId);

    List<UserPortfolio> findAllByIdInAndUserIdAndDeletedAtIsNull(List<Long> portfoliosList, Long currentUserId);

    @Query(
            """
                    SELECT u FROM UserPortfolio u WHERE LOWER(u.portfolioName)
                    LIKE LOWER(CONCAT('%', :keyword, '%')) AND u.user.id = :currentUserId
                    AND u.deletedAt IS NULL"""
    )
    List<PortfolioInfoDto> filterSearchPortfoliosByNameByUser(@Param("keyword") String keyword,
                                                              @Param("currentUserId") Long currentUserId);

    @Query("""
            SELECT
                up.portfolioName AS portfolioName,
                up.id AS portfolioId,
                up.description AS description,
                ph.shares AS shares,
                ph.avgPrice AS avgPrice,
                scp.updatedAt AS updatedAt,
                ph.stocks.ticker AS ticker,
                scp.prices AS price,
                scp.marketTimeStamp as marketTimeStamp
            FROM UserPortfolio up
            INNER JOIN PortfolioHoldings ph
            ON up.id = ph.userPortfolio.id
            INNER JOIN Stocks s
            ON ph.stocks.id = s.id
            LEFT OUTER JOIN StockCurrentPrices scp
                ON scp.stocks.id = s.id
            WHERE up.user.id = :currentUserId
            AND up.id = :id
            AND up.deletedAt IS NULL
            """)
    List<PortfoliosSummaryDto> portfolioSummary(@Param("currentUserId") Long currentUserId,
                                                @Param("id") Long id);
}