package com.ai_stock_intelligience.aiStockIntelligenceBackend.Service;

import com.ai_stock_intelligience.aiStockIntelligenceBackend.Dto.Requests.CreatePortfolioRequestDto;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Dto.Requests.DeletePortfolioRequestDto;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Dto.Requests.UpdatePortfolioRequestDto;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Models.UserPortfolio;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Repository.UserPortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserPortfolioService {

    @Autowired
    private UserPortfolioRepository userPortfolioRepository;

    public boolean createPortfolio(CreatePortfolioRequestDto userPortfolio) {
        Long currentUserId = null; //JWT
        if (userPortfolioRepository.existsByPortfolioNameAndUserIdAndDeletedAtIsNotNull(
                userPortfolio.getPortfolioName(), currentUserId)){
            return true;
        }
        UserPortfolio portfolio = new UserPortfolio();
        portfolio.setUserId(currentUserId);
        portfolio.setPortfolioName(userPortfolio.getPortfolioName());
        portfolio.setDescription(userPortfolio.getDescription());
        userPortfolioRepository.save(portfolio);
        return false;
    }

    public Long deletePortfolio(DeletePortfolioRequestDto userPortfolio){
        Long currentUserId = null; //JWT
        if (userPortfolioRepository.existsByIdAndUserIdAndDeletedAtIsNull(userPortfolio.getId(), currentUserId)){
            return userPortfolioRepository.deletePortfolio(userPortfolio.getId());
        }
        return null;
    }

    public List<UserPortfolio> getListOfPortfoliosByUser() {
        Long currentUserId = null; //JWT
        return userPortfolioRepository.findByUserIdAndDeletedAtIsNull(currentUserId);
    }


    public UserPortfolio updatePortfolioDetails(UpdatePortfolioRequestDto userPortfolio) {

        Long currentUserId = null; //JWt
        UserPortfolio currentPortfolioInfo = userPortfolioRepository.findByIdAndUserIdAndDeletedAtIsNull(userPortfolio.getId(), currentUserId);

        if (currentPortfolioInfo != null) {
            if (userPortfolio.getPortfolioName() != null) {
                currentPortfolioInfo.setPortfolioName(userPortfolio.getPortfolioName());
            }

            if (userPortfolio.getDescription() != null) {
                currentPortfolioInfo.setDescription(userPortfolio.getDescription());
            }

            return userPortfolioRepository.save(currentPortfolioInfo);
        }
        else {
            return null;
        }
    }
}
