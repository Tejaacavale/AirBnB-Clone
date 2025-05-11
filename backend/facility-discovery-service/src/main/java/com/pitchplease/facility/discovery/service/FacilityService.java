package com.pitchplease.facility.discovery.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pitchplease.facility.discovery.mapper.FacilityMapper;
import com.pitchplease.facility.discovery.model.dto.FacilityDto;
import com.pitchplease.facility.discovery.model.entity.Facility;
import com.pitchplease.facility.discovery.repository.FacilityRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Service
public class FacilityService {

    @Autowired
    private FacilityRepository facilityRepository;
    
    @Autowired
    private FacilityMapper facilityMapper;
    
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Get all facilities
     * 
     * @return List of all facilities
     */
    @Transactional(readOnly = true)
    public List<FacilityDto> getAllFacilities() {

        // // First, check the database directly using EntityManager
        // System.out.println("Querying facility table directly with EntityManager:");
        // Query query = entityManager.createNativeQuery("SELECT * FROM facilities", Facility.class);
        // @SuppressWarnings("unchecked")
        // List<Facility> entitiesFromNativeQuery = query.getResultList();
        // System.out.println("Result from native query: " + entitiesFromNativeQuery);
        // System.out.println("Number of records: " + entitiesFromNativeQuery.size());
        
        // List<Facility> facilities = facilityRepository.findAll();
        // System.out.println("l34 Facilities from DB: " + facilities);
        
        // List<FacilityDto> dtos = facilities.stream()
        //         .map(facility -> {
        //             FacilityDto dto = facilityMapper.toDto(facility);
        //             System.out.println("Mapped facility " + facility.getFacilityId() + " to DTO: " + dto);
        //             return dto;
        //         })
        //         .collect(Collectors.toList());
        // return dtos;
    
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
    @Transactional(readOnly = true)
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
    @Transactional
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
     * @throws RuntimeException if facility not found
     */
    @Transactional
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
     * @throws RuntimeException if facility not found
     */
    @Transactional
    public void deleteFacility(Long id) {
        Facility facility = facilityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Facility not found with id: " + id));
                
        facilityRepository.delete(facility);
    }
}