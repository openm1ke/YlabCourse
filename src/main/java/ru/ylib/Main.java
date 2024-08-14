package ru.ylib;

import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.ylib.services.CarService;
import ru.ylib.services.OrderService;
import ru.ylib.services.UserService;
import ru.ylib.utils.DatabaseConnection;
import ru.ylib.utils.Menu;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

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

            executeSqlFile("init-db.sql", connection);

            Liquibase liquibase = new Liquibase(
                    "db/changelog/db.changelog-master.yaml",
                    new ClassLoaderResourceAccessor(),
                    new JdbcConnection(connection)
            );

            liquibase.update(new Contexts());

            userService = new UserService(connection);
            carService = new CarService(connection);
            orderService = new OrderService(connection);
        } catch (SQLException e) {
            logger.error("Failed to connect to the database", e);
            return;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (LiquibaseException e) {
            throw new RuntimeException(e);
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

    private static void executeSqlFile(String filePath, Connection connection) throws SQLException, IOException {
        try (Statement statement = connection.createStatement()) {
            String sql = new String(Files.readAllBytes(Paths.get(filePath)));
            statement.execute(sql);
        }
    }
}