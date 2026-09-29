package com.ai_stock_intelligience.aiStockIntelligenceBackend.AuthConfig.SecurityPrincipals;

import com.ai_stock_intelligience.aiStockIntelligenceBackend.Models.Users;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class UserPrincipal implements UserDetails {

    private final Users users;

    public UserPrincipal(Users users){
        this.users = users;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("User"));
    }

    @Override
    public @Nullable String getPassword() {
        return users.getPasswordHash();
    }

    @Override
    public String getUsername() {
        return users.getEmail();
    }

    public Long getUserId(){ return users.getId();} //added userid field for Daoauth
}
