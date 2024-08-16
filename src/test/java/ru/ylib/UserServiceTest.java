package ru.ylib;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.testcontainers.containers.PostgreSQLContainer;
import ru.ylib.models.User;
import ru.ylib.models.UserRole;
import ru.ylib.services.UserService;
import ru.ylib.utils.DatabaseConnection;

public class UserServiceTest {
    private DatabaseConnection dbConnection;
    private PostgreSQLContainer<?> container;
    private UserService userService;

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
            dbConnection = new DatabaseConnection(url, username, password);
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
    public void testCreateUser() {
        User user = new User("testuser", "testpass", UserRole.USER);
        User createdUser = userService.create(user);

        Assertions.assertNotNull(createdUser);
        Assertions.assertEquals("testuser", createdUser.getLogin());
        Assertions.assertEquals("testpass", createdUser.getPassword());
        Assertions.assertEquals(UserRole.USER, createdUser.getRole());

    }

    private void executeSqlFile(String filePath, Connection connection) throws SQLException, IOException {
        try (Statement statement = connection.createStatement()) {
            String sql = new String(Files.readAllBytes(Paths.get(filePath)));
            statement.execute(sql);
        }
    }
}