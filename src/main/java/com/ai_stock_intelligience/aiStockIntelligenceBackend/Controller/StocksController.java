package com.ai_stock_intelligience.aiStockIntelligenceBackend.Controller;

import com.ai_stock_intelligience.aiStockIntelligenceBackend.Dto.Response.BasicStockDto;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Dto.Response.StockMoreDetailsDto;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Dto.Response.StocksDto;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Service.StocksService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class StocksController {

    private final StocksService stocks;

    public StocksController(StocksService stocks) {
        this.stocks = stocks;
    }

    @GetMapping("/getStocks")
    public ResponseEntity<BasicStockDto> getStocksList(
            @RequestParam String keyword, @RequestParam Long portfolioId, int page){
        if(!keyword.trim().isEmpty()) {
            return ResponseEntity.ok(stocks.getStocksListWithSearch(keyword, portfolioId, page));
        }
        return null;
    }

    @GetMapping("/getStockDetails")
    public ResponseEntity<StocksDto> getStockDetails(@RequestParam String ticker){
        return ResponseEntity.ok(stocks.getStocksDetails(ticker));
    }

    @GetMapping("/getStockMoreDetails")
    public ResponseEntity<StockMoreDetailsDto> getMoreStockDetails(@RequestParam String ticker){
        return ResponseEntity.ok(stocks.getStockMoreDetails(ticker));
    }

// TEMP data stock data like ticker and company
//    @PostMapping("/admin/getStocksData")
//    public ResponseEntity<List<Stocks>> getStockTickerAndName(){
//        return ResponseEntity.ok(importService.importStocks());
//    }

}
