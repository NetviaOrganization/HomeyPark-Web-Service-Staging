package com.homeypark.web_service.core.integration.tests;

import com.homeypark.web_service.vehicles.domain.model.aggregates.Vehicle;
import com.homeypark.web_service.vehicles.domain.model.commands.CreateVehicleCommand;
import com.homeypark.web_service.vehicles.domain.model.queries.GetVehicleByIdQuery;
import com.homeypark.web_service.vehicles.domain.services.VehicleCommandService;
import com.homeypark.web_service.vehicles.domain.services.VehicleQueryService;
import com.homeypark.web_service.vehicles.interfaces.rest.VehicleController;
import com.homeypark.web_service.vehicles.interfaces.rest.resources.CreateVehicleResource;
import com.homeypark.web_service.vehicles.interfaces.rest.resources.VehicleResource;
import com.homeypark.web_service.vehicles.interfaces.rest.transform.CreateVehicleCommandFromResourceAssembler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class VehicleControllerIntegrationTest {

    private VehicleCommandService vehicleCommandService;
    private VehicleQueryService vehicleQueryService;
    private VehicleController vehicleController;

    @BeforeEach
    void setUp() {
        vehicleQueryService = Mockito.mock(VehicleQueryService.class);
        vehicleCommandService = Mockito.mock(VehicleCommandService.class);
        vehicleController = new VehicleController(vehicleQueryService, vehicleCommandService);
    }

    @Test
    void testCreateVehicleSuccess() {
        CreateVehicleResource resource = new CreateVehicleResource("ABC123", "ModelX", "Tesla", 42L);
        CreateVehicleCommand command = CreateVehicleCommandFromResourceAssembler.toCommandFromResource(resource);
        Vehicle vehicle = new Vehicle(command);

        Mockito.when(vehicleCommandService.handle(ArgumentMatchers.any(CreateVehicleCommand.class)))
                .thenReturn(Optional.of(vehicle));
        // The controller expects the command service to return Optional<Vehicle>
        // The assembler converts Vehicle to VehicleResource

        ResponseEntity<VehicleResource> response = vehicleController.createVehicle(resource);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("ABC123", response.getBody().licensePlate());
    }

    @Test
    void testGetVehicleByIdSuccess() {
        Long vehicleId = 1L;
        Vehicle vehicle = new Vehicle("ABC123", "ModelX", "Tesla", 42L);

        Mockito.when(vehicleQueryService.handle(ArgumentMatchers.any(GetVehicleByIdQuery.class)))
                .thenReturn(Optional.of(vehicle));

        ResponseEntity<VehicleResource> response = vehicleController.getVehicleById(vehicleId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("ABC123", response.getBody().licensePlate());
    }
}