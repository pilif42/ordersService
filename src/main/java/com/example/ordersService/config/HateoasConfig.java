package com.example.ordersService.config;

import com.example.ordersService.hateoasprocessor.ProductProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HateoasConfig {
    @Bean
    ProductProcessor productProcessor() {
        return new ProductProcessor();
    }
}
