package com.homeypark.web_service.core.integration.tests;

import com.homeypark.web_service.parkings.domain.model.aggregates.Parking;
import com.homeypark.web_service.parkings.domain.model.commands.CreateParkingCommand;
import com.homeypark.web_service.parkings.domain.model.entities.Location;
import com.homeypark.web_service.parkings.domain.model.queries.GetAllParkingQuery;
import com.homeypark.web_service.parkings.domain.model.valueobjects.ProfileId;
import com.homeypark.web_service.parkings.domain.services.ParkingCommandService;
import com.homeypark.web_service.parkings.domain.services.ParkingQueryService;
import com.homeypark.web_service.parkings.interfaces.rest.ParkingController;
import com.homeypark.web_service.parkings.interfaces.rest.resources.CreateLocationResource;
import com.homeypark.web_service.parkings.interfaces.rest.resources.CreateParkingResource;
import com.homeypark.web_service.parkings.interfaces.rest.resources.ParkingResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ParkingControllerIntegrationTest {

    private ParkingCommandService parkingCommandService;
    private ParkingQueryService parkingQueryService;
    private ParkingController parkingController;

    @BeforeEach
    void setUp() {
        parkingCommandService = Mockito.mock(ParkingCommandService.class);
        parkingQueryService = Mockito.mock(ParkingQueryService.class);
        parkingController = new ParkingController(parkingCommandService, parkingQueryService);
    }

    @Test
    void testCreateParkingSuccess() {
        // Arrange
        CreateLocationResource locationResource = new CreateLocationResource(
                "Av. Ejemplo", "456", "Calle Principal", "Surco", "Lima", -12.1, -77.03
        );

        CreateParkingResource resource = new CreateParkingResource(
                1L, 2.5, 5.0, 2.0, 10.0, "+987654321", 1, "Espacio techado", locationResource
        );

        Parking parking = new Parking();
        parking.setId(1L);
        parking.setPhone("+987654321");
        parking.setDescription("Espacio techado");
        parking.setProfileId(new ProfileId(1L));
        parking.setSpace(1);
        parking.setWidth(2.5);
        parking.setLength(5.0);
        parking.setHeight(2.0);
        parking.setPrice(10.0);

        Location location = new Location();
        location.setAddress("Av. Ejemplo");
        location.setNumDirection("456");
        location.setStreet("Calle Principal");
        location.setDistrict("Surco");
        location.setCity("Lima");
        location.setLatitude(-12.1);
        location.setLongitude(-77.03);
        parking.setLocation(location);
        location.setParking(parking);  // Establecer la relación bidireccional

        Mockito.when(parkingCommandService.handle(ArgumentMatchers.any(CreateParkingCommand.class)))
                .thenReturn(Optional.of(parking));

        // Act
        ResponseEntity<ParkingResource> response = parkingController.createParking(resource);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("+987654321", response.getBody().phone());
        assertEquals("Espacio techado", response.getBody().description());
    }

    @Test
    void testGetAllParkingSuccess() {
        // Arrange
        Parking parking = new Parking();
        parking.setId(1L);
        parking.setPhone("+123456789");
        parking.setDescription("Parqueo iluminado");
        parking.setProfileId(new ProfileId(1L));
        parking.setSpace(1);
        parking.setWidth(2.5);
        parking.setLength(5.0);
        parking.setHeight(2.0);
        parking.setPrice(10.0);

        Location location = new Location();
        location.setAddress("Av. Siempre Viva");
        location.setNumDirection("742");
        location.setStreet("Springfield Street");
        location.setDistrict("Springfield");
        location.setCity("Illinois");
        location.setLatitude(-12.0464);
        location.setLongitude(-77.0428);
        parking.setLocation(location);
        location.setParking(parking);  // Establecer la relación bidireccional

        Mockito.when(parkingQueryService.handle(ArgumentMatchers.any(GetAllParkingQuery.class)))
                .thenReturn(Collections.singletonList(parking));

        // Act
        ResponseEntity<?> response = parkingController.getAllParking();

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, ((java.util.List<?>) response.getBody()).size());
    }
}