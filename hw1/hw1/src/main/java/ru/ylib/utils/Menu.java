package ru.ylib.utils;

import ru.ylib.models.User;
import ru.ylib.models.UserRole;
import ru.ylib.services.CarService;
import ru.ylib.services.OrderService;
import ru.ylib.services.UserService;

import java.util.Scanner;

public class Menu {

    private final UserService userService;
    private final CarService carService;
    private final OrderService orderService;
    private final Scanner scanner;
    private User currentUser;

    protected static final String ENTER_LOGIN = "Enter your login";
    protected static final String ENTER_PASSWORD = "Enter your password";

    public Menu(UserService userService, CarService carService, OrderService orderService) {
        this.userService = userService;
        this.carService = carService;
        this.orderService = orderService;
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        while(true) {
            int choice = 0;
            System.out.println("1. Authenticate");
            System.out.println("2. Registration");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            try {
                choice = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid choice. Please try again.");
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
                    System.out.println("Goodbye!");
                    scanner.close();
                    System.exit(0);
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    public AdminMenu showAdminMenu() {
        return new AdminMenu(this);
    }

    public UserService getUserService() {
        return userService;
    }

    public CarService getCarService() {
        return carService;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public OrderService getOrderService() {
        return orderService;
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

    public User getCurrentUser() {
        return currentUser;
    }
}
