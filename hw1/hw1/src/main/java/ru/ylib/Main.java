package ru.ylib;

import ru.ylib.models.User;
import ru.ylib.services.CarService;
import ru.ylib.services.UserService;
import ru.ylib.utils.AdminMenu;
import ru.ylib.utils.Menu;
import ru.ylib.utils.UserMenu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        UserService userService = new UserService();
        CarService carService = new CarService();
        Scanner scanner = new Scanner(System.in);

        Menu menu = new Menu(userService, carService);

        while(true) {

            System.out.println("1. Authenticate");
            System.out.println("2. Registration");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    User user = menu.authenticateUser();
                    if (user != null) {
                        switch (user.getRole()) {
                            case ADMIN:
                                new AdminMenu(userService, carService, scanner).showMenu();
                                break;
                            case USER:
                                new UserMenu(userService, carService, scanner).showMenu();
                                break;
                            default:
                                break;
                        }
                    }
                    break;
                case 2:
                    menu.registerUser();
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
}