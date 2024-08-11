package ru.ylib;

import java.sql.Connection;
import java.sql.SQLException;

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
    private Connection connection;
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
            connection = DatabaseConnection.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }

        userService = new UserService(connection);
    }

    @AfterEach
    void tearDown() {
        try {
            if (connection != null) {
                connection.close();
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

        createdUser.setLogin("updateduser");
        createdUser.setPassword("updatedpass");
        createdUser.setRole(UserRole.ADMIN);
        User updatedUser = userService.update(createdUser);

        Assertions.assertNotNull(updatedUser);
        Assertions.assertEquals("updateduser", updatedUser.getLogin());
        Assertions.assertEquals("updatedpass", updatedUser.getPassword());
        Assertions.assertEquals(UserRole.ADMIN, updatedUser.getRole());

        userService.delete(updatedUser.getId());

    }
}