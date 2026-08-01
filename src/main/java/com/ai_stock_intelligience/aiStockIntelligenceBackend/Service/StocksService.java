package com.ai_stock_intelligience.aiStockIntelligenceBackend.Service;

import com.ai_stock_intelligience.aiStockIntelligenceBackend.Dto.Response.BasicStockDto;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Dto.Response.StocksDto;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Repository.StocksRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class StocksService {

    private final StocksRepository stocksRepo;
    private final ImportStocksDetailsService importStocksDetailsService;

    public StocksService(StocksRepository stocksRepo, ImportStocksDetailsService importStocksDetailsService) {
        this.stocksRepo = stocksRepo;
        this.importStocksDetailsService = importStocksDetailsService;
    }

    public Page<BasicStockDto> getStocksList(Pageable pageable){
        return stocksRepo.findBy(pageable);
    }

    public Page<BasicStockDto> getStocksListWithSearch(String keyword, Pageable pageable) {
        return stocksRepo.getStocksList(keyword, pageable);
    }
}
