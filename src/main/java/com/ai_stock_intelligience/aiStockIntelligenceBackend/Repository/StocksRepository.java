package com.ai_stock_intelligience.aiStockIntelligenceBackend.Repository;

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

    Page<StocksDto> findBy(Pageable pageable);

    @Query(
            """
               SELECT s FROM Stocks s WHERE
               (LOWER(s.ticker) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
               LOWER(s.companyName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
               LOWER(s.exchange) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
               LOWER(s.sector) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
               LOWER(s.industry) LIKE LOWER(CONCAT('%', :keyword, '%')))"""
    )
    Page<StocksDto> getStocksList(@Param("keyword") String keyword, Pageable pageable);
}
