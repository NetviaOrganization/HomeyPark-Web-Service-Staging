package com.homeypark.web_service.vehicles.domain.services;

import com.homeypark.web_service.vehicles.domain.model.aggregates.Vehicle;
import com.homeypark.web_service.vehicles.domain.model.queries.GetAllVehiclesQuery;
import com.homeypark.web_service.vehicles.domain.model.queries.GetVehicleByIdQuery;
import com.homeypark.web_service.vehicles.domain.model.queries.GetVehiclesByProfileIdQuery;

import java.util.List;
import java.util.Optional;

public interface VehicleQueryService {
    Optional<Vehicle> handle(GetVehicleByIdQuery query);
    List<Vehicle> handle(GetAllVehiclesQuery query);
    List<Vehicle> handle(GetVehiclesByProfileIdQuery query);

}