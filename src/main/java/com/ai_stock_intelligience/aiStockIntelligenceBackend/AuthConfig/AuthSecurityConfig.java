package com.ai_stock_intelligience.aiStockIntelligenceBackend.AuthConfig;

import com.ai_stock_intelligience.aiStockIntelligenceBackend.Constants.UserConstants;
import com.ai_stock_intelligience.aiStockIntelligenceBackend.Service.MyUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
@EnableWebSecurity
public class AuthSecurityConfig {

    private final MyUserDetailsService userDetailsService; //verifies username and password

    private final JwtFilter jwtFilter;

    public AuthSecurityConfig(MyUserDetailsService userDetailsService, JwtFilter jwtFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity){
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.authorizeHttpRequests(request ->
                request.requestMatchers("/Create", "/Login", "/getStocks", "/getStockDetails")
                        .permitAll()
                        .anyRequest().authenticated());
//        httpSecurity.formLogin(Customizer.withDefaults()); //for webpage
        httpSecurity.httpBasic(Customizer.withDefaults()); //Postman
        httpSecurity.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        httpSecurity.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class); //Adds Jwt filter before username and password authentication
        return httpSecurity.build();
    }

    @Bean //basically provides the service to check it
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder(UserConstants.encryptionStrength)); //tells which password encoder is being used
        return provider;
    }

    //Purpose to tell this filter chain that there are more types of authentication can be processed like JWT or login via username and password. (The default username and password login)
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig){
        return authConfig.getAuthenticationManager();
    }
}
