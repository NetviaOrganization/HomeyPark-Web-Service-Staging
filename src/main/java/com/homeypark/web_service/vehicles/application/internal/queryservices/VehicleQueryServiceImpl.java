package com.homeypark.web_service.vehicles.application.internal.queryservices;

import com.homeypark.web_service.vehicles.domain.model.aggregates.Vehicle;
import com.homeypark.web_service.vehicles.domain.model.queries.GetAllVehiclesQuery;
import com.homeypark.web_service.vehicles.domain.model.queries.GetVehicleByIdQuery;
import com.homeypark.web_service.vehicles.domain.model.queries.GetVehiclesByProfileIdQuery;
import com.homeypark.web_service.vehicles.domain.model.valueobjects.ProfileId;
import com.homeypark.web_service.vehicles.domain.services.VehicleQueryService;
import com.homeypark.web_service.vehicles.infrastructure.persistence.jpa.repositories.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleQueryServiceImpl implements VehicleQueryService {

    private final VehicleRepository vehicleRepository;

    public VehicleQueryServiceImpl(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public Optional<Vehicle> handle(GetVehicleByIdQuery query) {
        return vehicleRepository.findById(query.vehicleId());
    }

    @Override
    public List<Vehicle> handle(GetAllVehiclesQuery query) {
        return vehicleRepository.findAll();
    }

    @Override
    public List<Vehicle> handle(GetVehiclesByProfileIdQuery query) {
        return vehicleRepository.findVehicleByProfileId(new ProfileId(query.profileId()));
    }

}