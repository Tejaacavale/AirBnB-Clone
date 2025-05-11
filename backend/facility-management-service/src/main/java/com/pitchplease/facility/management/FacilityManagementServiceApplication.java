package com.pitchplease.facility.management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {"com.pitchplease.facility.management.model.entity"})
public class FacilityManagementServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(FacilityManagementServiceApplication.class, args);
    }
}