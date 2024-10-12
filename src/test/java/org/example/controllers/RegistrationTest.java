package org.example.controllers;

import org.example.HabitTracker;
import org.example.enums.Role;
import org.example.models.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class RegistrationTest {

    @Test
    void registerUser_ValidEmail() {
        Scanner mockScanner = Mockito.mock(Scanner.class);
        when(mockScanner.nextLine()).thenReturn("test@example.com").thenReturn("password").thenReturn("Test User");
        Registration.scanner = mockScanner;

        HabitTracker.getUsers().clear();
        HabitTracker.setNextUserId(1L);

        Registration.registerUser();

        assertEquals(1, HabitTracker.getUsers().size());
        User registeredUser = HabitTracker.getUsers().get(1L);
        assertEquals("Test User", registeredUser.getName());
        assertEquals("test@example.com", registeredUser.getEmail());
        assertEquals("password", registeredUser.getPassword());
        assertEquals(Role.ADMIN, registeredUser.getRole());
    }
    @Test
    void registerUser_InvalidEmail() {
        Scanner mockScanner = Mockito.mock(Scanner.class);
        when(mockScanner.nextLine()).thenReturn("invalid-email").thenReturn("password").thenReturn("Test User");
        Registration.scanner = mockScanner;
        Registration.registerUser();

        assertEquals(0, HabitTracker.getUsers().size());
    }
}