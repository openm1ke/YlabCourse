package ru.ylib.utils;

import ru.ylib.services.CarService;

import java.util.Scanner;

public class CarMenu {

    private final CarService carService;
    private final Scanner scanner;

    public CarMenu(CarService carService, Scanner scanner) {
        this.carService = carService;
        this.scanner = scanner;
    }


    public void showMenu() {
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
                System.out.println("create car");
                //carService.create();
                break;
            case 2:
                System.out.println("read car");
                //carService.read();
                break;
            case 3:
                System.out.println("update car");
                //carService.update();
                break;
            case 4:
                System.out.println("delete car");
                //carService.delete();
                break;
            case 5:
                System.out.println("readall cars");
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
