package com.ai_stock_intelligience.aiStockIntelligenceBackend.Repository;

import com.ai_stock_intelligience.aiStockIntelligenceBackend.Dto.Response.BasicStockDto;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Dto.Response.StocksDto;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Models.Stocks;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



@Repository
public interface StocksRepository extends JpaRepository<Stocks, Long> {

//    Page<BasicStockDto> findBy(Pageable pageable);

    @Query("""
    SELECT s.id AS id, s.ticker AS ticker, s.companyName AS companyName
    FROM Stocks s
    WHERE NOT EXISTS (
        SELECT ph
        FROM PortfolioHoldings ph
        WHERE ph.stocks.id = s.id
          AND ph.userPortfolio.id = :portfolioId
          AND ph.deletedAt IS NULL
    )
    AND (
        LOWER(s.ticker) LIKE LOWER(CONCAT('%', :keyword, '%'))
        OR LOWER(s.companyName) LIKE LOWER(CONCAT('%', :keyword, '%'))
        OR LOWER(s.exchange) LIKE LOWER(CONCAT('%', :keyword, '%'))
        OR LOWER(s.sector) LIKE LOWER(CONCAT('%', :keyword, '%'))
        OR LOWER(s.industry) LIKE LOWER(CONCAT('%', :keyword, '%'))
    )
"""
    )
    Page<BasicStockDto.StockList> getStocksList(@Param("keyword") String keyword, @Param("portfolioId") Long portfolioId,
                                      Pageable pageable);

    Stocks findByTicker(String ticker);

    @Query(
            """
            SELECT s FROM Stocks s
            WHERE LOWER(s.ticker) = LOWER(:ticker)"""
    )
    StocksDto findStockDetailsByTicker(@Param("ticker") String ticker);
}
