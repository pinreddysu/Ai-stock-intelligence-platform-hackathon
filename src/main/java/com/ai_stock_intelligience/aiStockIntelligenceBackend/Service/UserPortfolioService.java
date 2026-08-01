package com.ai_stock_intelligience.aiStockIntelligenceBackend.Service;

import com.ai_stock_intelligience.aiStockIntelligenceBackend.Dto.Requests.*;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Dto.Response.PortfolioInfoDto;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Dto.Response.PortfolioValueAndUpdatedAtDto;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Dto.Response.PortfolioValueUpdateDto;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Dto.Response.PortfoliosSummaryDto;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Models.UserPortfolio;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Models.Users;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Repository.UserPortfolioRepository;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class UserPortfolioService {

    private final AuthenticationService authenticationService;

    private final UserPortfolioRepository userPortfolioRepository;

    private final UserRepository userRepository;

    private final ImportStockPriceService stockPriceService;

    public UserPortfolioService(AuthenticationService authenticationService,
                                UserPortfolioRepository userPortfolioRepository,
                                UserRepository userRepository,
                                ImportStockPriceService stockPriceService) {
        this.authenticationService = authenticationService;
        this.userPortfolioRepository = userPortfolioRepository;
        this.userRepository = userRepository;
        this.stockPriceService = stockPriceService;
    }

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

    public List<PortfolioInfoDto> getListOfPortfoliosByUser(String keyword) {
        Long currentUserId = authenticationService.getCurrentUserId(); //JWt
        List<PortfolioInfoDto> portfolioList;

        if(keyword != null){
            portfolioList = filterPortfoliosByNameByUser(keyword);
        }
        else{
            portfolioList = userPortfolioRepository.findByUserIdAndDeletedAtIsNull(currentUserId);
        }

        for(PortfolioInfoDto portfolio: portfolioList){
            portfolioSummary(currentUserId, portfolio, null);
        }
        return portfolioList;
    }

    public void portfolioSummary(Long currentUserId, PortfolioInfoDto portfolio, PortfolioValueUpdateDto portfolioValueUpdate) {
        List<PortfoliosSummaryDto> portfolioSummary = userPortfolioRepository
                .portfolioSummary(currentUserId, portfolioValueUpdate == null ? portfolio.getId() : portfolioValueUpdate.getId());

        double portfolioValue = 0.0;
        LocalDateTime updatedAt = null;
        LocalDateTime marketTimeStamp = null;
        Long holdingsInPortfolio = (long) portfolioSummary.size();

        for (PortfoliosSummaryDto holding : portfolioSummary) {

            Double price = holding.getPrice();

            LocalDateTime currentUpdatedAt = holding.getUpdatedAt();
            LocalDateTime currentMarketTimeStamp = holding.getMarketTimeStamp();

            if (price == null ||
                    currentUpdatedAt == null ||
                    currentMarketTimeStamp == null ||
                    (portfolioValueUpdate != null  &&
                            holding.getUpdatedAt().isBefore(LocalDateTime.now().minusMinutes(5)))) {

                PortfolioValueAndUpdatedAtDto imported =
                        stockPriceService.importStockPrices(holding.getTicker());

                price = imported.getPrices();
                currentUpdatedAt = imported.getUpdatedAt();
                currentMarketTimeStamp = imported.getMarketTimeStamp();
            }

            portfolioValue += price * holding.getShares();

            if (updatedAt == null || currentUpdatedAt.isAfter(updatedAt)) {
                updatedAt = currentUpdatedAt;
            }

            if (marketTimeStamp == null || currentMarketTimeStamp.isAfter(marketTimeStamp)) {
                marketTimeStamp = currentMarketTimeStamp;
            }
        }

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("MM-dd-yyyy hh:mm:ss a");

        String formattedLastUpdated =
                updatedAt != null ? updatedAt.format(formatter) : null;

        String formattedMarketTimestamp =
                marketTimeStamp != null ? marketTimeStamp.format(formatter) : null;


        if (portfolio == null) {

            portfolioValueUpdate.setPortfolioValue(portfolioValue);
            portfolioValueUpdate.setLastUpdatedAt(formattedLastUpdated);
            portfolioValueUpdate.setMarketTimeStamp(formattedMarketTimestamp);
            portfolioValueUpdate.setPortfolioHoldings(holdingsInPortfolio);

        } else {

            portfolio.setPortfolioValue(portfolioValue);
            portfolio.setLastUpdatedAt(formattedLastUpdated);
            portfolio.setMarketTimeStamp(formattedMarketTimestamp);
            portfolio.setPortfolioHoldings(holdingsInPortfolio);
        }
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

    public boolean deletePortfoliosList(DeletePortfoliosListRequestDto portfoliosList) {
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

    public List<PortfolioInfoDto> filterPortfoliosByNameByUser(String keyword) {
        Long currentUserId = authenticationService.getCurrentUserId();
        return userPortfolioRepository.filterSearchPortfoliosByNameByUser(keyword, currentUserId);
    }

    public PortfolioValueUpdateDto getUpdatedPortfolioValue(Long portfolioId) {
        Long currentUserId = authenticationService.getCurrentUserId();
        PortfolioValueUpdateDto portfolio = new PortfolioValueUpdateDto();
        portfolio.setId(portfolioId);
        portfolioSummary(currentUserId,null, portfolio);
        return portfolio;
    }
}
