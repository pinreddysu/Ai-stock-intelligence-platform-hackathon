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

    UserPortfolio findByIdAndUserIdAndDeletedAtIsNull(Long id, Long userId);

    boolean existsByPortfolioNameAndUserIdAndDeletedAtIsNull(String portfolioName, Long currentUserId);

    List<UserPortfolio> findAllByIdInAndUserIdAndDeletedAtIsNull(List<Long> portfoliosList, Long currentUserId);

    @Query("""
            SELECT up.portfolioName AS portfolioName, up.id AS id, up.description AS description,
            COUNT(ph.id) AS portfolioHoldings FROM UserPortfolio up LEFT OUTER JOIN PortfolioHoldings ph ON
            up.id = ph.userPortfolio.id WHERE ph.deletedAt IS NULL AND up.deletedAt is NULL AND
            up.user.id = :userId AND (LOWER(up.portfolioName) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR LOWER(up.description) LIKE LOWER(CONCAT('%', :keyword, '%'))) GROUP BY up.id
            """)
    List<PortfolioInfoDto> getPortfolioDetailsWithTotalHoldings(@Param("userId") Long userId, @Param("keyword") String keyword);
}