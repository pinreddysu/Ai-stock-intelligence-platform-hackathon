package com.ai_stock_intelligience.aiStockIntelligenceBackend.Service;

import com.ai_stock_intelligience.aiStockIntelligenceBackend.Dto.Response.StocksDto;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Repository.StocksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.autoconfigure.web.DataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StocksService {

    @Autowired
    private StocksRepository stocksRepo;

    public Page<StocksDto> getStocksList(Pageable pageable){
        return stocksRepo.findBy(pageable);
    }

    public Page<StocksDto> getStocksListWithSearch(String keyword, Pageable pageable) {
        return stocksRepo.getStocksList(keyword, pageable);
    }
}
