package com.ai_stock_intelligience.aiStockIntelligenceBackend.Controller;

import com.ai_stock_intelligience.aiStockIntelligenceBackend.Dto.Requests.LoginRequestDto;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Dto.Requests.RegisterRequestDto;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Service.UserAuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserAuthController {

    private final UserAuthService userAuthService;

    public UserAuthController(UserAuthService userAuthService) {
        this.userAuthService = userAuthService;
    }

    @PostMapping("/Create")
    public ResponseEntity<String> createAccount(@Valid @RequestBody RegisterRequestDto user){
        return ResponseEntity.ok(userAuthService.createAccount(user));
    }

    @GetMapping("/Greet")
    public String greet(){
        return"TEst";
    }

    @PostMapping("/Login")
    public ResponseEntity<String> loginAccount(@Valid @RequestBody LoginRequestDto user){

        return ResponseEntity.ok(userAuthService.loginAccount(user));
    }
}
