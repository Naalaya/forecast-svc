//package com.tymex.homework.forecastsvc.config;
//
//import lombok.extern.log4j.Log4j2;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.http.client.reactive.ReactorResourceFactory;
//import reactor.netty.resources.ConnectionProvider;
//import reactor.netty.resources.LoopResources;
//
//@Log4j2
//@Configuration
//@ComponentScan(basePackages = {"com.tymex.homework.forecastsvc"})
//public class AppConfig {
//    @Bean
//    public ReactorResourceFactory resourceFactory() {
//        ReactorResourceFactory factory = new ReactorResourceFactory();
//        factory.setUseGlobalResources(false);
//        factory.setConnectionProvider(ConnectionProvider.create("http-inbound-connection", 20));
//        factory.setLoopResources(LoopResources.create("http-inbound-pool", 20, true));
//        return factory;
//    }
//
//}
