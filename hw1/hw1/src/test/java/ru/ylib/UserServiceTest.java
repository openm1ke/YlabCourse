package ru.ylib;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.ylib.models.User;
import ru.ylib.models.UserRole;
import ru.ylib.services.UserService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    private UserService userService;
    @BeforeEach
    void setUp() {
        userService = new UserService();
    }

    @Test
    void createAndTestUser() {
        User user = userService.create(new User("test", "test", UserRole.USER));
        assertEquals("test", user.getLogin());
        assertEquals("test", user.getPassword());
        assertEquals(UserRole.USER, user.getRole());
        assertFalse(userService.register("test", "test", UserRole.USER));
        userService.delete(user.getId());

        userService.register("test", "test", UserRole.USER);

        User user2 = userService.read(1L);
        assertNull(user2);

        user2 = userService.authenticate("test", "test");
        assertEquals("test", user2.getLogin());
        assertEquals("test", user2.getPassword());
        assertEquals(UserRole.USER, user2.getRole());

        User user3 = userService.findByLogin("test");
        assertNotNull(user3);
        User user4 = userService.findByLogin("test2");
        assertNull(user4);

        user3.setPassword("test2");
        user3.setRole(UserRole.ADMIN);
        userService.update(user3);

        user3 = userService.authenticate("test", "test2");
        assertEquals("test", user3.getLogin());
        assertEquals("test2", user3.getPassword());
        assertEquals(UserRole.ADMIN, user3.getRole());

        List<User> users = userService.readAll();
        assertEquals(1, users.size());

    }
}