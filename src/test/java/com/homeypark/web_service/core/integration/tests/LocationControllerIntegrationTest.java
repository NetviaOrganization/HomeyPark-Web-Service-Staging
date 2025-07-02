package com.homeypark.web_service.core.integration.tests;

import com.homeypark.web_service.parkings.domain.model.aggregates.Parking;
import com.homeypark.web_service.parkings.domain.model.commands.UpdateLocationCommand;
import com.homeypark.web_service.parkings.domain.model.entities.Location;
import com.homeypark.web_service.parkings.domain.model.queries.GetAllLocationsQuery;
import com.homeypark.web_service.parkings.domain.services.LocationCommandService;
import com.homeypark.web_service.parkings.domain.services.LocationQueryService;
import com.homeypark.web_service.parkings.interfaces.rest.LocationController;
import com.homeypark.web_service.parkings.interfaces.rest.resources.LocationResource;
import com.homeypark.web_service.parkings.interfaces.rest.resources.UpdateLocationResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class LocationControllerIntegrationTest {

    private LocationCommandService locationCommandService;
    private LocationQueryService locationQueryService;
    private LocationController locationController;

    @BeforeEach
    void setUp() {
        locationCommandService = Mockito.mock(LocationCommandService.class);
        locationQueryService = Mockito.mock(LocationQueryService.class);
        locationController = new LocationController(locationCommandService, locationQueryService);
    }

    @Test
    void testUpdateLocationSuccess() {
        // Arrange
        Long locationId = 1L;
        UpdateLocationResource resource = new UpdateLocationResource(
                "Av. Siempre Viva", "742", "Springfield Street",
                "Springfield", "Illinois", -12.0464, -77.0428
        );

        Location updatedLocation = new Location(
                locationId,
                null, // Parking is null for now
                "Av. Siempre Viva",
                "742",
                "Springfield Street",
                "Springfield",
                "Illinois",
                -12.0464,
                -77.0428
        );

        Parking parking = Mockito.mock(Parking.class);
        Mockito.when(parking.getId()).thenReturn(100L);
        updatedLocation.setParking(parking);

        Mockito.when(locationCommandService.handle(ArgumentMatchers.any(UpdateLocationCommand.class)))
                .thenReturn(Optional.of(updatedLocation));

        // Act
        ResponseEntity<LocationResource> response = locationController.updateLocation(locationId, resource);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Av. Siempre Viva", response.getBody().address());
        assertEquals("742", response.getBody().numDirection());
        assertEquals("Illinois", response.getBody().city());
    }

    @Test
    void testGetAllLocationsSuccess() {
        // Arrange
        Long locationId = 1L;
        Location location = new Location(
                locationId,
                null, // Parking is null for now
                "Av. Siempre Viva",
                "742",
                "Springfield Street",
                "Springfield",
                "Illinois",
                -12.0464,
                -77.0428
        );

        // Mock Parking object
        Parking parking = Mockito.mock(Parking.class);
        Mockito.when(parking.getId()).thenReturn(100L);
        location.setParking(parking);

        Mockito.when(locationQueryService.handle(ArgumentMatchers.any(GetAllLocationsQuery.class)))
                .thenReturn(Collections.singletonList(location));

        // Act
        ResponseEntity<List<LocationResource>> response = locationController.getAllLocation();

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("Av. Siempre Viva", response.getBody().get(0).address());
    }
}