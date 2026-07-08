package com.ai_stock_intelligience.aiStockIntelligenceBackend.Controller;

import com.ai_stock_intelligience.aiStockIntelligenceBackend.Models.Users;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Service.UserAuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserAuthController {

    @Autowired
    private UserAuthService userAuthService;

    @PostMapping("/Create")
    public ResponseEntity<Users> createAccount(@RequestBody @Valid Users user){
        return ResponseEntity.ok(userAuthService.createAccount(user));
    }

//    @PostMapping("/Login")
//    public Users loginAccount(@RequestBody Users user){
//        return userAuthService.loginAccount(user);
//    }
}
