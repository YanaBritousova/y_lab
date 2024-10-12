package org.example;

import lombok.Getter;
import lombok.Setter;
import org.example.menus.MainMenu;
import org.example.models.User;
import java.util.HashMap;
import java.util.Map;

public class HabitTracker {

    @Getter
    @Setter
    static Map<Long, User> users = new HashMap<>();
    @Getter
    @Setter
    static Long nextUserId = 1L;

    public static void main(String[] args){
        System.out.println("Добро пожаловать в Habit Tracker!");
        while (true) {
            MainMenu.showMainMenu();

        }
    }

}

