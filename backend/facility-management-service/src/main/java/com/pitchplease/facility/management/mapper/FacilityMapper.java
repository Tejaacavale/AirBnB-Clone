package com.pitchplease.facility.management.mapper;

import org.springframework.stereotype.Component;

import com.pitchplease.facility.management.model.dto.FacilityDto;
import com.pitchplease.facility.management.model.entity.Facility;

@Component
public class FacilityMapper {
    
    public FacilityDto toDto(Facility entity) {
        if (entity == null) {
            return null;
        }
        
        FacilityDto dto = new FacilityDto();
        dto.setFacilityId(entity.getFacilityId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setAddress(entity.getAddress());
        dto.setCity(entity.getCity());
        dto.setFacilityType(entity.getFacilityType());
        dto.setHourlyRate(entity.getHourlyRate());
        dto.setOwnerId(entity.getOwnerId());
        
        return dto;
    }
    
    public Facility toEntity(FacilityDto dto) {
        if (dto == null) {
            return null;
        }
        
        Facility entity = new Facility();
        entity.setFacilityId(dto.getFacilityId());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setAddress(dto.getAddress());
        entity.setCity(dto.getCity());
        entity.setFacilityType(dto.getFacilityType());
        entity.setHourlyRate(dto.getHourlyRate());
        entity.setOwnerId(dto.getOwnerId());
        
        return entity;
    }
}