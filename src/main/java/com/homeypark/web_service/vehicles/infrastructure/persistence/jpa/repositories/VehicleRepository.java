package com.homeypark.web_service.vehicles.infrastructure.persistence.jpa.repositories;

import com.homeypark.web_service.vehicles.domain.model.aggregates.Vehicle;
import com.homeypark.web_service.vehicles.domain.model.valueobjects.ProfileId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    List<Vehicle> findVehicleByProfileId(ProfileId profileId);
    boolean existsByLicensePlate(String licensePlate);
}