package com.ai_stock_intelligience.aiStockIntelligenceBackend.Service;

import com.ai_stock_intelligience.aiStockIntelligenceBackend.Dto.Requests.RegisterRequestDto;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Models.Users;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAuthService {

    @Autowired
    private UserRepository userRepository;

    public Users createAccount(RegisterRequestDto user){
       Users users = new Users();
       users.setFirstName(user.getFirstName());
       users.setLastName(user.getLastName());
       users.setEmail(user.getEmail());
       users.setPasswordHash(user.getPassword());

        return userRepository.save(users);
    }

//    public Users loginAccount(RegisterRequestDto user) {
//
//    }
}
