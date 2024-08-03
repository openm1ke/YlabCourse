package ru.ylib.utils;

import ru.ylib.models.*;
import ru.ylib.services.CarService;
import ru.ylib.services.OrderService;
import ru.ylib.services.UserService;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class OrderManager {
    private OrderService orderService;
    private UserService userService;
    private CarService carService;
    private Scanner scanner;
    private Menu menu;

    public OrderManager(Menu menu) {
        this.orderService = menu.getOrderService();
        this.userService = menu.getUserService();
        this.carService = menu.getCarService();
        this.scanner = menu.getScanner();
        this.menu = menu;
    }

    public void manageOrders() {
        while(true) {
            System.out.println("1. Create order");
            System.out.println("2. Read order");
            System.out.println("3. Update order");
            System.out.println("4. Delete order");
            System.out.println("5. Read all orders");
            System.out.println("6. Return to main menu");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> createOrder();
                case 2 -> readOrder();
                case 3 -> updateOrder();
                case 4 -> deleteOrder();
                case 5 -> readAllOrders();
                case 6 -> {
                    System.out.println("Back to main menu...");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }


    private void createOrder() {
        while(true) {
            System.out.println("Enter user id: ");
            long userId = scanner.nextLong();
            scanner.nextLine();
            if (userService.read(userId) == null) {
                System.out.println("User not found. Please try again.");
                continue;
            }
            System.out.println("Enter car id: ");
            long carId = scanner.nextLong();
            scanner.nextLine();
            if (carService.read(carId) == null) {
                System.out.println("Car not found. Please try again.");
                continue;
            }

            OrderStatus orderStatus = selectOrderStatus();
            OrderType orderType = selectOrderType();
            LocalDate orderDate = LocalDate.now();

            Order order = new Order(orderStatus, carId, userId, orderType, orderDate);
            orderService.create(order);
            System.out.println("Order created successfully.");
            System.out.println(order);

            break;
        }
    }

    private OrderStatus selectOrderStatus() {
        while(true) {
            System.out.println("Select order status:");

            for (int i = 0; i < OrderStatus.values().length; i++) {
                System.out.println((i + 1) + ". " + OrderStatus.values()[i]);
            }

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice >= 1 && choice <= OrderStatus.values().length) {
                return OrderStatus.values()[choice - 1];
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private OrderType selectOrderType() {
        while(true) {
            System.out.println("Select order type:");

            for (int i = 0; i < OrderType.values().length; i++) {
                System.out.println((i + 1) + ". " + OrderType.values()[i]);
            }

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice >= 1 && choice <= OrderType.values().length) {
                return OrderType.values()[choice - 1];
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void readOrder() {
        System.out.println("Enter order id:");
        long id = scanner.nextLong();
        Order order = orderService.read(id);
        if(order != null) {
            System.out.println(order);
        } else {
            System.out.println("Order not found!");
        }
    }

    private void updateOrder() {
        System.out.println("Enter order id: ");
        long id = scanner.nextLong();
        Order order = orderService.read(id);
        if(order != null) {
            System.out.println("Enter new user id: ");
            long newUserId = scanner.nextLong();
            scanner.nextLine();
            order.setUserId(newUserId);

            System.out.println("Enter new car id: ");
            long newCarId = scanner.nextLong();
            scanner.nextLine();
            order.setCarId(newCarId);

            System.out.println("Enter new order status: ");
            OrderStatus newOrderStatus = selectOrderStatus();
            order.setStatus(newOrderStatus);

            System.out.println("Enter new order type: ");
            OrderType newOrderType = selectOrderType();
            order.setType(newOrderType);

            LocalDate newOrderDate = LocalDate.now();
            order.setOrderDate(newOrderDate);

            orderService.update(order);
            System.out.println("Order updated successfully.");
            System.out.println(order);
        } else {
            System.out.println("Order not found!");
        }
    }

    private void deleteOrder() {
        System.out.println("Enter order id: ");
        long id = scanner.nextLong();
        Order order = orderService.read(id);
        if(order != null) {
            System.out.println("Order deleted successfully.");
        } else {
            System.out.println("Order not found!");
        }
    }
    private void readAllOrders() {
        List<Order> orders = orderService.readAll();
        for (Order order : orders) {
            System.out.println(order);
        }
    }
}
