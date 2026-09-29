package com.ai_stock_intelligience.aiStockIntelligenceBackend.Service;

import com.ai_stock_intelligience.aiStockIntelligenceBackend.Controller.ImportStockPriceClient;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Dto.Response.PortfolioValueAndUpdatedAtDto;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Dto.StockPricesDto;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Models.StockCurrentPrices;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Models.Stocks;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Repository.StockCurrentPricesRepository;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Repository.StocksRepository;
import org.springframework.stereotype.Service;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class ImportStockPriceService {

    private final ImportStockPriceClient stockPriceClient;
    private final ObjectMapper mapper;
    private final StocksRepository stocksRepository;
    private final StockCurrentPricesRepository pricesRepository;

    public ImportStockPriceService(ImportStockPriceClient stockPriceClient,
                                   ObjectMapper mapper, StocksRepository stocksRepository,
                                   StockCurrentPricesRepository pricesRepository) {
        this.stockPriceClient = stockPriceClient;
        this.mapper = mapper;
        this.stocksRepository = stocksRepository;
        this.pricesRepository = pricesRepository;
    }

    public StockCurrentPrices importStockPrices(String ticker){
        String json = stockPriceClient.importStockPrices(ticker);
        List<StockPricesDto> stockPricesDto = mapper.readValue(json,
                new TypeReference<List<StockPricesDto>>() {});

        StockPricesDto stocksPrices = stockPricesDto.get(0);

        Stocks stocks = stocksRepository.findByTicker(ticker);

        StockCurrentPrices stockCurrentPrices = pricesRepository.findByStocksId(stocks.getId());

        if(stockCurrentPrices == null){
            stockCurrentPrices = new StockCurrentPrices();
        }

        stockCurrentPrices.setStocks(stocks);
        stockCurrentPrices.setChange(stocksPrices.getChange());
        stockCurrentPrices.setPrices(stocksPrices.getPrice());
        stockCurrentPrices.setChangePercentage(stocksPrices.getChangePercentage());
        stockCurrentPrices.setPreviousClose(stocksPrices.getPreviousClose());
        LocalDateTime dateTime =
                LocalDateTime.ofInstant(
                        Instant.ofEpochSecond(stocksPrices.getTimestamp()),
                        ZoneId.systemDefault()
                );
        stockCurrentPrices.setMarketTimeStamp(dateTime);
        pricesRepository.save(stockCurrentPrices);
        return stockCurrentPrices;
    }

    public PortfolioValueAndUpdatedAtDto importStockPricesForPortfolioValue(String ticker){
        StockCurrentPrices stockCurrentPrices = importStockPrices(ticker);
        return new PortfolioValueAndUpdatedAtDto(stockCurrentPrices.getPrices(), stockCurrentPrices.getUpdatedAt(), stockCurrentPrices.getMarketTimeStamp());
    }

}
