package org.example.controllers;

import org.example.HabitTracker;
import org.example.models.User;

import java.util.Scanner;


public class ManageUsers {
    static Scanner scanner = new Scanner(System.in);
    public static void manageUsers(User user) {
        if (HabitTracker.getUsers().values().size()==1) System.out.println("Список пользователей пуст.");
        else {
            System.out.println("Выберите пользователя, которого хотите удалить:");
            HabitTracker.getUsers().values().stream()
                    .filter(u -> !u.getId().equals(user.getId()))
                    .forEach(u -> System.out.println(u.getId() + ". " + u.getName()));
            int choice = scanner.nextInt();
            HabitTracker.getUsers().remove(Long.valueOf(choice));
            System.out.println("Пользователь успешно удалён");

        }
    }
}
