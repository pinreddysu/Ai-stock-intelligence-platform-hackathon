package com.ai_stock_intelligience.aiStockIntelligenceBackend.Service;

import com.ai_stock_intelligience.aiStockIntelligenceBackend.Dto.Response.BasicStockDto;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Dto.Response.StockMoreDetailsDto;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Dto.Response.StocksDto;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Models.StockCurrentPrices;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Models.UserPortfolio;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Repository.StockCurrentPricesRepository;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Repository.StocksRepository;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Repository.UserPortfolioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class StocksService {

    private final StocksRepository stocksRepo;
    private final ImportStocksDetailsService importStocksDetailsService;
    private final UserPortfolioRepository userPortfolioRepository;
    private final AuthenticationService authenticationService;
    private final StockCurrentPricesRepository stockCurrentPricesRepository;
    private final ImportStockPriceService importStockPrices;

    public StocksService(StocksRepository stocksRepo, ImportStocksDetailsService importStocksDetailsService,
                         UserPortfolioRepository userPortfolioRepository, AuthenticationService authenticationService,
                         StockCurrentPricesRepository stockCurrentPricesRepository, ImportStockPriceService importStockPrices) {
        this.stocksRepo = stocksRepo;
        this.importStocksDetailsService = importStocksDetailsService;
        this.authenticationService = authenticationService;
        this.userPortfolioRepository = userPortfolioRepository;
        this.stockCurrentPricesRepository = stockCurrentPricesRepository;
        this.importStockPrices = importStockPrices;
    }

    public Page<BasicStockDto> getStocksList(Pageable pageable){
        return stocksRepo.findBy(pageable);
    }

    public Page<BasicStockDto> getStocksListWithSearch(String keyword, Pageable pageable) {
        return stocksRepo.getStocksList(keyword, pageable);
    }

    public StocksDto getStocksDetails(String ticker){
        StocksDto stocks = stocksRepo.findStockDetailsByTicker(ticker);
        if (stocks.getExchange() == null)
            importStocksDetailsService.importDetailsByTicker(ticker);
        return stocks;
    }

    public StockMoreDetailsDto getStockMoreDetails(String ticker) {
        StocksDto stocks = getStocksDetails(ticker);
        StockCurrentPrices stockCurrentPrices = stockCurrentPricesRepository.findByStocksId(stocks.getId());

        if(stockCurrentPrices != null) {
            LocalDateTime deadLineDate = stockCurrentPrices.getMarketTimeStamp().plusDays(1);
            if(stockCurrentPrices.getMarketTimeStamp().isBefore(deadLineDate))
                stockCurrentPrices = importStockPrices.importStockPrices(ticker);
        }

        if(stockCurrentPrices == null) {
            stockCurrentPrices = importStockPrices.importStockPrices(ticker);
        }
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("MM-dd-yyyy hh:mm:ss a");

        String formattedLastUpdated =
                stockCurrentPrices.getUpdatedAt() != null ? stockCurrentPrices.getUpdatedAt().format(formatter) : null;

        String formattedMarketTimestamp =
                stockCurrentPrices.getMarketTimeStamp() != null ? stockCurrentPrices.getMarketTimeStamp().format(formatter) : null;

        StockMoreDetailsDto moreDetails = new StockMoreDetailsDto(
                stocks.getId(), stocks.getTicker(),
                stocks.getCompanyName(), stocks.getSector(),
                stocks.getIndustry(), stocks.getExchange(), stockCurrentPrices.getPrices(),
                stockCurrentPrices.getPreviousClose(), stockCurrentPrices.getChange(),
                stockCurrentPrices.getChangePercentage(), formattedMarketTimestamp, formattedLastUpdated);
        return moreDetails;
    }
}
