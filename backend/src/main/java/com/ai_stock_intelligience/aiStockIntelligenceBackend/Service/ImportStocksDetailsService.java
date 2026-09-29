package com.ai_stock_intelligience.aiStockIntelligenceBackend.Service;

import com.ai_stock_intelligience.aiStockIntelligenceBackend.Controller.ImportStockDetailsClient;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Dto.StockDetailsDto;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Models.Stocks;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Repository.StocksRepository;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;


@Service
public class ImportStocksDetailsService {

    private final StocksRepository stocksRepository;
    private final ImportStockDetailsClient stockDetails;
    private final ObjectMapper objectMapper;

    public ImportStocksDetailsService(StocksRepository stocksRepository,
                                      ImportStockDetailsClient stockDetails,
                                      ObjectMapper objectMapper) {
        this.stocksRepository = stocksRepository;
        this.stockDetails = stockDetails;
        this.objectMapper = objectMapper;
    }

    public void importDetailsByTicker(String ticker){

        String json = stockDetails.importStockDetails(ticker);

        StockDetailsDto stocksDetails =
                objectMapper.readValue(json, StockDetailsDto.class);

        Stocks stocks = stocksRepository.findByTicker(ticker.trim());
        stocks.setExchange(stocksDetails.getExchange());
        stocks.setIndustry(stocksDetails.getIndustry());
        stocks.setSector(stocksDetails.getSector());
        stocksRepository.save(stocks);
    }
}
