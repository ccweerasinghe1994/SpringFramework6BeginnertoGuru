package com.wchamara.spring6restmvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Use lambda-style configuration for authorizeHttpRequests
        http.authorizeHttpRequests(auth -> {
                    auth.anyRequest().authenticated();
                })
                .httpBasic(Customizer.withDefaults())  // Default basic HTTP authentication
                .csrf(csrf -> csrf.ignoringRequestMatchers("/api/**"));  // Disable CSRF for API routes

        return http.build();
    }
}
