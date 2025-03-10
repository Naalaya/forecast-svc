package com.tymex.homework.forecastsvc.config;


import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Log4j2
@Configuration
@ComponentScan(basePackages = {"com.tymex.homework.forecastsvc"})
public class AppConfig {
    @Bean
    public org.springframework.web.client.RestTemplate restTemplate() {
        return new org.springframework.web.client.RestTemplate();
    }

}
