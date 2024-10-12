package org.example.controllers;

import org.example.enums.Frequency;
import org.example.enums.Role;
import org.example.models.Habit;
import org.example.models.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class EditHabitsTest {

    @Test
    void editHabit_ChangeAllFields() {
        User user = new User(1L, "John Doe", "john.doe@example.com", "password", Role.USER);
        Habit habit = new Habit(1L, 1L, "Old Habit", "Old Description", Frequency.DAILY, LocalDate.of(2024, 10, 11).atStartOfDay());

        user.getHabits().add(habit);

        Scanner mockScanner = Mockito.mock(Scanner.class);
        when(mockScanner.nextInt()).thenReturn(1).thenReturn(2);
        when(mockScanner.nextLine()).thenReturn("New Name").thenReturn("New Description");
        ManageHabits.scanner = mockScanner;
        ManageHabits.editHabit(user);

        assertEquals("New Name", habit.getName());
        assertEquals("New Description", habit.getDescription());
        assertEquals(Frequency.WEEKLY, habit.getFrequency());
    }
}