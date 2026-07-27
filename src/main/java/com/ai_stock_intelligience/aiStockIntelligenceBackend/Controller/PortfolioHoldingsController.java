package com.ai_stock_intelligience.aiStockIntelligenceBackend.Controller;

import com.ai_stock_intelligience.aiStockIntelligenceBackend.Dto.Requests.*;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Dto.Response.HoldingsDTO;
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
    public ResponseEntity<String> updateHoldings(@Valid @RequestBody UpdateHoldingRequestDto portfolioHoldings) {
        PortfolioHoldings updatedPortfolioHoldings = portfolioHoldingsService.updateHoldings(portfolioHoldings);
        if (updatedPortfolioHoldings != null)
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Holding has been updated");
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Holding could not be updated");
    }

    @DeleteMapping("/removeHoldings")
    public ResponseEntity<String> deleteHoldings(@Valid @RequestBody RemoveHoldingRequestDto portfolioHoldings) {
        if (portfolioHoldingsService.deleteHoldings(portfolioHoldings))
            return ResponseEntity.ok("Stock has been removed");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Holding does not exist to delete it");
    }

    @DeleteMapping("/removeListOfHoldings")
    public ResponseEntity<String> deleteListOfHoldings(@Valid @RequestBody RemoveHoldingsListRequestDto portfolioHoldingsIds){
        if(portfolioHoldingsService.deleteListOfHoldings(portfolioHoldingsIds))
            return ResponseEntity.ok("List of holdings have been deleted");
        return ResponseEntity.internalServerError().body("List of holdings could not be deleted");
    }

//    @GetMapping("/getAllHoldings")
//    public ResponseEntity<List<HoldingsDTO>> getAllHoldings(@Valid @RequestBody GetPortfolioHoldingsDto portfolioHoldings){
//        return ResponseEntity.ok(portfolioHoldingsService.getAllHoldings(portfolioHoldings));
//    }

    @GetMapping("/getAllHoldings")
    public ResponseEntity<List<HoldingsDTO>> filterPortfoliosByNameByUser(@Valid @RequestBody GetPortfolioHoldingsDto portfolioHoldings, @RequestParam(required = false) String keyword){
        if (keyword != null) {
           return ResponseEntity.ok(portfolioHoldingsService.filterSearchHoldings(portfolioHoldings, keyword));
        }
        return ResponseEntity.ok(portfolioHoldingsService.getAllHoldings(portfolioHoldings));
    }
}
