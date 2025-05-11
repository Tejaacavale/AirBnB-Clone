package com.pitchplease.gateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.core.ParameterizedTypeReference;
import java.util.Base64;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller to handle sports facility related requests.
 * Acts as a gateway to redirect requests to the facility-service microservice.
 */
@RestController
@RequestMapping("/facilities")
public class FacilityController {

    private static final Logger logger = LoggerFactory.getLogger(FacilityController.class);
    
    @Autowired
    private RestTemplate restTemplate;
    
    @Value("${microservice.facility-discovery-service.url}")
    private String facilityDiscoveryServiceUrl;

    /**
     * Get all available facilities
     * 
     * @return List of all facilities
     */
    @GetMapping("/all")
    public ResponseEntity<?> getAllFacilities() {
        logger.info("Received request to fetch all facilities");
        
        try {
            System.out.println("l39 gateway/facilitycontroller.java");
            System.out.println("l40 url = "+ facilityDiscoveryServiceUrl + "/all");

            // Add HTTP Basic Authentication
            HttpHeaders headers = new HttpHeaders();
            String auth = "postgres:postgres"; // Replace with actual credentials
            byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));
            String authHeader = "Basic " + new String(encodedAuth);
            headers.set("Authorization", authHeader);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            // Forward the request to the facility microservice
            // ResponseEntity<?> response = restTemplate.getForEntity(
            //         facilityDiscoveryServiceUrl + "/facilities_discovery", 
            //         Object.class);

            // Forward the request to the facility microservice
            ResponseEntity<List<?>> response = restTemplate.exchange(
                facilityDiscoveryServiceUrl + "/all", 
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<?>>() {}
            );

            logger.info("Successfully fetched facilities from facility-discovery-service");
            
            // Return the response from the facility microservice
            return ResponseEntity
                    .status(response.getStatusCode())
                    .body(response.getBody());
                    
        } catch (Exception e) {
            logger.error("Error while fetching facilities: {}", e.getMessage());
            return ResponseEntity
                    .internalServerError()
                    .body("Failed to fetch facilities: " + e.getMessage());
        }
    }
}