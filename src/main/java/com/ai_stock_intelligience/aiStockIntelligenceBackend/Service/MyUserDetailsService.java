package com.ai_stock_intelligience.aiStockIntelligenceBackend.Service;

import com.ai_stock_intelligience.aiStockIntelligenceBackend.AuthConfig.SecurityPrincipals.UserPrincipal;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Models.Users;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = userRepository.findByEmail(username);

        if(users == null){
            throw new UsernameNotFoundException("User does not exist.");
        }
        return new UserPrincipal(users); //only keeps about info needed by
    }
}
