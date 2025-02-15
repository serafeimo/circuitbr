package com.example.circuitbr.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CircuitBreakerConfig {

    @Bean
    public CircuitBreaker albumsServiceCircuitBreaker(CircuitBreakerRegistry registry) {
        return registry.circuitBreaker("albumsService");
    }
}