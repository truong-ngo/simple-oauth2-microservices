package com.example.springclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.GatewayFilterSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@CrossOrigin("http://localhost:4200")
public class SpringClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringClientApplication.class, args);
    }

    @Bean
    public RouteLocator gateway(RouteLocatorBuilder builder) {
        return builder
                .routes()
                .route(rs -> rs
                        .path("/")
                        .filters(GatewayFilterSpec::tokenRelay)
                        .uri("http://localhost:8081")
                ).build();
    }
}