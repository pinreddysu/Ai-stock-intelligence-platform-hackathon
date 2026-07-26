package com.ai_stock_intelligience.aiStockIntelligenceBackend.Repository;

import com.ai_stock_intelligience.aiStockIntelligenceBackend.Dto.Response.PortfolioInfoDto;
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

    boolean existsByPortfolioNameAndUserIdAndDeletedAtIsNotNull(String portfolioName, Long userId);

    boolean existsByIdAndUserIdAndDeletedAtIsNull(Long id, Long userId);

    List<PortfolioInfoDto> findByUserIdAndDeletedAtIsNull(Long userId);

    UserPortfolio findByIdAndUserIdAndDeletedAtIsNull(Long id, Long userId);

    boolean existsByPortfolioNameAndUserIdAndDeletedAtIsNull(String portfolioName, Long currentUserId);
}