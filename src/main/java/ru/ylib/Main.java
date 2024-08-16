package ru.ylib;

import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.extern.slf4j.Slf4j;
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

        UserService userService = null;
        CarService carService = null;
        OrderService orderService = null;
        // Инициализация DatabaseConnection
        DatabaseConnection dbConnection = new DatabaseConnection();

        try (Connection connection = dbConnection.getConnection()) {

            executeSqlFile("init-db.sql", connection);

            Liquibase liquibase = new Liquibase(
                    "db/changelog/db.changelog-master.yaml",
                    new ClassLoaderResourceAccessor(),
                    new JdbcConnection(connection)
            );

            liquibase.update(new Contexts());

            userService = new UserService(dbConnection);
            carService = new CarService(dbConnection);
            orderService = new OrderService(dbConnection);
        } catch (SQLException e) {
            logger.error("Failed to connect to the database", e);
            return;
        } catch (IOException | LiquibaseException e) {
            throw new RuntimeException(e);
        }

        // Запуск меню
        Menu menu = new Menu(userService, carService, orderService);
        menu.showMenu();

        // Закрытие подключения к базе данных
        dbConnection.closeConnection();

        logger.info("Application finished");
    }

    private static void executeSqlFile(String filePath, Connection connection) throws SQLException, IOException {
        try (Statement statement = connection.createStatement()) {
            String sql = new String(Files.readAllBytes(Paths.get(filePath)));
            statement.execute(sql);
        }
    }
}