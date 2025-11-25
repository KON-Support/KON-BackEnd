package com.trier.KON_BackEnd.config;

import com.trier.KON_BackEnd.utils.JwtAuthFilter;
import com.trier.KON_BackEnd.utils.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class JwtFilterConfig {
    @Bean
    public JwtAuthFilter jwtAuthFilter(UserDetailsService userDetailsService, JwtUtil jwtUtil) {
        return new JwtAuthFilter(userDetailsService, jwtUtil);
    }


}
