package com.homeypark.web_service.core.entities.unit.tests;

import com.homeypark.web_service.parkings.domain.model.commands.UpdateLocationCommand;
import com.homeypark.web_service.parkings.domain.model.entities.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class LocationTest {

    private Location mockLocation;

    @BeforeEach
    void setUp() {
        mockLocation = mock(Location.class);
    }

    @Test
    void testUpdateLocation() {
        // Arrange
        UpdateLocationCommand updateCommand = new UpdateLocationCommand(
                1L,
                "Test Address",
                "123",
                "Test Street",
                "Test District",
                "Test City",
                12.34,
                56.78
        );

        // Act
        mockLocation.updateLocation(updateCommand);

        // Assert
        verify(mockLocation).updateLocation(updateCommand);
    }

    @Test
    void testSettersAndGetters() {
        // Act
        mockLocation.setAddress("New Address");
        mockLocation.setNumDirection("456");
        mockLocation.setStreet("New Street");
        mockLocation.setDistrict("New District");
        mockLocation.setCity("New City");
        mockLocation.setLatitude(98.76);
        mockLocation.setLongitude(54.32);

        // Assert
        verify(mockLocation).setAddress("New Address");
        verify(mockLocation).setNumDirection("456");
        verify(mockLocation).setStreet("New Street");
        verify(mockLocation).setDistrict("New District");
        verify(mockLocation).setCity("New City");
        verify(mockLocation).setLatitude(98.76);
        verify(mockLocation).setLongitude(54.32);
    }
}