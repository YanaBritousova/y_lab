package org.example.menus;

import org.example.HabitTracker;
import org.example.controllers.ManageProfile;
import org.example.controllers.ManageHabits;
import org.example.models.User;

import java.util.Scanner;


public class UserMenu {
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Выводит меню для пользователя.
     *
     * @param user Текущий пользователь.
     */
    public static void userMenu(User user) {
        while (true) {
            User curUser = HabitTracker.getUsers().values().stream().filter(u->u.getId().equals(user.getId())).findAny().orElse(null);
            if (curUser==null) return;
            System.out.println("\nМеню пользователя " + user.getEmail() + ":");
            System.out.println("1. Управление профилем");
            System.out.println("2. Управление привычками");
            System.out.println("3. Выйти из аккаунта");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    ManageProfile.manageProfile(user);
                    break;
                case 2:
                    ManageHabits.manageHabits(user);
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
