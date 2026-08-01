package com.ai_stock_intelligience.aiStockIntelligenceBackend.Controller;

import com.ai_stock_intelligience.aiStockIntelligenceBackend.Dto.Response.StocksDto;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Service.StocksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.autoconfigure.web.DataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StocksController {

    private final StocksService stocks;

    public StocksController(StocksService stocks) {
        this.stocks = stocks;
    }

    @GetMapping("/getStocks")
    public ResponseEntity<Page<StocksDto>> getStocksList(
            @RequestParam(required = false) String keyword, Pageable pageable){
        if (keyword != null){
            return ResponseEntity.ok(stocks.getStocksListWithSearch(keyword, pageable));
        }
        return ResponseEntity.ok(stocks.getStocksList(pageable));
    }

}
