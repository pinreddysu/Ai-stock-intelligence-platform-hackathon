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
//            System.out.println(userId);
        }
//checks if it is the credentials provided by user authenticated and there is valid email and userid data from token
        if (email != null && userId != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = context.getBean(MyUserDetailsService.class).loadUserByUsername(email); //gives email, password, and granted authorities from db via email passed from token to object
            if(jwtService.validatingToken(token, userDetails)){ //checks the token valid or not
                UsernamePasswordAuthenticationToken userAuthToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()); //passes email and password and authorities to create authenticated object
                userAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); //Sets IP and session info
                SecurityContextHolder.getContext().setAuthentication(userAuthToken); //holds on to this info being authenticated
            }

        }
        filterChain.doFilter(request,response); //proceed to next filter chain
    }
}
