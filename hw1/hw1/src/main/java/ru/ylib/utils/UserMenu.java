package ru.ylib.utils;

import ru.ylib.models.*;
import ru.ylib.services.CarService;
import ru.ylib.services.OrderService;
import ru.ylib.services.UserService;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class UserMenu {

    private final OrderService orderService;
    private final UserService userService;
    private final CarService carService;
    private final Scanner scanner;
    private User currentUser;

    public UserMenu(Menu menu) {
        this.scanner = menu.getScanner();
        this.orderService = menu.getOrderService();
        this.userService = menu.getUserService();
        this.carService = menu.getCarService();
        this.currentUser = menu.getCurrentUser();
    }

    public void showMenu() {
        while (true) {
            System.out.println("Welcome, " + currentUser.getId() + "!");
            System.out.println("1. My orders");
            System.out.println("2. My cars");
            System.out.println("3. Shop cars");
            System.out.println("4. Return to main menu");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    showCurrentUserOrders();
                    break;
                case 2:
                    showCurrentUserCars();
                    break;
                case 3:
                    showShopCars();
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

    private void showShopCars() {
        List<Car> cars = carService.readAll();
        if (cars.isEmpty()) {
            System.out.println("No cars found.");
        } else {
            System.out.println("Cars:");
            for (Car car : cars) {
                // если машина не продана
                if(car.getStatus() != CarStatus.SOLD) {
                    System.out.println(car);
                }
            }

            buyNewCar();
        }
    }

    private void buyNewCar() {
        System.out.println("Enter car id to buy or 0 to menu:");
        long carId = scanner.nextLong();
        scanner.nextLine();
        if (carId == 0) {
            return;
        }
        Car car = carService.read(carId);
        if (car != null && car.getStatus() == CarStatus.AVAILABLE) {
            Order order = new Order(OrderStatus.CREATED, carId, currentUser.getId(), OrderType.BUY, LocalDate.now());
            orderService.create(order);
            car.setStatus(CarStatus.RESERVED);
            carService.update(car);
            System.out.println("Car reserved successfully. Wait till order is completed. You can cancel order before that.");
        } else {
            System.out.println("Car not found or not available.");
        }
    }

    private void showCurrentUserCars() {
        List<Order> currentUserOrders = orderService.readAll();
        if (currentUserOrders.isEmpty()) {
            System.out.println("No cars found.");
        } else {
            System.out.println("Current user cars:");
            for (Order order : currentUserOrders) {
                // если заказ найден и он от нашего пользователя и он завершен и тип заказа - покупка
                if(order.getUserId() == currentUser.getId() && order.getStatus() == OrderStatus.COMPLETED && order.getType() == OrderType.BUY) {
                    Car car = carService.read(order.getCarId());
                    System.out.println(car);
                }
            }
            showCarsTypeServices();
        }
    }

    private void showCarsTypeServices() {
        System.out.println("Enter car id to service or 0 to menu:");
        long carId = scanner.nextLong();
        scanner.nextLine();

        if (carId == 0) {
            return;
        }
        Order order = orderService.readByCarId(carId);
        if (order != null && order.getUserId() == currentUser.getId() && order.getStatus() == OrderStatus.COMPLETED) {
            Car car = carService.read(order.getCarId());
            System.out.println(car);
            // создаем заказ на обслуживание
            OrderType type = selectUserTypeService();
            Order newOrder = new Order(OrderStatus.CREATED, carId, currentUser.getId(), type, LocalDate.now());
            orderService.create(newOrder);
            System.out.println("New order to service created successfully. Wait till order is completed. You can cancel order before that.");
        } else {
            System.out.println("Car not found. Please try again.");
        }
    }

    private OrderType selectUserTypeService() {
        while (true) {
            System.out.println("Select order type:");
            // кроме покупки
            for (int i = 1; i < OrderType.values().length; i++) {
                System.out.println((i) + ". " + OrderType.values()[i]);
            }

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice >= 1 && choice <= OrderType.values().length) {
                return OrderType.values()[choice];
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void cancelUserOrder() {
        System.out.print("Enter order id to cancel or 0 to menu: ");
        long orderId = scanner.nextLong();
        scanner.nextLine();
        if(orderId == 0) {
            return;
        }
        Order order = orderService.read(orderId);
        // если заказ найден и он от нашего пользователя и не завершен
        if (order != null && order.getUserId() == currentUser.getId()) {
            if(order.getStatus() == OrderStatus.COMPLETED || order.getStatus() == OrderStatus.CANCELED) {
                System.out.println("Order already completed/canceled!");
                return;
            }
            order.setStatus(OrderStatus.CANCELED);
            orderService.update(order);

            // если это покупка мы меняем статус машины в таблице cars на available
            if(order.getType() == OrderType.BUY) {
                Car car = carService.read(order.getCarId());
                car.setStatus(CarStatus.AVAILABLE);
                carService.update(car);
            }

            System.out.println("Order canceled.");
        } else {
            System.out.println("Order not found. Please try again.");
        }

    }

    private void showCurrentUserOrders() {
        List<Order> currentUserOrders = orderService.readAll();
        if (currentUserOrders.isEmpty()) {
            System.out.println("No orders found.");
        } else {
            System.out.println("Current user orders:");
            for (Order order : currentUserOrders) {
                if(order.getUserId() == currentUser.getId()) {
                    System.out.println(order);
                }
            }
            cancelUserOrder();
        }
    }
}
