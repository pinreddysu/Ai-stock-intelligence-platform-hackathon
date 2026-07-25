package com.ai_stock_intelligience.aiStockIntelligenceBackend.Repository;

import com.ai_stock_intelligience.aiStockIntelligenceBackend.Models.Users;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Projections.AuthenticationUserIdProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Users findByEmail(String email);

    AuthenticationUserIdProjection findProjectedByEmail(String email);
}
