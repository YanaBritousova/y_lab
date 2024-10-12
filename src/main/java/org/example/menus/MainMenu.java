package org.example.menus;

import org.example.controllers.Login;
import org.example.controllers.Registration;

import java.util.Scanner;

public class MainMenu {
    private static Scanner scanner = new Scanner(System.in);

    public static void showMainMenu() {
        System.out.println("\nВыберите действие:");
        System.out.println("1. Регистрация");
        System.out.println("2. Вход");
        System.out.println("3. Выход из приложения");
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
            case 1:
                Registration.registerUser();
                break;
            case 2:
                Login.loginUser();
                break;
            case 3:
                System.out.println("До свидания!");
                System.exit(0);
            default:
                System.out.println("Неверный выбор. Пожалуйста, попробуйте снова.");
        }
    }
}
