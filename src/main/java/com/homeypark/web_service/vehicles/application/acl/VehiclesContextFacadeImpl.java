package com.homeypark.web_service.vehicles.application.acl;

import com.homeypark.web_service.vehicles.domain.model.queries.GetVehicleByIdQuery;
import com.homeypark.web_service.vehicles.domain.services.VehicleQueryService;
import com.homeypark.web_service.vehicles.interfaces.acl.VehiclesContextFacade;
import org.springframework.stereotype.Service;

@Service
public class VehiclesContextFacadeImpl implements VehiclesContextFacade {
    private final VehicleQueryService vehicleQueryService;

    public VehiclesContextFacadeImpl(VehicleQueryService vehicleQueryService) {
        this.vehicleQueryService = vehicleQueryService;
    }

    @Override
    public boolean checkVehicleExistById(Long vehicleId) {
        var getVehicleByIdQuery = new GetVehicleByIdQuery(vehicleId);
        var vehicle = vehicleQueryService.handle(getVehicleByIdQuery);
        return vehicle.isPresent();
    }
}
