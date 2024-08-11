package ru.ylib;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.ylib.services.CarService;
import ru.ylib.services.OrderService;
import ru.ylib.services.UserService;
import ru.ylib.utils.DatabaseConnection;
import ru.ylib.utils.Menu;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    public static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        logger.info("Starting application");

        Connection connection = null;
        UserService userService = null;
        CarService carService = null;
        OrderService orderService = null;

        try {
            connection = DatabaseConnection.getConnection();
            userService = new UserService(connection);
            carService = new CarService(connection);
            orderService = new OrderService(connection);
        } catch (SQLException e) {
            logger.error("Failed to connect to the database", e);
            return;
        }

        // Запуск меню
        Menu menu = new Menu(userService, carService, orderService);
        menu.showMenu();

        // Закрытие подключения к базе данных
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            logger.error("Failed to close the database connection", e);
        }

        logger.info("Application finished");
    }
}