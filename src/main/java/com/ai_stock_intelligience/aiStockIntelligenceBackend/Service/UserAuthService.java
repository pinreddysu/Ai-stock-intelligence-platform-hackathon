package com.ai_stock_intelligience.aiStockIntelligenceBackend.Service;

import com.ai_stock_intelligience.aiStockIntelligenceBackend.Constants.UserConstants;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Dto.Requests.LoginRequestDto;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Dto.Requests.RegisterRequestDto;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Models.Users;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Projections.AuthenticationUserIdProjection;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserAuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(UserConstants.encryptionStrength);

    public Users createAccount(RegisterRequestDto user){
       Users users = new Users();
       users.setFirstName(user.getFirstName());
       users.setLastName(user.getLastName());
       users.setEmail(user.getEmail());
       users.setPasswordHash(bCryptPasswordEncoder.encode(user.getPassword()));

        return userRepository.save(users);
    }

    public String loginAccount(LoginRequestDto user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
        );
        if (authentication.isAuthenticated()){
            AuthenticationUserIdProjection userId = userRepository.findProjectedByEmail(user.getEmail());
            String token = jwtService.generateToken(user.getEmail(), userId.getId());
            System.out.println(token);
            return token;
        }
        else{
            return "Failed";
        }
    }
}
