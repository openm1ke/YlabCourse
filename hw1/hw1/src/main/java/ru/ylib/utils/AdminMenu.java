package ru.ylib.utils;

import ru.ylib.models.User;
import ru.ylib.models.UserRole;
import ru.ylib.services.CarService;
import ru.ylib.services.OrderService;
import ru.ylib.services.UserService;

import java.util.List;
import java.util.Scanner;

public class AdminMenu {

    private final UserService userService;
    private final CarService carService;
    private final Scanner scanner;
    private final CarManager carManager;
    private final OrderManager orderManager;

    public AdminMenu(UserService userService, CarService carService, OrderService orderService, Scanner scanner) {
        this.userService = userService;
        this.carService = carService;
        this.scanner = scanner;
        this.orderManager = new OrderManager(orderService, carService, userService, scanner);
        this.carManager = new CarManager(carService, scanner);
    }


    public void manageUsers() {
        while(true) {
            System.out.println("1. Create user");
            System.out.println("2. Read user");
            System.out.println("3. Update user");
            System.out.println("4. Delete user");
            System.out.println("5. Read all users");
            System.out.println("6. Return to main menu");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> createUser();
                case 2 -> readUser();
                case 3 -> updateUser();
                case 4 -> deleteUser();
                case 5 -> readAllUsers();
                case 6 -> {
                    System.out.println("Back to main menu...");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void readAllUsers() {
        List<User> users = userService.readAll();
        for (User user : users) {
            System.out.println(user);
        }
    }

    private void deleteUser() {
        while(true) {
            System.out.println("Enter user login:");
            String login = scanner.nextLine();
            User user = userService.findByLogin(login);
            if (user != null) {
                userService.delete(user.getId());
                System.out.printf("User deleted successfully.");
                return;
            }
            System.out.println("User not found. Please try again.");
        }
    }
    private void updateUser() {
        while(true) {
            System.out.println("Enter user login:");
            String login = scanner.nextLine();
            User user = userService.findByLogin(login);
            if (user != null) {
                System.out.println("Enter new password:");
                String password = scanner.nextLine();
                user.setPassword(password);
                UserRole role = selectUserRole();
                user.setRole(role);
                userService.update(user);
                System.out.println("User updated successfully.");
                return;
            }
            System.out.println("User not found. Please try again.");
        }
    }

    public void readUser() {
        while(true) {
            System.out.println("Enter user login:");
            String login = scanner.nextLine();
            User user = userService.findByLogin(login);
            if (user != null) {
                System.out.println(user);
                return;
            }
            System.out.println("User not found. Please try again.");
        }
    }


    public void createUser() {
        while(true) {
            System.out.println("Enter your login:");
            String login = scanner.nextLine();
            System.out.println("Enter your password:");
            String password = scanner.nextLine();

            boolean userExists = userService.readAll().stream().anyMatch(user -> user.getLogin().equals(login));

            if (userExists) {
                System.out.println("User with this login already exists. Please try again.");
            } else {
                UserRole role = selectUserRole();
                User user = userService.create(new User(login, password, role));
                System.out.println("User created successfully.");
                System.out.println(user);
                return;
            }
        }
    }

    private UserRole selectUserRole() {
        while(true) {
            System.out.println("Select user role:");

            for (int i = 0; i < UserRole.values().length; i++) {
                System.out.println((i + 1) + ". " + UserRole.values()[i]);
            }

            int choice = scanner.nextInt();
            scanner.nextLine();


            if (choice >= 1 && choice <= UserRole.values().length) {
                return UserRole.values()[choice - 1];
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }


    public void manageCars() {
        while(true) {
            System.out.println("1. Create car");
            System.out.println("2. Read car");
            System.out.println("3. Update car");
            System.out.println("4. Delete car");
            System.out.println("5. Read all cars");
            System.out.println("6. Return to main menu");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    //carService.create();
                    break;
                case 2:
                    //carService.read();
                    break;
                case 3:
                    //carService.update();
                    break;
                case 4:
                    //carService.delete();
                    break;
                case 5:
                    //carService.readAll();
                    break;
                case 6:
                    System.out.println("Back to main menu...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }


    public void showMenu() {
        while(true) {
            System.out.println("1. Manage users");
            System.out.println("2. Manage cars");
            System.out.println("3. Manage orders");
            System.out.println("4. Return to main menu");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    manageUsers();
                    break;
                case 2:
                    carManager.manageCars();
                    break;
                case 3:
                    orderManager.manageOrders();
                    break;
                case 4:
                    System.out.println("Back to main menu...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }
}
