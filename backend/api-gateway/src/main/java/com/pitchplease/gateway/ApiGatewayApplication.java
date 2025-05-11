package com.pitchplease.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiGatewayApplication {
    public static void main(String[] args) {
        System.setProperty("server.port", "8080");
        SpringApplication.run(ApiGatewayApplication.class, args);
    }
}