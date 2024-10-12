package org.example.menus;

import org.example.controllers.ManageProfile;
import org.example.controllers.ManageUsers;
import org.example.models.User;

import java.util.Scanner;

public class AdminMenu {
    private static final Scanner scanner = new Scanner(System.in);
    public static void adminMenu(User user) {
        while (true){
            System.out.println("\nМеню администратора "+user.getEmail() + ":");
            System.out.println("1. Управление профилем");
            System.out.println("2. Управление пользователями");
            System.out.println("3. Выйти из аккаунта");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    ManageProfile.manageProfile(user);
                    break;
                case 2:
                    ManageUsers.manageUsers(user);
                    break;
                case 3:
                    System.out.println("Выход из аккаунта.");
                    return;
                default:
                    System.out.println("Неверный выбор. Пожалуйста, попробуйте снова.");
            }
        }
    }
}
