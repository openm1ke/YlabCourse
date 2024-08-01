package ru.ylib.utils;

import ru.ylib.services.CarService;
import ru.ylib.services.UserService;

import java.util.Scanner;

public class AdminMenu {

    private final UserService userService;
    private final CarService carService;
    private final Scanner scanner;


    public AdminMenu(UserService userService, CarService carService, Scanner scanner) {
        this.userService = userService;
        this.carService = carService;
        this.scanner = scanner;
    }


    public void showMenu() {
        while(true) {
            System.out.println("1. Manage users");
            System.out.println("2. Manage cars");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    new UserMenu(userService, carService, scanner).showMenu();
                    break;
                case 2:
                    new CarMenu(carService, scanner).showMenu();
                    break;
                case 3:
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }
}
