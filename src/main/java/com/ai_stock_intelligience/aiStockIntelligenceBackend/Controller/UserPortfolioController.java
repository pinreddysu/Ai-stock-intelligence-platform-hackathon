package com.ai_stock_intelligience.aiStockIntelligenceBackend.Controller;

import com.ai_stock_intelligience.aiStockIntelligenceBackend.Dto.Requests.CreatePortfolioRequestDto;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Dto.Requests.DeletePortfolioRequestDto;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Dto.Requests.DeletePortfoliosListRequestDto;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Dto.Requests.UpdatePortfolioRequestDto;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Dto.Response.PortfolioInfoDto;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Models.UserPortfolio;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Service.UserPortfolioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserPortfolioController {

    private final UserPortfolioService userPortfolioService;

    public UserPortfolioController(UserPortfolioService userPortfolioService) {
        this.userPortfolioService = userPortfolioService;
    }

    @PostMapping("/createPortfolio")
    public ResponseEntity<String> createPortfolio(@Valid @RequestBody CreatePortfolioRequestDto userPortfolio){
        if(userPortfolioService.createPortfolio(userPortfolio)){
            return ResponseEntity.badRequest().body("Portfolio already exists with that name");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Portfolio is created");
    }

    @GetMapping("/myPortfolios")
    public ResponseEntity<List<PortfolioInfoDto>> getListOfPortfoliosByUser(@RequestParam(required = false) String keyword){
        return ResponseEntity.ok(userPortfolioService.getListOfPortfoliosByUser(keyword));
    }

    @PatchMapping("/editPortfolioDetails")
    public ResponseEntity<String> updatePortfolioById(@Valid @RequestBody UpdatePortfolioRequestDto userPortfolio){
        UserPortfolio updatedUserPortfolio = userPortfolioService.updatePortfolioDetails(userPortfolio);
        if (updatedUserPortfolio != null) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Portfolio is updated");
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Portfolio could not be updated");
        }
    }

    @DeleteMapping("/deletePortfolio")
    public ResponseEntity<String> deletePortfolio(@Valid @RequestBody DeletePortfolioRequestDto userPortfolio){
        if(userPortfolioService.deletePortfolio(userPortfolio)){
            return ResponseEntity.ok("Portfolio deleted");
        }
        else {
            return ResponseEntity.badRequest().body("Portfolio does not exist or has been already deleted");
        }
    }

    @DeleteMapping("/deletePortfoliosList")
    public ResponseEntity<String> deletePortfoliosList(@Valid @RequestBody DeletePortfoliosListRequestDto portfoliosList){
        if(userPortfolioService.deletePortfoliosList(portfoliosList)){
            return ResponseEntity.ok("Portfolios deleted");
        }
        return ResponseEntity.badRequest().body("List of portfolios could not be deleted");
    }
}
