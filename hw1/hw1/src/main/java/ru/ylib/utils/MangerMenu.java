package ru.ylib.utils;

import ru.ylib.services.CarService;

import java.util.Scanner;

public class MangerMenu {

    private CarService carService;
    private Scanner scanner;
    private CarManager carManager;


    public MangerMenu(CarService carService, Scanner scanner) {
        this.carService = carService;
        this.scanner = scanner;
        this.carManager = new CarManager(carService, scanner);
    }

    public void showMenu() {
        while (true) {
            System.out.println("1. Manage cars");
            System.out.println("2. Manage orders");
            System.out.println("3. Return to main menu");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    carManager.manageCars();
                    break;
                case 2:
                    //manageOrders();
                    break;
                case 3:
                    System.out.println("Back to main menu...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }
}
