package com.homeypark.web_service.core.entities.unit.tests;

import com.homeypark.web_service.iam.domain.model.entities.Role;
import com.homeypark.web_service.iam.domain.model.valueobjects.Roles;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RoleTest {

    @Test
    void testNoArgsConstructor() {
        Role role = new Role();
        assertNull(role.getId());
        assertNull(role.getName());
    }

    @Test
    void testAllArgsConstructor() {
        Role role = new Role(1L, Roles.ROLE_ADMIN);
        assertEquals(1L, role.getId());
        assertEquals(Roles.ROLE_ADMIN, role.getName());
    }

    @Test
    void testConstructorWithEnum() {
        Role role = new Role(Roles.ROLE_HOST);
        assertNull(role.getId());
        assertEquals(Roles.ROLE_HOST, role.getName());
    }

    @Test
    void testGetStringName() {
        Role role = new Role(Roles.ROLE_GUEST);
        assertEquals("ROLE_GUEST", role.getStringName());
    }

    @Test
    void testGetDefaultRole() {
        Role defaultRole = Role.getDefaultRole();
        assertEquals(Roles.ROLE_GUEST, defaultRole.getName());
    }

    @Test
    void testToRoleFromName() {
        Role role = Role.toRoleFromName("ROLE_ADMIN");
        assertEquals(Roles.ROLE_ADMIN, role.getName());
    }

    @Test
    void testValidateRoleSetReturnsDefaultIfEmpty() {
        List<Role> validated = Role.validateRoleSet(List.of());
        assertEquals(1, validated.size());
        assertEquals(Roles.ROLE_GUEST, validated.get(0).getName());
    }

    @Test
    void testValidateRoleSetReturnsSameIfNotEmpty() {
        Role role = new Role(Roles.ROLE_HOST);
        List<Role> validated = Role.validateRoleSet(List.of(role));
        assertEquals(1, validated.size());
        assertEquals(Roles.ROLE_HOST, validated.get(0).getName());
    }
}