package org.example;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class HabitTracker {
    static Scanner scanner = new Scanner(System.in);
    static Map<Long, User> users = new HashMap<>();
    static Long nextUserId = 1L;

    public static void main(String[] args){
        System.out.println("Добро пожаловать в Habit Tracker!");
        while (true) {
            showMainMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    registerUser();
                    break;
                case 2:
                    loginUser();
                    break;
                case 3:
                    System.out.println("До свидания!");
                    System.exit(0);
                default:
                    System.out.println("Неверный выбор. Пожалуйста, попробуйте снова.");
            }
        }
    }

    private static void showMainMenu() {
        System.out.println("\nВыберите действие:");
        System.out.println("1. Регистрация");
        System.out.println("2. Вход");
        System.out.println("3. Выход из приложения");
    }
    private static void registerUser() {
        System.out.println("\nРегистрация:");
        System.out.print("Введите email: ");
        String email = scanner.nextLine();
        if (isValidEmail(email)) {
            if (users.values().stream().anyMatch(user -> user.getEmail().equals(email))) {
                System.out.println("Пользователь с таким email уже существует.");
                return;
            }
            System.out.print("Введите пароль: ");
            String password = scanner.nextLine();
            System.out.print("Введите имя: ");
            String name = scanner.nextLine();
            users.put(nextUserId, new User(nextUserId, name, email, password));
            nextUserId++;
            System.out.println("Регистрация завершена успешно.");
        }
        else {
            System.out.println("Некорректный email");
        }
    }
    private static boolean isValidEmail(String email) {
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        return email.matches(regex);
    }

    private static void loginUser() {
        System.out.println("\nВход:");
        System.out.print("Введите email: ");
        String email = scanner.nextLine();
        User user = users.values().stream().filter(u->u.getEmail().equals(email)).findAny().orElse(null);
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
        userMenu(user);
    }

    private static void userMenu(User user) {
        while (true) {
            if (!isUserExists(user)) return;
            System.out.println("\nМеню пользователя " + user.getEmail() + ":");
            System.out.println("1. Управление профилем");
            System.out.println("2. Управление привычками");
            System.out.println("3. Выйти из аккаунта");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    manageProfile(user);
                    break;
                case 2:
                    manageHabits(user);
                    break;
                case 3:
                    System.out.println("Выход из аккаунта.");
                    return;
                default:
                    System.out.println("Неверный выбор. Пожалуйста, попробуйте снова.");
            }
        }
    }

    private static boolean isUserExists(User user){
        User curUser = users.values().stream().filter(u->u.getId().equals(user.getId())).findAny().orElse(null);
        if (curUser==null) return false;
        return true;
    }

    private static void manageProfile(User user) {
        while (true) {
            if (!isUserExists(user)) return;
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
                    System.out.print("Введите новое имя: ");
                    String newName = scanner.nextLine();
                    user.setName(newName);
                    System.out.println("Имя изменено.");
                    break;
                case 2:
                    System.out.print("Введите новый email: ");
                    String newEmail = scanner.nextLine();
                    if (users.values().stream().anyMatch(u -> u.getEmail().equals(newEmail))) {
                        System.out.println("Пользователь с таким email уже существует.");
                        break;
                    }
                    user.setEmail(newEmail);
                    System.out.println("Email изменен.");
                    break;
                case 3:
                    System.out.print("Введите текущий пароль: ");
                    String currentPassword = scanner.nextLine();
                    if (!user.getPassword().equals(currentPassword)) {
                        System.out.println("Неверный пароль.");
                        break;
                    }
                    System.out.print("Введите новый пароль: ");
                    String newPassword = scanner.nextLine();
                    user.setPassword(newPassword);
                    System.out.println("Пароль изменен.");
                    break;
                case 4:
                    System.out.println("Вы действительно хотите удалить аккаунт? (да/нет)");
                    String confirm = scanner.nextLine();
                    if (confirm.equalsIgnoreCase("да")) {
                        users.remove(user.getId());
                        System.out.println("Аккаунт удален.");
                        break;
                    }
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
    private static void manageHabits(User user) {
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
        if (user.getHabits()!=null) {nextHabitId = Long.valueOf(user.getHabits().size() + 1);}
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

