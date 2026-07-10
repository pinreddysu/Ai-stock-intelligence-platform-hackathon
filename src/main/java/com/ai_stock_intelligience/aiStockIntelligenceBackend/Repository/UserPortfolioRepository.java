package com.ai_stock_intelligience.aiStockIntelligenceBackend.Repository;

import com.ai_stock_intelligience.aiStockIntelligenceBackend.Models.UserPortfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserPortfolioRepository extends JpaRepository<UserPortfolio, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE UserPortfolio u SET u.deletedAt = CURRENT_TIMESTAMP WHERE u.id = :id")
    Long deletePortfolio(@Param("id") Long id);

    boolean existsByPortfolioNameAndUserIdAndDeletedAt(String portfolioName, Long userId, LocalDateTime deletedAt);

    boolean existsByIdAndDeletedAtIsNull(Long id);

    List<UserPortfolio> findByUserIdAndDeletedAtIsNull(Long userId);

    UserPortfolio findByIdAndDeletedAtIsNull(Long id);

}