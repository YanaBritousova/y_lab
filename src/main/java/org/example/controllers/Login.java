package org.example.controllers;

import org.example.HabitTracker;
import org.example.enums.Role;
import org.example.menus.AdminMenu;
import org.example.menus.UserMenu;
import org.example.models.User;

import java.util.Scanner;

public class Login {
    private static final Scanner scanner = new Scanner(System.in);

    public static void loginUser() {
        System.out.println("\nВход:");
        System.out.print("Введите email: ");
        String email = scanner.nextLine();
        User user = HabitTracker.getUsers().values().stream().filter(u -> u.getEmail().equals(email)).findAny().orElse(null);
        if (user == null) {
            System.out.println("Пользователь с таким email не найден.");
            return;
        }
        System.out.print("Введите пароль: ");
        String password = scanner.nextLine();
        if (!user.getPassword().equals(password)) {
            System.out.println("Неверный пароль.");
            return;
        }
        System.out.println("Вход выполнен успешно.");
        if (user.getRole() == Role.USER) {
            UserMenu.userMenu(user);
        } else if (user.getRole() == Role.ADMIN) {
            AdminMenu.adminMenu(user);
        }
    }
}
