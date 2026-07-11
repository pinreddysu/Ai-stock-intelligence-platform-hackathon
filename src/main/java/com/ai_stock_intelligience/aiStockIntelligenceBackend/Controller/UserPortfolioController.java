package com.ai_stock_intelligience.aiStockIntelligenceBackend.Controller;

import com.ai_stock_intelligience.aiStockIntelligenceBackend.Models.UserPortfolio;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Service.UserPortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserPortfolioController {

    @Autowired
    private UserPortfolioService userPortfolioService;

    @PostMapping("/createPortfolio")
    public ResponseEntity<String> createPortfolio(@RequestBody UserPortfolio userPortfolio){
        if(userPortfolioService.createPortfolio(userPortfolio)){
            return ResponseEntity.badRequest().body("Portfolio already exists with that name");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Portfolio is created");
    }

    @GetMapping("/myPortfolios")
    public ResponseEntity<List<UserPortfolio>> getListOfPortfoliosByUser(@RequestBody UserPortfolio userPortfolio){
        return ResponseEntity.ok(userPortfolioService.getListOfPortfoliosByUser(userPortfolio));
    }

    @PatchMapping("/editPortfolioDetails")
    public ResponseEntity<UserPortfolio> updatePortfolioById(@RequestBody UserPortfolio userPortfolio){
        UserPortfolio updatedUserPortfolio = userPortfolioService.updatePortfolioDetails(userPortfolio);
        if (updatedUserPortfolio != null) {
            return ResponseEntity.ok(userPortfolioService.updatePortfolioDetails(userPortfolio));
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @PatchMapping("/deletePortfolio")
    public ResponseEntity<String> deletePortfolio(@RequestBody UserPortfolio userPortfolio){
        if(userPortfolioService.deletePortfolio(userPortfolio) != null){
            return ResponseEntity.ok("Portfolio deleted");
        }
        else {
            return ResponseEntity.badRequest().body("Portfolio does not exist or has been already deleted");
        }
    }
}
