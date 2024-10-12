package org.example.controllers;

import org.example.HabitTracker;
import org.example.enums.Role;
import org.example.models.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ManageProfileTest {

    @Test
    void manageProfile_changeEmailToInvalid() {
        User user = new User(1L, "John Doe", "john.doe@example.com", "password", Role.USER);
        Map<Long, User> users = new HashMap<>();
        users.put(user.getId(), user);
        HabitTracker.setUsers(users);
        Scanner mockScanner = Mockito.mock(Scanner.class);
        ManageProfile.scanner = mockScanner;
        when(mockScanner.nextLine()).thenReturn("newmail");

        ManageProfile.changeEmail(user);


        assertEquals("john.doe@example.com",user.getEmail());
    }

    @Test
    void manageProfile_changePassword(){
        User user = new User(1L, "John Doe", "john.doe@example.com", "password", Role.USER);
        Map<Long, User> users = new HashMap<>();
        users.put(user.getId(), user);
        HabitTracker.setUsers(users);
        Scanner mockScanner = Mockito.mock(Scanner.class);
        ManageProfile.scanner = mockScanner;
        when(mockScanner.nextLine()).thenReturn("password").thenReturn("password1");

        ManageProfile.changePassword(user);


        assertEquals("password1",user.getPassword());
    }

}