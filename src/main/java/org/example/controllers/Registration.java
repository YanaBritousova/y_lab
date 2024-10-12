package org.example.controllers;

import org.example.HabitTracker;
import org.example.enums.Role;
import org.example.models.User;

import java.util.Scanner;

public class Registration {
    private static Scanner scanner = new Scanner(System.in);

    public static void registerUser() {
        System.out.println("\nРегистрация:");
        System.out.print("Введите email: ");
        String email = scanner.nextLine();
        if (isValidEmail(email)) {
            if (HabitTracker.getUsers().values().stream().anyMatch(user -> user.getEmail().equals(email))) {
                System.out.println("Пользователь с таким email уже существует.");
                return;
            }
            System.out.print("Введите пароль: ");
            String password = scanner.nextLine();
            System.out.print("Введите имя: ");
            String name = scanner.nextLine();
            if (HabitTracker.getUsers().isEmpty()) {
                HabitTracker.getUsers().put(HabitTracker.getNextUserId(), new User(HabitTracker.getNextUserId(), name, email, password, Role.ADMIN));
            } else {
                HabitTracker.getUsers().put(HabitTracker.getNextUserId(), new User(HabitTracker.getNextUserId(), name, email, password, Role.USER));
            }
            HabitTracker.setNextUserId(HabitTracker.getNextUserId()+1);
            System.out.println("Регистрация завершена успешно.");
        } else {
            System.out.println("Некорректный email");
        }
    }

    private static boolean isValidEmail(String email) {
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        return email.matches(regex);
    }
}
