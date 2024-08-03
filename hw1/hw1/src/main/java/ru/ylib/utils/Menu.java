package ru.ylib.utils;

import ru.ylib.models.User;
import ru.ylib.models.UserRole;
import ru.ylib.services.CarService;
import ru.ylib.services.UserService;

import java.util.Scanner;

public class Menu {

    private final UserService userService;
    private final CarService carService;
    private final Scanner scanner;

    protected static final String ENTER_LOGIN = "Enter your login";
    protected static final String ENTER_PASSWORD = "Enter your password";

    public Menu(UserService userService, CarService carService) {
        this.userService = userService;
        this.carService = carService;
        this.scanner = new Scanner(System.in);

        initializeUsers();
    }

    private void initializeUsers() {
        User user1 = new User("admin", "admin", UserRole.ADMIN);
        User user2 = new User("user", "user", UserRole.USER);
        User user3 = new User("manager", "manager", UserRole.MANAGER);
        userService.create(user1);
        userService.create(user2);
        userService.create(user3);
    }

    public void registerUser() {
        while(true) {
            System.out.println(ENTER_LOGIN);
            String login = scanner.nextLine();
            System.out.println(ENTER_PASSWORD);
            String password = scanner.nextLine();
            // по умолчанию регистрируем обычного пользователя
            boolean registered = userService.register(login, password, UserRole.USER);
            if (registered) {
                System.out.println("User registered successfully.");
                return;
            } else {
                System.out.println("User with this login already exists. Please try again.");
            }
        }
    }

    public User authenticateUser() {
        while(true) {
            System.out.println(ENTER_LOGIN);
            String login = scanner.nextLine();
            System.out.println(ENTER_PASSWORD);
            String password = scanner.nextLine();
            User user = userService.authenticate(login, password);
            if (user != null) {
                return user;
            }
            System.out.println("Invalid login or password. Please try again.");
        }
    }
}
