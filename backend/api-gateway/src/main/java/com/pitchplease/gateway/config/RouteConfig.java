package com.pitchplease.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {
    
    @Value("${microservice.facility-discovery-service.url}")
    private String facilityDiscoveryServiceUrl;
    
    @Value("${microservice.facility-management-service.url}")
    private String facilityManagementServiceUrl;
    
    @Value("${microservice.booking-service.url}")
    private String bookingServiceUrl;
    
    @Value("${microservice.user-service.url}")
    private String userServiceUrl;
    
    @Value("${microservice.payment-service.url}")
    private String paymentServiceUrl;
    
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
            // Facility Discovery routes
            .route("facility_discovery_service", r -> r
                .path("/api/get_all_facilities", "/api/facilities/**")
                .uri(facilityDiscoveryServiceUrl))
            
            // Other service routes
            .route("facility_management_service", r -> r
                .path("/api/manage/**")
                .uri(facilityManagementServiceUrl))
            
            .route("booking_service", r -> r
                .path("/api/bookings/**")
                .uri(bookingServiceUrl))
                
            .route("user_service", r -> r
                .path("/api/users/**", "/api/auth/**")
                .uri(userServiceUrl))
                
            .route("payment_service", r -> r
                .path("/api/payments/**")
                .uri(paymentServiceUrl))
                
            .build();
    }
}