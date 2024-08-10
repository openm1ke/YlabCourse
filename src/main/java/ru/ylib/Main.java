package ru.ylib;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.ylib.models.*;
import ru.ylib.services.UserService;
import ru.ylib.utils.DatabaseConnection;
import java.util.List;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    public static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        logger.info("Starting application");

        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = null;
        UserService userService = null;

        try {
            connection = databaseConnection.getConnection();
            userService = new UserService(connection);
        } catch (SQLException e) {
            logger.error("Failed to connect to the database", e);
            return;
        }

        User user1 = new User("admin", "admin", UserRole.ADMIN);
        User user2 = new User("manager", "manager", UserRole.MANAGER);
        User user3 = new User("user", "user", UserRole.USER);

        userService.create(user1);
        userService.create(user2);
        userService.create(user3);

        // Вывод всех пользователей из базы данных
        List<User> users = userService.readAll();
        logger.info("Users in database:");
        for (User user : users) {
            logger.info(user.toString());
        }

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