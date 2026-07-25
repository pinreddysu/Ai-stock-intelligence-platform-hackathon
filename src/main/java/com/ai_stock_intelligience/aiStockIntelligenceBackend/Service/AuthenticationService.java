package com.ai_stock_intelligience.aiStockIntelligenceBackend.Service;

import com.ai_stock_intelligience.aiStockIntelligenceBackend.AuthConfig.SecurityPrincipals.UserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthenticationService {

    public Long getCurrentUserId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) Objects.requireNonNull(authentication).getPrincipal();
        return Objects.requireNonNull(userPrincipal).getUserId();
    }
}
