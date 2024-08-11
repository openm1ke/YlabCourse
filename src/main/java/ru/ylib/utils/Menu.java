package ru.ylib.utils;

import lombok.Getter;
import ru.ylib.models.User;
import ru.ylib.models.UserRole;
import ru.ylib.services.CarService;
import ru.ylib.services.OrderService;
import ru.ylib.services.UserService;

import java.util.Optional;
import java.util.Scanner;

import static ru.ylib.Main.logger;

@Getter
public class Menu {

    private final UserService userService;
    private final CarService carService;
    private final OrderService orderService;
    private final Scanner scanner;
    private User currentUser;

    protected static final String ENTER_LOGIN = "Enter your login";
    protected static final String ENTER_PASSWORD = "Enter your password";
    protected static final String INVALID_CHOICE = "Invalid choice. Please try again.";
    protected static final String INVALID_LOGIN_OR_PASSWORD = "Invalid login or password. Please try again.";
    protected static final String GOODBYE = "Goodbye!";
    protected static final String REGISTER_SUCCESS = "User registered successfully.";
    protected static final String USER_ALREADY_EXISTS = "User with this login already exists. Please try again.";

    public Menu(UserService userService, CarService carService, OrderService orderService) {
        this.userService = userService;
        this.carService = carService;
        this.orderService = orderService;
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        logger.info("Showing Main menu");
        while(true) {
            int choice = 0;
            System.out.println("1. Authenticate");
            System.out.println("2. Registration");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            try {
                choice = scanner.nextInt();
            } catch (Exception e) {
                System.out.println(INVALID_CHOICE);
                scanner.nextLine();
                continue;
            }
            scanner.nextLine();

            switch (choice) {
                case 1:
                    User user = this.authenticateUser();
                    if (user != null) {
                        currentUser = user;
                        switch (user.getRole()) {
                            case ADMIN:
                                new AdminMenu(this).showMenu();
                                break;
                            case USER:
                                new UserMenu(this).showMenu();
                                break;
                            case MANAGER:
                                new ManagerMenu(this).showMenu();
                                break;
                            default:
                                break;
                        }
                    }
                    break;
                case 2:
                    this.registerUser();
                    break;
                case 3:
                    System.out.println(GOODBYE);
                    scanner.close();
                    System.exit(0);
                    return;
                default:
                    System.out.println(INVALID_CHOICE);
                    break;
            }
        }
    }

    public void registerUser() {
        while(true) {
            System.out.println(ENTER_LOGIN);
            String login = scanner.nextLine();
            System.out.println(ENTER_PASSWORD);
            String password = scanner.nextLine();
            // по умолчанию регистрируем обычного пользователя
            Optional<User> registered = userService.register(login, password, UserRole.USER);
            if (registered.isPresent()) {
                System.out.println(REGISTER_SUCCESS);
                return;
            } else {
                System.out.println(USER_ALREADY_EXISTS);
            }
        }
    }

    public User authenticateUser() {
        while(true) {
            System.out.println(ENTER_LOGIN);
            String login = scanner.nextLine();
            System.out.println(ENTER_PASSWORD);
            String password = scanner.nextLine();
            Optional<User> user = userService.authenticate(login, password);
            if (user.isPresent()) {
                return user.get();
            }
            System.out.println(INVALID_LOGIN_OR_PASSWORD);
        }
    }

}
