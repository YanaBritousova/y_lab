package org.example.controllers;

import org.example.enums.Frequency;
import org.example.models.Habit;
import org.example.models.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Scanner;

public class ManageHabits {
    static Scanner scanner = new Scanner(System.in);
    public static void manageHabits(User user) {
        while (true) {
            System.out.println("\nУправление привычками:");
            System.out.println("1. Создать привычку");
            System.out.println("2. Просмотреть привычки");
            System.out.println("3. Отметить выполнение привычки");
            System.out.println("4. Редактировать привычку");
            System.out.println("5. Удалить привычку");
            System.out.println("6. Статистика выполнения привычки");
            System.out.println("7. Назад");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    createHabit(user);
                    break;
                case 2:
                    viewHabits(user);
                    break;
                case 3:
                    markHabitCompletion(user);
                    break;
                case 4:
                    editHabit(user);
                    break;
                case 5:
                    deleteHabit(user);
                    break;
                case 6:
                    getHabitStatistics(user);
                    break;
                case 7:
                    return;
                default:
                    System.out.println("Неверный выбор. Пожалуйста, попробуйте снова.");
            }
        }
    }

    private static void getHabitStatistics(User user) {
        if (user.getHabits().isEmpty()) {
            System.out.println("У вас нет привычек.");
            return;
        }
        System.out.println("\nВыберите привычку для просмотра статистики:");
        int i = 1;
        for (Habit habit : user.getHabits()) {
            System.out.println(i + ". " + habit.getName());
            i++;
        }
        int choice = scanner.nextInt();
        scanner.nextLine();
        if (choice < 1 || choice > user.getHabits().size()) {
            System.out.println("Неверный выбор.");
            return;
        }
        Habit habitToView = user.getHabits().get(choice - 1);

        System.out.println("\nСтатистика привычки '" + habitToView.getName() + "':");

        System.out.println("Выполнение за сегодня: " + getCompletionRateForPeriod(habitToView, LocalDate.now(), LocalDate.now()));
        System.out.println("Выполнение за последнюю неделю: " + getCompletionRateForPeriod(habitToView, LocalDate.now().minusWeeks(1), LocalDate.now()));
        System.out.println("Выполнение за последний месяц: " + getCompletionRateForPeriod(habitToView, LocalDate.now().minusMonths(1), LocalDate.now()));

    }

    private static double getCompletionRateForPeriod(Habit habit, LocalDate startDate, LocalDate endDate) {
        int completionCount = 0;
        for (LocalDate mark : habit.getCompletionDates()) {
            if (mark.isAfter(startDate.minusDays(1)) && mark.isBefore(endDate.plusDays(1))) {
                completionCount++;
            }
        }
        return completionCount;
    }

    private static void markHabitCompletion(User user) {
        if (user.getHabits().isEmpty()) {
            System.out.println("Список привычек пуст");
            return;
        }
        System.out.println("\nВыберите привычку для отметки выполнения:");
        int i = 1;
        for (Habit habit : user.getHabits()) {
            System.out.println(i + ". " + habit.getName());
            i++;
        }
        int choice = scanner.nextInt();
        scanner.nextLine();
        if (choice < 1 || choice > user.getHabits().size()) {
            System.out.println("Неверный выбор.");
            return;
        }
        Habit habitToMark = user.getHabits().get(choice - 1);

        if (habitToMark.getFrequency() == Frequency.WEEKLY) {
            if (hasHabitBeenCompletedThisWeek(habitToMark)) {
                System.out.println("Вы уже отмечали выполнение этой привычки на этой неделе.");
                return;
            }
        } else if (habitToMark.getFrequency() == Frequency.MONTHLY) {
            if (hasHabitBeenCompletedThisMonth(habitToMark)) {
                System.out.println("Вы уже отмечали выполнение этой привычки в этом месяце.");
                return;
            }
        } else if (habitToMark.getFrequency() == Frequency.DAILY) {
            if (hasHabitBeenCompletedToday(habitToMark)) {
                System.out.println("Вы уже отмечали выполнение этой привычки сегодня.");
                return;
            }
        }

        habitToMark.getCompletionDates().add(LocalDate.now());
        System.out.println("Выполнение привычки '" + habitToMark.getName() + "' отмечено.");
    }

    private static boolean hasHabitBeenCompletedToday(Habit habit) {
        return habit.getCompletionDates().stream()
                .anyMatch(date -> date.isEqual(LocalDate.now()));
    }

    private static boolean hasHabitBeenCompletedThisWeek(Habit habit) {
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.minusDays(today.getDayOfWeek().getValue() - 1);
        LocalDate endOfWeek = startOfWeek.plusDays(6);

        return habit.getCompletionDates().stream()
                .anyMatch(date -> date.isAfter(startOfWeek.minusDays(1)) && date.isBefore(endOfWeek.plusDays(1)));
    }

    private static boolean hasHabitBeenCompletedThisMonth(Habit habit) {
        LocalDate today = LocalDate.now();
        LocalDate startOfMonth = today.withDayOfMonth(1);
        LocalDate endOfMonth = startOfMonth.plusMonths(1).minusDays(1);

        return habit.getCompletionDates().stream()
                .anyMatch(date -> date.isAfter(startOfMonth.minusDays(1)) && date.isBefore(endOfMonth.plusDays(1)));
    }


    private static void viewHabits(User user) {
        if (user.getHabits().isEmpty()) System.out.println("Список пуст");
        else{
            user.getHabits().stream().sorted(Comparator.comparing(Habit::getCreateDate)).forEach(System.out::println);
        }
    }

    private static void editHabit(User user) {
        if (user.getHabits().isEmpty()) {
            System.out.println("У вас нет привычек для редактирования.");
            return;
        }
        System.out.println("\nВыберите привычку для редактирования:");
        int i = 1;
        for (Habit habit : user.getHabits()) {
            System.out.println(i + ". " + habit.getName());
            i++;
        }
        int choice = scanner.nextInt();
        scanner.nextLine();
        if (choice < 1 || choice > user.getHabits().size()) {
            System.out.println("Неверный выбор.");
            return;
        }
        Habit habitToEdit = user.getHabits().get(choice - 1);
        System.out.println("\nРедактирование привычки:");
        System.out.print("Введите новое название (оставьте пустым, чтобы не менять): ");
        String newName = scanner.nextLine();
        if (!newName.isEmpty()) {
            habitToEdit.setName(newName);
        }
        System.out.print("Введите новое описание (оставьте пустым, чтобы не менять): ");
        String newDescription = scanner.nextLine();
        if (!newDescription.isEmpty()) {
            habitToEdit.setDescription(newDescription);
        }
        System.out.println("Введите частоту. ");
        System.out.println("1. Ежедневно");
        System.out.println("2. Еженедельно");
        System.out.println("3. Ежемесячно");
        int newFrequency = scanner.nextInt();
        switch(newFrequency){
            case 1:
                habitToEdit.setFrequency(Frequency.DAILY);
                break;
            case 2:
                habitToEdit.setFrequency(Frequency.WEEKLY);
                break;
            case 3:
                habitToEdit.setFrequency(Frequency.MONTHLY);
                break;
            default:
                habitToEdit.setFrequency(habitToEdit.getFrequency());
        }
        System.out.println("Привычка обновлена.");
    }

    private static void deleteHabit(User user) {
        if (user.getHabits().isEmpty()) {
            System.out.println("У вас нет привычек для удаления.");
            return;
        }
        System.out.println("\nВыберите привычку для удаления:");
        int i = 1;
        for (Habit habit : user.getHabits()) {
            System.out.println(i + ". " + habit.getName());
            i++;
        }
        int choice = scanner.nextInt();
        scanner.nextLine();
        if (choice < 1 || choice > user.getHabits().size()) {
            System.out.println("Неверный выбор.");
            return;
        }
        Habit habitToDelete = user.getHabits().get(choice - 1);
        user.getHabits().remove(habitToDelete);
        System.out.println("Привычка удалена.");
    }



    private static void createHabit(User user) {
        System.out.println("\nСоздание привычки.");
        System.out.print("Введите название привычки: ");
        String name = scanner.nextLine();
        System.out.print("Введите описание привычки: ");
        String description = scanner.nextLine();
        System.out.println("Введите частоту. ");
        System.out.println("1. Ежедневно");
        System.out.println("2. Еженедельно");
        System.out.println("3. Ежемесячно");
        int frequency = scanner.nextInt();
        Long nextHabitId=1L;
        if (user.getHabits()!=null) {nextHabitId = (long) (user.getHabits().size() + 1);}
        Habit habit = new Habit();
        switch (frequency){
            case 1:
                habit = new Habit(nextHabitId,user.getId(),name,description,Frequency.DAILY, LocalDateTime.now() );
                break;
            case 2:
                habit = new Habit(nextHabitId,user.getId(),name,description,Frequency.WEEKLY, LocalDateTime.now() );
                break;
            case 3:
                habit = new Habit(nextHabitId,user.getId(),name,description,Frequency.MONTHLY, LocalDateTime.now() );
                break;
            default:
                System.out.println("Неверный выбор. Пожалуйста, попробуйте снова.");
        }
        user.getHabits().add(habit);
        System.out.println("Привычка создана.");
    }
}
