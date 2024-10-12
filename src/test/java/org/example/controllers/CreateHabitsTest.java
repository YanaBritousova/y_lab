package org.example.controllers;

import org.example.enums.Frequency;
import org.example.enums.Role;
import org.example.models.Habit;
import org.example.models.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class CreateHabitsTest {
    @Test
    void createHabit() {
        User user = new User(1L, "John Doe", "john.doe@example.com", "password", Role.USER);

        Scanner mockScanner = Mockito.mock(Scanner.class);
        when(mockScanner.nextLine()).thenReturn("Reading").thenReturn("Read for 30 minutes");
        when(mockScanner.nextInt()).thenReturn(1);
        ManageHabits.scanner = mockScanner;
        ManageHabits.createHabit(user);

        assertEquals(1, user.getHabits().size());
        Habit createdHabit = user.getHabits().get(0);
        assertEquals(1L, createdHabit.getId());
        assertEquals(1L, createdHabit.getUserId());
        assertEquals("Reading", createdHabit.getName());
        assertEquals("Read for 30 minutes", createdHabit.getDescription());
        assertEquals(Frequency.DAILY, createdHabit.getFrequency());
        assertNotNull(createdHabit.getCreateDate());
    }
}
