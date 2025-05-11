package com.pitchplease.facility.discovery.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pitchplease.facility.discovery.model.dto.FacilityDto;
import com.pitchplease.facility.discovery.service.FacilityService;

@RestController
// @RequestMapping("/facilities_discovery")
public class FacilityController {

    @Autowired
    private FacilityService facilityService;
    
    /**
     * Get all facilities
     * This is the endpoint that will be called from the API Gateway
     * 
     * @return List of all facilities
     */
    @GetMapping("/all")
    public ResponseEntity<List<FacilityDto>> getAllFacilities() {
        System.out.println("l36 facility controller.java ");
        List<FacilityDto> facilities = facilityService.getAllFacilities();
        System.out.println("l38 facilities = " + facilities);
        return new ResponseEntity<>(facilities, HttpStatus.OK);
    }
    
    /**
     * Get facility by ID
     * 
     * @param id Facility ID
     * @return Facility if found
     */
    @GetMapping("/{id}")
    public ResponseEntity<FacilityDto> getFacilityById(@PathVariable Long id) {
        Optional<FacilityDto> facility = facilityService.getFacilityById(id);
        
        if (facility.isPresent()) {
            return new ResponseEntity<>(facility.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    /**
     * Create a new facility
     * 
     * @param facilityDto Facility to be created
     * @return Created facility
     */
    @PostMapping
    public ResponseEntity<FacilityDto> createFacility(@RequestBody FacilityDto facilityDto) {
        FacilityDto newFacility = facilityService.createFacility(facilityDto);
        return new ResponseEntity<>(newFacility, HttpStatus.CREATED);
    }
    
    /**
     * Update an existing facility
     * 
     * @param id Facility ID
     * @param facilityDto Updated facility details
     * @return Updated facility
     */
    @PutMapping("/{id}")
    public ResponseEntity<FacilityDto> updateFacility(@PathVariable Long id, @RequestBody FacilityDto facilityDto) {
        try {
            FacilityDto updatedFacility = facilityService.updateFacility(id, facilityDto);
            return new ResponseEntity<>(updatedFacility, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    /**
     * Delete a facility
     * 
     * @param id Facility ID
     * @return No content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFacility(@PathVariable Long id) {
        try {
            facilityService.deleteFacility(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}