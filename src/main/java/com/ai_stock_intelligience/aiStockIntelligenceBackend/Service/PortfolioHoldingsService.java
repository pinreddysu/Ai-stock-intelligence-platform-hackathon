package com.ai_stock_intelligience.aiStockIntelligenceBackend.Service;

import com.ai_stock_intelligience.aiStockIntelligenceBackend.Dto.Requests.*;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Dto.Response.HoldingsDTO;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Models.PortfolioHoldings;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Models.Stocks;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Models.UserPortfolio;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Repository.PortfolioHoldingsRepository;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Repository.StocksRepository;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Repository.UserPortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PortfolioHoldingsService {

    @Autowired
    private PortfolioHoldingsRepository portfolioHoldingsRepository;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserPortfolioRepository userPortfolioRepository;

    @Autowired
    private StocksRepository stocksRepository;


    public boolean deleteHoldings(RemoveHoldingRequestDto portfolioHoldings) {
        PortfolioHoldings existingPortfolioHoldings = portfolioHoldingsRepository
                .findByIdAndUserPortfolioUserIdAndDeletedAtIsNull(portfolioHoldings.getId(),
                        authenticationService.getCurrentUserId());
        if(existingPortfolioHoldings != null
                && existingPortfolioHoldings.getDeletedAt() == null){
                    existingPortfolioHoldings.setDeletedAt(LocalDateTime.now());
                    portfolioHoldingsRepository.save(existingPortfolioHoldings);
                    return true;
            }
            return false;
    }

    public boolean deleteListOfHoldings(RemoveHoldingsListRequestDto portfolioHoldingsList) {
        List<PortfolioHoldings> listOfHoldings = portfolioHoldingsRepository
                .findAllByIdInAndUserPortfolioUserIdAndDeletedAtIsNull(portfolioHoldingsList.getIds(), authenticationService.getCurrentUserId());

        if (listOfHoldings.isEmpty()){
            return false;
        }

        for (PortfolioHoldings holding: listOfHoldings){
            holding.setDeletedAt(LocalDateTime.now());
        }

        portfolioHoldingsRepository.saveAll(listOfHoldings);
        return true;
    }


    public boolean addHoldings(AddHoldingsToPortfolioDto portfolioHoldingsDetails) {
        //JWT user retrieval
        Long currentUserId = authenticationService.getCurrentUserId();
        if (portfolioHoldingsRepository
                .existsByUserPortfolioIdAndStocksIdAndUserPortfolioUserIdAndDeletedAtIsNull
                        (portfolioHoldingsDetails.getPortfolioId(),
                portfolioHoldingsDetails.getStockId(), currentUserId)){
            return true;
        }

        UserPortfolio userPortfolio = userPortfolioRepository.findByIdAndUserIdAndDeletedAtIsNull(
                portfolioHoldingsDetails.getPortfolioId(), currentUserId);
        Stocks stocks = stocksRepository.findById(portfolioHoldingsDetails.getStockId())
                .orElseThrow();
        PortfolioHoldings portfolioHoldings = new PortfolioHoldings();
        portfolioHoldings.setUserPortfolio(userPortfolio);
        portfolioHoldings.setShares(portfolioHoldingsDetails.getShares());
        portfolioHoldings.setAvgPrice(portfolioHoldingsDetails.getAvgPrice());
        portfolioHoldings.setStocks(stocks);
        portfolioHoldingsRepository.save(portfolioHoldings);
        return false;
    }

    public PortfolioHoldings updateHoldings(UpdateHoldingRequestDto portfolioHoldings) {
        PortfolioHoldings existingPortfolioHoldings = portfolioHoldingsRepository
                .findByIdAndUserPortfolioUserIdAndDeletedAtIsNull(portfolioHoldings.getId(),
                        authenticationService.getCurrentUserId());

        if (existingPortfolioHoldings != null) {
            if(portfolioHoldings.getAvgPrice() != null) {
                existingPortfolioHoldings.setAvgPrice(portfolioHoldings.getAvgPrice());
            }
            if(portfolioHoldings.getShares() != null) {
                existingPortfolioHoldings.setShares(portfolioHoldings.getShares());
            }
            return portfolioHoldingsRepository.save(existingPortfolioHoldings);
        }
        return null;
    }

    public List<HoldingsDTO> getAllHoldings(GetPortfolioHoldingsDto portfolioHoldings) {
        Long currentUserId = authenticationService.getCurrentUserId();
        return portfolioHoldingsRepository.findByUserPortfolioIdAndUserPortfolioUserId(
                portfolioHoldings.getPortfolioId(), currentUserId);
    }
}
