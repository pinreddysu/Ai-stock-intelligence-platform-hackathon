package com.ai_stock_intelligience.aiStockIntelligenceBackend.Service;

import com.ai_stock_intelligience.aiStockIntelligenceBackend.Dto.HoldingsDTO;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Dto.Requests.AddHoldingsToPortfolioDto;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Dto.Requests.GetPortfolioHoldingsDto;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Models.PortfolioHoldings;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Models.Stocks;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Models.UserPortfolio;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Repository.PortfolioHoldingsRepository;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Repository.StocksRepository;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Repository.UserPortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public boolean deleteHoldings(PortfolioHoldings portfolioHoldings) {
        if (portfolioHoldingsRepository.existsById(portfolioHoldings.getId())){
            portfolioHoldingsRepository.delete(portfolioHoldings);
//            portfolioHoldingsRepository.deleteById(portfolioHoldings.getId());
            return true;
        }
        return false;
    }

    public void deleteListOfHoldings(List<PortfolioHoldings> portfolioHoldingsList) {
        portfolioHoldingsRepository.deleteAll(portfolioHoldingsList);
    }


    public boolean addHoldings(AddHoldingsToPortfolioDto portfolioHoldingsDetails) {
        //JWT user retrieval
        Long currentUserId = authenticationService.getCurrentUserId();
        if (portfolioHoldingsRepository.existsByUserPortfolioIdAndStocksIdAndUserPortfolioUserId(portfolioHoldingsDetails.getPortfolioId(),
                portfolioHoldingsDetails.getStockId(), currentUserId)){
            return true;
        }

        UserPortfolio userPortfolio = userPortfolioRepository.findById(portfolioHoldingsDetails.getPortfolioId())
                .orElseThrow();
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

    public PortfolioHoldings updateHoldings(PortfolioHoldings portfolioHoldings) {
        PortfolioHoldings existingPortfolioHoldings = portfolioHoldingsRepository
                .findById(portfolioHoldings.getId())
                .orElse(null);

        if (existingPortfolioHoldings != null) {
            if(portfolioHoldings.getAvgPrice() >= 0) {
                existingPortfolioHoldings.setAvgPrice(portfolioHoldings.getAvgPrice());
            }
            if(portfolioHoldings.getShares() >= 0) {
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
