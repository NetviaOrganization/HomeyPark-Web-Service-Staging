package com.homeypark.web_service.core.entities.unit.tests;

import com.homeypark.web_service.iam.domain.model.aggregates.User;
import com.homeypark.web_service.iam.domain.model.commands.SignUpCommand;
import com.homeypark.web_service.iam.domain.model.entities.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

/**
 * This class represents the unit tests for the User aggregate.
 */
public class UserTest {
    private Role role;
    private SignUpCommand command;

    @BeforeEach
    void setUp() {
        role = mock(Role.class);
        // Corregir la creación del SignUpCommand con todos los parámetros requeridos
        command = new SignUpCommand(
                "John",                 // firstName
                "Doe",                  // lastName
                LocalDate.now(),        // birthDate
                "test@mail.com",        // email
                "Password123",          // password
                List.of(role)           // roles
        );
    }

    @Test
    void testConstructorWithCommand() {
        // Act
        User user = new User(command.email(), command.password(), command.roles());
        // Assert
        assertNotNull(user);
        assertEquals("test@mail.com", user.getEmail());
        assertEquals("Password123", user.getPassword());
        assertEquals(1, user.getRoles().size());
    }

    @Test
    void testConstructorWithFields() {
        // Act
        User user = new User("test@mail.com", "Password123", List.of(role));
        // Assert
        assertNotNull(user);
        assertEquals("test@mail.com", user.getEmail());
        assertEquals("Password123", user.getPassword());
        assertEquals(1, user.getRoles().size());
    }

    @Test
    void testGetEmail() {
        User user = new User(command.email(), command.password(), command.roles());
        assertEquals("test@mail.com", user.getEmail());
    }

    @Test
    void testSetPassword() {
        User user = new User(command.email(), command.password(), command.roles());
        user.setPassword("NewPassword456");
        assertEquals("NewPassword456", user.getPassword());
    }
}