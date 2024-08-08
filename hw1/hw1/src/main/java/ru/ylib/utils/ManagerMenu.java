package ru.ylib.utils;

import java.util.Scanner;

public class ManagerMenu {

    private final Scanner scanner;
    private final CarManager carManager;
    private final OrderManager orderManager;

    public ManagerMenu(Menu menu) {
        this.scanner = menu.getScanner();
        this.carManager = new CarManager(menu);
        this.orderManager = new OrderManager(menu);
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
                    orderManager.manageOrders();
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
