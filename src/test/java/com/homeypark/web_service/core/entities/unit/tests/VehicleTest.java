package com.homeypark.web_service.core.entities.unit.tests;

import com.homeypark.web_service.vehicles.domain.model.aggregates.Vehicle;
import com.homeypark.web_service.vehicles.domain.model.commands.CreateVehicleCommand;
import com.homeypark.web_service.vehicles.domain.model.commands.UpdateVehicleCommand;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VehicleTest {

    @Test
    void testConstructorWithFields() {
        Vehicle vehicle = new Vehicle("ABC123", "ModelX", "Tesla", 42L);
        assertNull(vehicle.getId());
        assertEquals("ABC123", vehicle.getLicensePlate());
        assertEquals("ModelX", vehicle.getModel());
        assertEquals("Tesla", vehicle.getBrand());
        assertNotNull(vehicle.getProfileId());
    }

    @Test
    void testConstructorWithCreateVehicleCommand() {
        CreateVehicleCommand command = new CreateVehicleCommand("XYZ789", "Civic", "Honda", 99L);
        Vehicle vehicle = new Vehicle(command);
        assertNull(vehicle.getId());
        assertEquals("XYZ789", vehicle.getLicensePlate());
        assertEquals("Civic", vehicle.getModel());
        assertEquals("Honda", vehicle.getBrand());
        assertNotNull(vehicle.getProfileId());
    }

    @Test
    void testUpdatedVehicle() {
        Vehicle vehicle = new Vehicle("AAA111", "Corolla", "Toyota", 1L);
        UpdateVehicleCommand updateCommand = new UpdateVehicleCommand(1L, "BBB222", "Yaris", "Toyota");
        vehicle.updatedVehicle(updateCommand);
        assertEquals("BBB222", vehicle.getLicensePlate());
        assertEquals("Yaris", vehicle.getModel());
        assertEquals("Toyota", vehicle.getBrand());
    }
}