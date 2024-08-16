package ru.ylib;

import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import ru.ylib.services.CarService;
import ru.ylib.services.UserService;
import ru.ylib.utils.DatabaseConnection;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CarServiceTest {
    private DatabaseConnection dbConnection;
    private PostgreSQLContainer<?> container;
    private UserService userService;
    private CarService carService;

    @BeforeEach
    void setUp() {
        container = new PostgreSQLContainer<>("postgres:latest")
                .withDatabaseName("postgres")
                .withUsername("postgres")
                .withPassword("postgres");
        container.start();

        try {
            String url = container.getJdbcUrl();
            String username = container.getUsername();
            String password = container.getPassword();
            DatabaseConnection dbConnection = new DatabaseConnection(url, username, password);
            Connection connection = dbConnection.getConnection();

            executeSqlFile("init-db.sql", connection);

            Liquibase liquibase = new Liquibase(
                    "db/changelog/db.changelog-master.yaml",
                    new ClassLoaderResourceAccessor(),
                    new JdbcConnection(connection)
            );


            liquibase.update(new Contexts());
        } catch (Exception e) {
            e.printStackTrace();
        }

        userService = new UserService(dbConnection);
        carService = new CarService(dbConnection);
    }

    @AfterEach
    void tearDown() {
        try {
            if (!dbConnection.getConnection().isClosed()) {
                dbConnection.closeConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(container != null) {
            container.stop();
        }
    }

    @Test
    void CarServiceUnitTest() {
        Assertions.assertEquals(3, carService.readAll().size());

    }

    private void executeSqlFile(String filePath, Connection connection) throws SQLException, IOException {
        try (Statement statement = connection.createStatement()) {
            String sql = new String(Files.readAllBytes(Paths.get(filePath)));
            statement.execute(sql);
        }
    }
}
