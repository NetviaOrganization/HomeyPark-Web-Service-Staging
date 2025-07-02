package com.homeypark.web_service.core.entities.unit.tests;

import com.homeypark.web_service.parkings.domain.model.aggregates.Parking;
import com.homeypark.web_service.parkings.domain.model.commands.CreateLocationCommand;
import com.homeypark.web_service.parkings.domain.model.commands.CreateParkingCommand;
import com.homeypark.web_service.parkings.domain.model.entities.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ParkingTest {

    private Parking parking;
    private Location mockLocation;
    private CreateParkingCommand mockCommand;

    @BeforeEach
    void setUp() {
        // Arrange: Crear mocks y objetos necesarios
        mockLocation = mock(Location.class);
        CreateLocationCommand locationCommand = new CreateLocationCommand(
                "Test Address", "123", "Test Street", "Test District", "Test City", 12.34, 56.78
        );
        mockCommand = new CreateParkingCommand(
                1L, 2.5, 5.0, 3.0, 10.0, "123456789", 1, "Test Description", locationCommand
        );

        parking = new Parking();
    }

    @Test
    void testConstructorWithCommand() {
        // Arrange
        CreateParkingCommand command = mockCommand;

        // Act
        Parking parking = new Parking(command);

        // Assert
        assertNotNull(parking.getLocation());
        assertEquals(1L, parking.getProfileId().profileIdAsPrimitive());
        assertEquals(2.5, parking.getWidth());
        assertEquals(5.0, parking.getLength());
        assertEquals(3.0, parking.getHeight());
        assertEquals(10.0, parking.getPrice());
        assertEquals("123456789", parking.getPhone());
        assertEquals(1, parking.getSpace());
        assertEquals("Test Description", parking.getDescription());
        assertEquals("Test Address", parking.getLocation().getAddress());
    }

    @Test
    void testSettersAndGetters() {
        // Arrange
        Parking parking = new Parking();

        // Act
        parking.setWidth(2.5);
        parking.setLength(5.0);
        parking.setHeight(3.0);
        parking.setPrice(10.0);
        parking.setPhone("123456789");
        parking.setSpace(1);
        parking.setDescription("Test Description");

        // Assert
        assertEquals(2.5, parking.getWidth());
        assertEquals(5.0, parking.getLength());
        assertEquals(3.0, parking.getHeight());
        assertEquals(10.0, parking.getPrice());
        assertEquals("123456789", parking.getPhone());
        assertEquals(1, parking.getSpace());
        assertEquals("Test Description", parking.getDescription());
    }

    @Test
    void testSetAndGetLocation() {
        // Arrange
        when(mockLocation.getAddress()).thenReturn("Mock Address");

        // Act
        parking.setLocation(mockLocation);

        // Assert
        assertNotNull(parking.getLocation());
        assertEquals("Mock Address", parking.getLocation().getAddress());
        verify(mockLocation, times(1)).getAddress();
    }
}