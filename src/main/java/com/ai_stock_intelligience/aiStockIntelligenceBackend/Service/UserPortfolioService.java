package com.ai_stock_intelligience.aiStockIntelligenceBackend.Service;

import com.ai_stock_intelligience.aiStockIntelligenceBackend.Dto.Requests.CreatePortfolioRequestDto;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Dto.Requests.DeletePortfolioRequestDto;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Dto.Requests.DeletePortfoliosListRequestDto;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Dto.Requests.UpdatePortfolioRequestDto;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Dto.Response.PortfolioInfoDto;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Models.UserPortfolio;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Models.Users;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Repository.UserPortfolioRepository;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserPortfolioService {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserPortfolioRepository userPortfolioRepository;

    @Autowired
    private UserRepository userRepository;

    public boolean createPortfolio(CreatePortfolioRequestDto userPortfolio) {
        Long currentUserId = authenticationService.getCurrentUserId(); //JWt
        if (userPortfolioRepository.existsByPortfolioNameAndUserIdAndDeletedAtIsNull(
                userPortfolio.getPortfolioName(), currentUserId)){
            return true;
        }
        Users users = userRepository.findById(currentUserId)
                .orElseThrow();
        UserPortfolio portfolio = new UserPortfolio();
        portfolio.setUser(users);
        portfolio.setPortfolioName(userPortfolio.getPortfolioName());
        portfolio.setDescription(userPortfolio.getDescription());
        userPortfolioRepository.save(portfolio);
        return false;
    }

    public boolean deletePortfolio(DeletePortfolioRequestDto userPortfolio){
        Long currentUserId = authenticationService.getCurrentUserId(); //JWT

        UserPortfolio portfolio = userPortfolioRepository.findByIdAndUserIdAndDeletedAtIsNull(userPortfolio.getId(), currentUserId);

        if (portfolio == null){
            return false;
        }

        portfolio.setDeletedAt(LocalDateTime.now());
        userPortfolioRepository.save(portfolio);
        return true;
    }

    public List<PortfolioInfoDto> getListOfPortfoliosByUser() {
        Long currentUserId = authenticationService.getCurrentUserId(); //JWt
        return userPortfolioRepository.findByUserIdAndDeletedAtIsNull(currentUserId);
    }


    public UserPortfolio updatePortfolioDetails(UpdatePortfolioRequestDto userPortfolio) {
        Long currentUserId = authenticationService.getCurrentUserId(); //JWt get current user that sent the token
        UserPortfolio currentPortfolioInfo = userPortfolioRepository.findByIdAndUserIdAndDeletedAtIsNull(userPortfolio.getId(), currentUserId);

        if (currentPortfolioInfo != null) {
            if (userPortfolio.getPortfolioName() != null &&
                    !userPortfolioRepository.existsByPortfolioNameAndUserIdAndDeletedAtIsNull(
                    userPortfolio.getPortfolioName(), currentUserId) &&
                    !userPortfolio.getPortfolioName().equalsIgnoreCase(currentPortfolioInfo.getPortfolioName())) {
                currentPortfolioInfo.setPortfolioName(userPortfolio.getPortfolioName());
            }

            if (userPortfolio.getDescription() != null) {
                currentPortfolioInfo.setDescription(
                        userPortfolio.getDescription().isBlank() ? null : userPortfolio.getDescription());
            }

            return userPortfolioRepository.save(currentPortfolioInfo);
        }
        else {
            return null;
        }
    }

    public boolean deletePorfoliosList(DeletePortfoliosListRequestDto portfoliosList) {
        Long currentUserId = authenticationService.getCurrentUserId(); //JWT
        List<UserPortfolio> existingPortfolio = userPortfolioRepository.findAllByIdInAndUserIdAndDeletedAtIsNull(
                portfoliosList.getIds(), currentUserId);
        if (existingPortfolio != null){
            for (UserPortfolio portfolio: existingPortfolio){
                portfolio.setDeletedAt(LocalDateTime.now());
            }
            userPortfolioRepository.saveAll(existingPortfolio);
            return true;
        }
        return false;
    }
}
