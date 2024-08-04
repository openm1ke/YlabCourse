package ru.ylib;

import ru.ylib.models.*;
import ru.ylib.services.CarService;
import ru.ylib.services.OrderService;
import ru.ylib.services.UserService;
import ru.ylib.utils.Menu;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        UserService userService = new UserService();
        CarService carService = new CarService();
        OrderService orderService = new OrderService();

        User user1 = new User("admin", "admin", UserRole.ADMIN);
        User user2 = new User("manager", "manager", UserRole.MANAGER);
        User user3 = new User("user", "user", UserRole.USER);

        userService.create(user1);
        userService.create(user2);
        userService.create(user3);

        Car car1 = new Car("BMW", "X5", 2020, 50000, CarStatus.AVAILABLE);
        Car car2 = new Car("Mercedes-Benz", "C-class", 2021, 60000, CarStatus.AVAILABLE);
        Car car3 = new Car("Toyota", "Camry", 2022, 70000, CarStatus.AVAILABLE);
        carService.create(car1);
        carService.create(car2);
        carService.create(car3);

        Order order1 = new Order(OrderStatus.CREATED, car1.getId(), user3.getId(), OrderType.BUY, LocalDate.now());
        orderService.create(order1);

        order1.setStatus(OrderStatus.COMPLETED);
        orderService.update(order1);

        car1.setStatus(CarStatus.SOLD);
        carService.update(car1);

        Menu menu = new Menu(userService, carService, orderService);
        menu.showMenu();
    }
}