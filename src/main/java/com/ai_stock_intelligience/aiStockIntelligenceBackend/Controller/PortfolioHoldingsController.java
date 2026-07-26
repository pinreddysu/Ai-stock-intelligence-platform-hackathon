package com.ai_stock_intelligience.aiStockIntelligenceBackend.Controller;

import com.ai_stock_intelligience.aiStockIntelligenceBackend.Dto.HoldingsDTO;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Dto.Requests.AddHoldingsToPortfolioDto;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Dto.Requests.GetPortfolioHoldingsDto;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Models.PortfolioHoldings;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Service.PortfolioHoldingsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PortfolioHoldingsController {

    @Autowired
    private PortfolioHoldingsService portfolioHoldingsService;

    @PostMapping("/addStocks")
    public ResponseEntity<String> addHoldingsToPortfolio(@Valid @RequestBody AddHoldingsToPortfolioDto portfolioHoldings){
        if (portfolioHoldingsService.addHoldings(portfolioHoldings))
            return ResponseEntity.badRequest().body("Stock already exists in this portfolio");

        return ResponseEntity.status(HttpStatus.CREATED).body("Stock was Added");
    }

    @PatchMapping("/updateHoldings")
    public ResponseEntity<PortfolioHoldings> updateHoldings(@RequestBody PortfolioHoldings portfolioHoldings) {
        PortfolioHoldings updatedPortfolioHoldings = portfolioHoldingsService.updateHoldings(portfolioHoldings);
        if (updatedPortfolioHoldings != null)
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedPortfolioHoldings);
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @DeleteMapping("/removeHoldings")
    public ResponseEntity<String> deleteHoldings(@RequestBody PortfolioHoldings portfolioHoldings) {
        if (portfolioHoldingsService.deleteHoldings(portfolioHoldings))
            return ResponseEntity.ok("Stock has been removed");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Holding does not exist to delete it");
    }

    @DeleteMapping("/removeListOfHoldings")
    public ResponseEntity<String> deleteListOfHoldings(@RequestBody List<PortfolioHoldings> portfolioHoldingsList){
        portfolioHoldingsService.deleteListOfHoldings(portfolioHoldingsList);
        return ResponseEntity.ok("List of holdings have been deleted");
    }

    @GetMapping("/getAllHoldings")
    public ResponseEntity<List<HoldingsDTO>> getAllHoldings(@Valid @RequestBody GetPortfolioHoldingsDto portfolioHoldings){
        return ResponseEntity.ok(portfolioHoldingsService.getAllHoldings(portfolioHoldings));
    }
}
