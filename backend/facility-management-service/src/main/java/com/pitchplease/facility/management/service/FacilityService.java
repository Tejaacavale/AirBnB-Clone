// Path: facility-management-service/src/main/java/com/pitchplease/facility/management/service/FacilityService.java
package com.pitchplease.facility.management.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pitchplease.facility.management.mapper.FacilityMapper;
import com.pitchplease.facility.management.model.dto.FacilityDto;
import com.pitchplease.facility.management.model.entity.Facility;
import com.pitchplease.facility.management.repository.FacilityRepository;

@Service
public class FacilityService {

    @Autowired
    private FacilityRepository facilityRepository;
    
    @Autowired
    private FacilityMapper facilityMapper;
    
    /**
     * Get all facilities
     * 
     * @return List of all facilities
     */
    public List<FacilityDto> getAllFacilities() {
        return facilityRepository.findAll().stream()
                .map(facilityMapper::toDto)
                .collect(Collectors.toList());
    }
    
    /**
     * Get facility by ID
     * 
     * @param id Facility ID
     * @return Optional containing the facility if found
     */
    public Optional<FacilityDto> getFacilityById(Long id) {
        return facilityRepository.findById(id)
                .map(facilityMapper::toDto);
    }
    
    /**
     * Create a new facility
     * 
     * @param facilityDto Facility to be created
     * @return Created facility
     */
    public FacilityDto createFacility(FacilityDto facilityDto) {
        Facility entity = facilityMapper.toEntity(facilityDto);
        entity.setFacilityId(null); // Ensure we're creating a new entity
        Facility savedEntity = facilityRepository.save(entity);
        return facilityMapper.toDto(savedEntity);
    }
    
    /**
     * Update an existing facility
     * 
     * @param id Facility ID
     * @param facilityDto Updated facility details
     * @return Updated facility
     */
    public FacilityDto updateFacility(Long id, FacilityDto facilityDto) {
        Facility existing = facilityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Facility not found with id: " + id));
        
        // Update fields
        existing.setName(facilityDto.getName());
        existing.setDescription(facilityDto.getDescription());
        existing.setAddress(facilityDto.getAddress());
        existing.setCity(facilityDto.getCity());
        existing.setFacilityType(facilityDto.getFacilityType());
        existing.setHourlyRate(facilityDto.getHourlyRate());
        existing.setOwnerId(facilityDto.getOwnerId());
        
        Facility updated = facilityRepository.save(existing);
        return facilityMapper.toDto(updated);
    }
    
    /**
     * Delete a facility
     * 
     * @param id Facility ID
     */
    public void deleteFacility(Long id) {
        Facility facility = facilityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Facility not found with id: " + id));
                
        facilityRepository.delete(facility);
    }
}