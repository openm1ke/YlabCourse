package ru.ylib.utils;

import ru.ylib.models.Car;
import ru.ylib.models.CarStatus;
import ru.ylib.models.UserRole;
import ru.ylib.services.CarService;

import java.util.List;
import java.util.Scanner;

public class CarManager {

    private final CarService carService;
    private final Scanner scanner;

    public CarManager(Menu menu) {
        this.carService = menu.getCarService();
        this.scanner = menu.getScanner();
    }

    public void manageCars() {
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
            case 1 -> createCar();
            case 2 -> readCar();
            case 3 -> updateCar();
            case 4 -> deleteCar();
            case 5 -> readAllCars();
            case 6 -> {
                System.out.println("Back to main menu...");
                return;
            }
            default -> System.out.println("Invalid choice. Please try again.");
        }
    }

    private void readAllCars() {
        List<Car> cars = carService.readAll();
        for (Car car : cars) {
            System.out.println(car);
        }
    }

    private void deleteCar() {
        System.out.println("Enter car id:");
        long carId = scanner.nextLong();
        Car car = carService.read(carId);
        if (car != null) {
            carService.delete(carId);
            System.out.println("Car deleted successfully.");
        } else {
            System.out.println("Car not found. Please try again.");
        }
    }

    private void updateCar() {
        while(true) {
            System.out.println("Enter car id:");
            long carId = scanner.nextLong();
            Car car = carService.read(carId);
            if (car != null) {
                System.out.println("Enter new car model:");
                String carModel = scanner.nextLine();

                System.out.println("Enter new car year:");
                int carYear = scanner.nextInt();
                scanner.nextLine();

                System.out.println("Enter new car price:");
                double carPrice = scanner.nextDouble();
                scanner.nextLine();

                CarStatus carStatus = selectCarStatus();

                car.setModel(carModel);
                car.setYear(carYear);
                car.setPrice(carPrice);
                car.setStatus(carStatus);
                if(carService.update(car) != null) {
                    System.out.println("Car updated successfully.");
                    System.out.println(car);
                    break;
                }
            }
            System.out.println("User not found. Please try again.");
        }
    }
    private void readCar() {
        while(true) {
            System.out.println("Enter car id:");
            long id = scanner.nextLong();
            Car car = carService.read(id);
            if (car != null) {
                System.out.println(car);
                break;
            }
            System.out.println("User not found. Please try again.");
        }
    }

    private void createCar() {
        while(true) {
            System.out.println("Enter car brand:");
            String carBrand = scanner.nextLine();
            System.out.println("Enter car model:");
            String carModel = scanner.nextLine();

            System.out.println("Enter car year:");
            int carYear = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Enter car price:");
            double carPrice = scanner.nextDouble();
            scanner.nextLine();

            CarStatus carStatus = selectCarStatus();

            Car car = new Car(carBrand, carModel, carYear, carPrice, carStatus);
            if(carService.create(car) != null) {
                System.out.println("Car created successfully.");
                System.out.println(car);
                break;
            }
            System.out.println("Car not created. Please try again.");
        }
    }

    private CarStatus selectCarStatus() {
        while(true) {
            System.out.println("Select car status:");

            for (int i = 0; i < CarStatus.values().length; i++) {
                System.out.println((i + 1) + ". " + CarStatus.values()[i]);
            }

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice >= 1 && choice <= UserRole.values().length) {
                return CarStatus.values()[choice - 1];
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
