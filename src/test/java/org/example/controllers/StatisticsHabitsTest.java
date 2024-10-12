package org.example.controllers;

import org.example.enums.Frequency;
import org.example.models.Habit;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class StatisticsHabitsTest {

    @Test
    public void testCalculateStreak_Daily_ThreeDaysCompleted() {
        Habit habit = new Habit(1L,1L,"Reading", "Read for 30 minutes", Frequency.DAILY, LocalDate.of(2024, 10, 11).atStartOfDay());
        habit.setCurrentStreak(1);
        habit.getCompletionDates().add(LocalDate.of(2024, 10, 11));
        habit.getCompletionDates().add(LocalDate.of(2024, 10, 12));
        long currentStreak = ManageHabits.calculateStreak(habit);
        assertEquals(2, currentStreak);
    }
    @Test
    public void testCalculateStreak_Daily_OneDayCompleted() {
        Habit habit = new Habit(1L,1L,"Reading", "Read for 30 minutes", Frequency.DAILY, LocalDate.of(2024, 10, 12).atStartOfDay());
        habit.getCompletionDates().add(LocalDate.of(2024, 10, 12));
        long currentStreak = ManageHabits.calculateStreak(habit);
        assertEquals(1, currentStreak);
    }
    @Test
    public void testCalculateStreak_Weekly_HabitCompletedThisWeek() {
        Habit habit = new Habit(1L,1L,"Exercise", "Go for a run", Frequency.WEEKLY, LocalDateTime.now());

        habit.getCompletionDates().add(LocalDate.now());

        long currentStreak = ManageHabits.calculateStreak(habit);
        assertEquals(1, currentStreak);
    }

    @Test
    public void testCalculateStreak_Weekly_HabitCompletedLastWeek() {
        Habit habit = new Habit(1L,1L,"Exercise", "Go for a run", Frequency.WEEKLY, LocalDate.of(2024, 10, 1).atStartOfDay());

        habit.getCompletionDates().add(LocalDate.of(2024, 10, 2));
        habit.setCurrentStreak(1);
        habit.getCompletionDates().add(LocalDate.now());

        long currentStreak = ManageHabits.calculateStreak(habit);
        assertEquals(2, currentStreak);
    }

}
