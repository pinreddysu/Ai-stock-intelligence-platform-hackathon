package com.ai_stock_intelligience.aiStockIntelligenceBackend.AuthConfig;

import com.ai_stock_intelligience.aiStockIntelligenceBackend.Constants.UserConstants;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Service.JwtService;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Service.MyUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    ApplicationContext context;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        String token = null;
        String email = null;
        Long userId = null;

        if (header != null && header.startsWith("Bearer ")){
            token = header.substring(UserConstants.extractTokenStart);
            email = jwtService.extractEmail(token);
            System.out.println(email);
            userId = jwtService.extractUserId(token);
            System.out.println(userId);
        }

        if (email != null && userId != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = context.getBean(MyUserDetailsService.class).loadUserByUsername(email); //gives email, password, userid, and granted authorities
            if(jwtService.validatingToken(token, userDetails)){
                UsernamePasswordAuthenticationToken userAuthToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                userAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(userAuthToken);
            }

        }
        filterChain.doFilter(request,response);
    }
}
