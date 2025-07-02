package com.homeypark.web_service.core.integration.tests;

import com.homeypark.web_service.profiles.domain.model.aggregates.Profile;
import com.homeypark.web_service.profiles.domain.model.commands.CreateProfileCommand;
import com.homeypark.web_service.profiles.domain.model.queries.GetProfileByUserIdQuery;
import com.homeypark.web_service.profiles.domain.services.ProfileCommandService;
import com.homeypark.web_service.profiles.domain.services.ProfileQueryService;
import com.homeypark.web_service.profiles.interfaces.rest.ProfileController;
import com.homeypark.web_service.profiles.interfaces.rest.resources.CreateProfileResource;
import com.homeypark.web_service.profiles.interfaces.rest.resources.ProfileResource;
import com.homeypark.web_service.profiles.interfaces.rest.transformers.CreateProfileCommandFromResourceAssembler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ProfileControllerIntegrationTest {

    private ProfileCommandService profileCommandService;
    private ProfileQueryService profileQueryService;
    private ProfileController profileController;

    @BeforeEach
    void setUp() {
        profileQueryService = Mockito.mock(ProfileQueryService.class);
        profileCommandService = Mockito.mock(ProfileCommandService.class);
        profileController = new ProfileController(profileQueryService, profileCommandService);
    }

    @Test
    void testCreateProfileSuccess() {
        CreateProfileResource resource = new CreateProfileResource("John", "Doe", LocalDate.of(1990, 1, 1), 1L);
        CreateProfileCommand command = CreateProfileCommandFromResourceAssembler.toCommandFromResource(resource);
        Profile profile = new Profile(command);

        Mockito.when(profileCommandService.handle(ArgumentMatchers.any(CreateProfileCommand.class)))
                .thenReturn(Optional.of(profile));

        ResponseEntity<ProfileResource> response = profileController.createProfile(resource);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("John", response.getBody().firstName());
    }

    @Test
    void testGetProfileByIdSuccess() {
        Long userId = 1L;
        Profile profile = new Profile(new CreateProfileCommand("John", "Doe", LocalDate.of(1990, 1, 1), 1L));

        Mockito.when(profileQueryService.handle(ArgumentMatchers.any(GetProfileByUserIdQuery.class)))
                .thenReturn(Optional.of(profile));

        ResponseEntity<ProfileResource> response = profileController.getProfileByUserId(userId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("John", response.getBody().firstName());
    }
}