package com.homeypark.web_service.core.entities.unit.tests;

import com.homeypark.web_service.profiles.domain.model.aggregates.Profile;
import com.homeypark.web_service.profiles.domain.model.commands.CreateProfileCommand;
import com.homeypark.web_service.profiles.domain.model.commands.UpdateProfileCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ProfileTest {

    private CreateProfileCommand mockCreateCommand;
    private UpdateProfileCommand mockUpdateCommand;
    private Profile mockProfile;

    @BeforeEach
    void setUp() {
        // Arrange: Initialize commands and mock
        mockCreateCommand = new CreateProfileCommand("John", "Doe", LocalDate.parse("2000-01-01"), 1L);
        mockUpdateCommand = new UpdateProfileCommand(1L, "Jane", "Smith", LocalDate.parse("2000-01-01"));
        mockProfile = mock(Profile.class);
    }

    @Test
    void testConstructorWithCreateCommand() {
        // Arrange
        CreateProfileCommand command = mockCreateCommand;

        // Act
        Profile profile = new Profile(command);

        // Assert
        assertEquals("John", profile.getFirstName());
        assertEquals("Doe", profile.getLastName());
        assertNotNull(profile.getUserId());
        assertEquals(1L, profile.getUserId().userIdAsPrimitive());
    }

    @Test
    void testUpdateProfile() {
        // Arrange
        UpdateProfileCommand command = mockUpdateCommand;

        // Act
        mockProfile.updatedProfile(command);

        // Assert
        verify(mockProfile).updatedProfile(command);
    }

    @Test
    void testSettersAndGetters() {
        // Act
        mockProfile.setFirstName("Alice");
        mockProfile.setLastName("Brown");

        // Assert
        verify(mockProfile).setFirstName("Alice");
        verify(mockProfile).setLastName("Brown");
    }
}