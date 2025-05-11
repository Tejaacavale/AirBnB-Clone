package com.pitchplease.facility.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pitchplease.facility.management.model.entity.Facility;

@Repository
public interface FacilityRepository extends JpaRepository<Facility, Long> {
    // You can add custom query methods here if needed
}