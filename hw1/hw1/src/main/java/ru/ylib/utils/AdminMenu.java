package ru.ylib.utils;

import java.util.Scanner;

public class AdminMenu {
    private final Scanner scanner;
    private final CarManager carManager;
    private final OrderManager orderManager;
    private final UserManager userManager;

    public AdminMenu(Menu menu) {
        this.scanner = menu.getScanner();
        this.carManager = new CarManager(menu);
        this.orderManager = new OrderManager(menu);
        this.userManager = new UserManager(menu);
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
                    userManager.manageUsers();
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
