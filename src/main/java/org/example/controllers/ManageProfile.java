package org.example.controllers;

import org.example.HabitTracker;
import org.example.models.User;

import java.util.Scanner;


public class ManageProfile {
    static Scanner scanner = new Scanner(System.in);

    /**
     * Управление профилем.
     *
     * @param user Текущий пользователь.
     */
    public static void manageProfile(User user) {
        while (true) {
            User curUser = HabitTracker.getUsers().values().stream().filter(u->u.getId().equals(user.getId())).findAny().orElse(null);
            if (curUser==null) return;
            System.out.println("\nУправление профилем:");
            System.out.println("1. Изменить имя");
            System.out.println("2. Изменить email");
            System.out.println("3. Изменить пароль");
            System.out.println("4. Удалить аккаунт");
            System.out.println("5. Просмотреть данные аккаунта");
            System.out.println("6. Назад");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    changeName(user);
                    break;
                case 2:
                    changeEmail(user);
                    break;
                case 3:
                    changePassword(user);
                    break;
                case 4:
                    deleteAccount(user);
                    break;
                case 5:
                    System.out.println("Имя: " + user.getName() + ", Email: " + user.getEmail() + ", Пароль: " + user.getPassword());
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Неверный выбор. Пожалуйста, попробуйте снова.");
            }
        }
    }

    public static void deleteAccount(User user) {
        System.out.println("Вы действительно хотите удалить аккаунт? (да/нет)");
        String confirm = scanner.nextLine();
        if (confirm.equalsIgnoreCase("да")) {
            HabitTracker.getUsers().remove(user.getId());
            System.out.println("Аккаунт удален.");
            return;
        }
    }

    public static void changePassword(User user) {
        System.out.print("Введите текущий пароль: ");
        String currentPassword = scanner.nextLine();
        if (!user.getPassword().equals(currentPassword)) {
            System.out.println("Неверный пароль.");
            return;
        }
        System.out.print("Введите новый пароль: ");
        String newPassword = scanner.nextLine();
        user.setPassword(newPassword);
        System.out.println("Пароль изменен.");
    }

    public static void changeEmail(User user) {
        System.out.print("Введите новый email: ");
        String newEmail = scanner.nextLine();
        if (HabitTracker.getUsers().values().stream().anyMatch(u -> u.getEmail().equals(newEmail))) {
            System.out.println("Пользователь с таким email уже существует.");
            return;
        }
        if (!isValidEmail(newEmail)){
            System.out.println("Некорректный email");
            return;
        }
        user.setEmail(newEmail);
        System.out.println("Email изменен.");
    }

    public static void changeName(User user) {
        System.out.print("Введите новое имя: ");
        String newName = scanner.nextLine();
        user.setName(newName);
        System.out.println("Имя изменено.");
    }

    private static boolean isValidEmail(String email) {
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        return email.matches(regex);
    }
}
