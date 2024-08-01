package ru.ylib.utils;

import ru.ylib.services.CarService;
import ru.ylib.services.UserService;

import java.util.Scanner;

public class UserMenu {

    private final UserService userService;
    private final CarService carService;
    private final Scanner scanner;

    public UserMenu(UserService userService, CarService carService, Scanner scanner) {
        this.userService = userService;
        this.carService = carService;
        this.scanner = scanner;
    }

    public void showMenu() {
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
            case 1:
                //userService.create();
                break;
            case 2:
                //userService.read();
                break;
            case 3:
                //userService.update();
                break;
            case 4:
                //userService.delete();
                break;
            case 5:
                //userService.readAll();
                break;
            case 6:
                System.out.println("Back to main menu...");
                showMenu();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                break;
        }
    }

}
