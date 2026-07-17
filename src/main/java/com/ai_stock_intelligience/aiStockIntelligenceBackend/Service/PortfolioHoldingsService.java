package com.ai_stock_intelligience.aiStockIntelligenceBackend.Service;

import com.ai_stock_intelligience.aiStockIntelligenceBackend.Dto.HoldingsDTO;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Models.PortfolioHoldings;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Repository.PortfolioHoldingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PortfolioHoldingsService {

    @Autowired
    private PortfolioHoldingsRepository portfolioHoldingsRepository;

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


    public boolean addHoldings(PortfolioHoldings portfolioHoldings) {
        if (portfolioHoldingsRepository.existsByPortfolioIdAndStockId(portfolioHoldings.getPortfolioId(),
                portfolioHoldings.getStockId())){
            return true;
        }
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

    public List<HoldingsDTO> getAllHoldings(PortfolioHoldings portfolioHoldings) {
        System.out.println(portfolioHoldings.getPortfolioId());
        return portfolioHoldingsRepository.findByPortfolioId(portfolioHoldings.getPortfolioId());
    }
}
