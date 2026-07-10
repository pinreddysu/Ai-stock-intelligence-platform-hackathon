package com.ai_stock_intelligience.aiStockIntelligenceBackend.Service;

import com.ai_stock_intelligience.aiStockIntelligenceBackend.Models.UserPortfolio;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Repository.UserPortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserPortfolioService {

    @Autowired
    private UserPortfolioRepository userPortfolioRepository;

    public boolean createPortfolio(UserPortfolio userPortfolio) {
        if (userPortfolioRepository.existsByPortfolioNameAndUserIdAndDeletedAt(
                userPortfolio.getPortfolioName(), userPortfolio.getUserId(), userPortfolio.getDeletedAt())){
            return true;
        }
        userPortfolioRepository.save(userPortfolio);
        return false;
    }

    public Long deletePortfolio(UserPortfolio userPortfolio){
        if (userPortfolioRepository.existsByIdAndDeletedAtIsNull(userPortfolio.getId())){
            return userPortfolioRepository.deletePortfolio(userPortfolio.getId());
        }
        return null;
    }

    public List<UserPortfolio> getListOfPortfoliosByUser(UserPortfolio userPortfolio) {
        return userPortfolioRepository.findByUserIdAndDeletedAtIsNull(userPortfolio.getUserId());
    }


    public UserPortfolio updatePortfolioDetails(UserPortfolio userPortfolio) {

        UserPortfolio currentPortfolioInfo = userPortfolioRepository.findByIdAndDeletedAtIsNull(userPortfolio.getId());

        if (userPortfolio.getPortfolioName() != null) {
            currentPortfolioInfo.setPortfolioName(userPortfolio.getPortfolioName());
        }

        if (userPortfolio.getDescription() != null) {
            currentPortfolioInfo.setDescription(userPortfolio.getDescription());
        }

        return userPortfolioRepository.save(currentPortfolioInfo);
    }
}
