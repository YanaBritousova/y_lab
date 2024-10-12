package org.example.controllers;

import org.example.HabitTracker;
import org.example.enums.Role;
import org.example.menus.UserMenu;
import org.example.models.User;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static org.example.menus.UserMenu.*;
import static org.example.menus.UserMenu.userMenu;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoginTest {

    @Test
    void loginUser() {
        Map<Long, User> users = new HashMap<>();
        User user = new User(1L, "Test User", "test@example.com", "password", Role.USER);
        users.put(user.getId(), user);
        HabitTracker.setUsers(users);

        Scanner mockScanner = Mockito.mock(Scanner.class);
        when(mockScanner.nextLine()).thenReturn("test@example.com").thenReturn("password");
        ManageHabits.scanner = mockScanner;

        Login.loginUser();

        MockedStatic<UserMenu> userMenuMockedStatic = Mockito.mockStatic(UserMenu.class);
        userMenuMockedStatic.verify(
                () -> UserMenu.userMenu(user),
                times(1)
        );
    }
}