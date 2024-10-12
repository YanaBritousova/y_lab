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

class DeleteHabitsTest {
    @Test
    void deleteHabit(){
        User user = new User(1L,"Yana","yar@mail.com","lll", Role.USER);
        Habit habit = new Habit(1L,1L,"Reading", "Read for 30 minutes", Frequency.DAILY, LocalDate.of(2024, 10, 12).atStartOfDay());
        user.getHabits().add(habit);
        Scanner mockScanner = Mockito.mock(Scanner.class);
        ManageHabits.scanner = mockScanner;
        when(mockScanner.nextInt()).thenReturn(1);
        ManageHabits.deleteHabit(user);
        assert(user.getHabits().isEmpty());
    }

}