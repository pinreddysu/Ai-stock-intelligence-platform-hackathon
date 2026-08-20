package com.ai_stock_intelligience.aiStockIntelligenceBackend.Service;

import com.ai_stock_intelligience.aiStockIntelligenceBackend.Dto.Requests.*;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Dto.Response.PortfolioDetailsDto;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Dto.Response.PortfolioValueAndUpdatedAtDto;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Dto.Response.StocksDto;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Models.PortfolioHoldings;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Models.Stocks;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Models.UserPortfolio;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Repository.PortfolioHoldingsRepository;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Repository.StocksRepository;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Repository.UserPortfolioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class PortfolioHoldingsService {

    private final PortfolioHoldingsRepository portfolioHoldingsRepository;

    private final AuthenticationService authenticationService;

    private final UserPortfolioRepository userPortfolioRepository;

    private final StocksRepository stocksRepository;

    private final ImportStockPriceService stockCurrentPricesService;

    private final StocksService stocksService;

    public PortfolioHoldingsService(PortfolioHoldingsRepository portfolioHoldingsRepository,
                                    AuthenticationService authenticationService,
                                    UserPortfolioRepository userPortfolioRepository,
                                    StocksRepository stocksRepository,
                                    ImportStockPriceService stockCurrentPricesService,
                                    StocksService stocksService) {
        this.portfolioHoldingsRepository = portfolioHoldingsRepository;
        this.authenticationService = authenticationService;
        this.userPortfolioRepository = userPortfolioRepository;
        this.stocksRepository = stocksRepository;
        this.stockCurrentPricesService = stockCurrentPricesService;
        this.stocksService = stocksService;
    }

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


    public boolean addHoldings(List<AddHoldingsToPortfolioDto> portfolioHoldingsDetails) {
        //JWT user retrieval
        Long currentUserId = authenticationService.getCurrentUserId();
        List<PortfolioHoldings> portfolioHoldingsList = new ArrayList<>();
        for(AddHoldingsToPortfolioDto holdings: portfolioHoldingsDetails) {
            if (portfolioHoldingsRepository
                    .existsByUserPortfolioIdAndStocksIdAndUserPortfolioUserIdAndDeletedAtIsNull
                            (holdings.getPortfolioId(), holdings.getStockId(), currentUserId)){
                return true;
            }

            UserPortfolio userPortfolio = userPortfolioRepository.findByIdAndUserIdAndDeletedAtIsNull(
                    holdings.getPortfolioId(), currentUserId);
            StocksDto stocksDto = stocksService.getStocksDetails(holdings.getTicker().trim());
            Stocks stocks = stocksRepository.findById(stocksDto.getId())
                    .orElseThrow();

            PortfolioHoldings portfolioHoldings = new PortfolioHoldings();
            portfolioHoldings.setUserPortfolio(userPortfolio);
            portfolioHoldings.setShares(holdings.getShares());
            portfolioHoldings.setAvgPrice(holdings.getAvgPrice());
            portfolioHoldings.setStocks(stocks);
            portfolioHoldingsList.add(portfolioHoldings);
//            portfolioHoldingsRepository.save(portfolioHoldings);
        }
        portfolioHoldingsRepository.saveAll(portfolioHoldingsList);
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

    public PortfolioDetailsDto portfolioSummary(GetPortfolioHoldingsDto portfolioHoldings, String keyword, boolean refresh, int page, int size){
        Long currentUserId = authenticationService.getCurrentUserId();
        Pageable pageable = PageRequest.of(page, size);
        UserPortfolio portfolioInfo = userPortfolioRepository.findByIdAndUserIdAndDeletedAtIsNull(
                portfolioHoldings.getPortfolioId(), currentUserId);
        PortfolioDetailsDto portfolioSummary = new PortfolioDetailsDto(portfolioInfo.getPortfolioName(),
                portfolioInfo.getId(), portfolioInfo.getDescription());
        portfolioSummary.setHoldingsDto(portfolioHoldingsRepository.getPortfolioHoldings(portfolioInfo.getId(), currentUserId));
        portfolioInsights(portfolioSummary, portfolioInfo.getId(), currentUserId);
        portfolioSummary.setPortfolioHoldings((long) portfolioSummary.getHoldings().size());
        portfolioStats(portfolioSummary,refresh);
        portfolioHoldingsPagination(portfolioSummary, portfolioInfo.getId(), keyword, pageable, currentUserId);
        portfolioAllocation(portfolioSummary);
        portfolioSummary.setSectorAllocationDto(portfolioHoldingsRepository.getSectorAllocation(currentUserId, portfolioHoldings.getPortfolioId()));
        return portfolioSummary;
    }

    private void portfolioHoldingsPagination(PortfolioDetailsDto portfolioSummary, Long portfolioId, String keyword, Pageable pageable, Long currentUserId) {
        Page<PortfolioDetailsDto.HoldingsDto> holdingsDtoPage = portfolioHoldingsRepository.getPortfolioHoldings(portfolioId, keyword == null ? "":keyword, pageable, currentUserId );
        portfolioSummary.setHoldingsDto(holdingsDtoPage.getContent());
        portfolioSummary.setFilteredPortfolioHoldings(holdingsDtoPage.getTotalElements());
        for(PortfolioDetailsDto.HoldingsDto holdingsDto: portfolioSummary.getHoldings()){
            holdingsDto.setInvestedAmount(holdingsDto.getAvgPrice()*holdingsDto.getShares());
            holdingsDto.setCurrentValue(holdingsDto.getPrice()*holdingsDto.getShares());
            double gainOrLoss = holdingsDto.getCurrentValue()- holdingsDto.getInvestedAmount();
            holdingsDto.setGainOrLoss(gainOrLoss);
            holdingsDto.setGainOrLossPercent((gainOrLoss/ holdingsDto.getInvestedAmount())*100);
        }
    }

    private void portfolioInsights(PortfolioDetailsDto portfolioSummary, Long portfolioId, Long currentUserId) {
        portfolioSummary.setMaxStockAllocation(
                portfolioHoldingsRepository.getMaxStockAllocation(portfolioId, currentUserId));
    }

    public void portfolioAllocation(PortfolioDetailsDto portfolioSummary){
        for(PortfolioDetailsDto.HoldingsDto holding: portfolioSummary.getHoldings()){
            holding.setAllocation(
                    (holding.getCurrentValue()/portfolioSummary.getPortfolioValue())*100);
        }
    }

    public void portfolioStats(PortfolioDetailsDto portfolioSummary, boolean refresh){
        double portfolioValue = 0.0;
        double portfolioInvested = 0.0;
        double portfolioGainOrLoss = 0.0;
        LocalDateTime updatedAt = null;
        LocalDateTime marketTimeStamp = null;
//        Long holdingsInPortfolio = (long) portfolioSummary.getHoldings().size();

        for (PortfolioDetailsDto.HoldingsDto holding : portfolioSummary.getHoldings()) {
            double currentValue = 0.0;
            double investedAmount = 0.0;
            Double price = holding.getPrice();

            LocalDateTime currentUpdatedAt = holding.getUpdatedAt();
            LocalDateTime currentMarketTimeStamp = holding.getMarketTimeStamp();

            if (price == null ||
                    currentUpdatedAt == null ||
                    currentMarketTimeStamp == null ||
                    (refresh  &&
                            holding.getUpdatedAt().isBefore(LocalDateTime.now().minusMinutes(5)))) {

                PortfolioValueAndUpdatedAtDto imported =
                        stockCurrentPricesService.importStockPricesForPortfolioValue(holding.getTicker());

                price = imported.getPrices();
                currentUpdatedAt = imported.getUpdatedAt();
                currentMarketTimeStamp = imported.getMarketTimeStamp();
            }
            investedAmount = holding.getShares() * holding.getAvgPrice();
            currentValue = price * holding.getShares();
            double gainOrLoss = currentValue - investedAmount;
            if (updatedAt == null || currentUpdatedAt.isAfter(updatedAt)) {
                updatedAt = currentUpdatedAt;
            }

            if (marketTimeStamp == null || currentMarketTimeStamp.isAfter(marketTimeStamp)) {
                marketTimeStamp = currentMarketTimeStamp;
            }
            portfolioInvested += investedAmount;
            portfolioValue += currentValue;
            portfolioGainOrLoss += gainOrLoss;
        }

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("MM-dd-yyyy hh:mm:ss a");

        String formattedLastUpdated =
                updatedAt != null ? updatedAt.format(formatter) : null;

        String formattedMarketTimestamp =
                marketTimeStamp != null ? marketTimeStamp.format(formatter) : null;

            portfolioSummary.setPortfolioValue(portfolioValue);
            portfolioSummary.setLastUpdatedAt(formattedLastUpdated);
            portfolioSummary.setMarketTimeStamp(formattedMarketTimestamp);
//            portfolioSummary.setPortfolioHoldings(holdingsInPortfolio);
            portfolioSummary.setPortfolioInvested(portfolioInvested);
            portfolioSummary.setGainOrLoss(portfolioGainOrLoss);
            portfolioSummary.setGainOrLossPercent((portfolioGainOrLoss/portfolioInvested)*100);
    }
}
