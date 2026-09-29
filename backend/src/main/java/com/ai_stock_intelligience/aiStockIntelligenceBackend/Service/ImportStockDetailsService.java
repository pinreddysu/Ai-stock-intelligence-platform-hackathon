package com.ai_stock_intelligience.aiStockIntelligenceBackend.Service;

import com.ai_stock_intelligience.aiStockIntelligenceBackend.Controller.ImportStocksDataClient;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Dto.StockImportDto;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Models.Stocks;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Repository.StocksRepository;
import org.springframework.stereotype.Service;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

@Service
public class ImportStockDetailsService {

    private final StocksRepository stocksRepository;
    private final ImportStocksDataClient importStocksDataClient;
    private final ObjectMapper objectMapper;

    public ImportStockDetailsService(StocksRepository stocksRepository,
                                     ImportStocksDataClient importStocksDataClient,
                                     ObjectMapper objectMapper) {
        this.stocksRepository = stocksRepository;
        this.importStocksDataClient = importStocksDataClient;
        this.objectMapper = objectMapper;
    }

    public List<Stocks> importStocks(){
        String json = importStocksDataClient.getStocksDetailsList();

        Map<String, StockImportDto> companies = objectMapper.readValue(
                json,
                new TypeReference<Map<String, StockImportDto>>(){});

        List<Stocks> stocks = companies.values().stream() //changed return type to add conditionally if stocks exist just modified otherwise create new object
                .limit(100)
                .map(company ->
                    {
                        Stocks stock = stocksRepository
                                .findByTicker(company.getTicker().trim());
                        if (stock == null){
                            Stocks newStock = new Stocks();
                            newStock.setTicker(company.getTicker().trim());
                            newStock.setCompanyName(company.getCompanyName().trim());
                            return newStock;
                        }
                        stock.setTicker(company.getTicker().trim());
                        stock.setCompanyName(company.getCompanyName().trim());
                        return stock;
                    }).toList();

        return stocksRepository.saveAll(stocks);
    }
}
