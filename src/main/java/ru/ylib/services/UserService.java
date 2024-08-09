package ru.ylib.services;

import ru.ylib.models.User;
import ru.ylib.models.UserRole;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.ylib.Main.logger;

/**
 * This class implements the CRUDService interface for User objects.
 */
public class UserService implements CRUDService<User> {

    private final Map<Long, User> userMap = new HashMap<>();

    /**
     * Creates a new User object and adds it to the DataStore.
     *
     * @param user The User object to create.
     * @return The created User object.
     */
    @Override
    public User create(User user) {
        userMap.put(user.getId(), user);
        logger.info("User created: {}", user);
        return user;
    }

    /**
     * Retrieves a User object from the DataStore by its ID.
     *
     * @param id The ID of the User object to retrieve.
     * @return The User object with the specified ID, or null if not found.
     */
    @Override
    public User read(long id) {
        logger.info("User read: {}", id);
        return userMap.get(id);
    }

    /**
     * Updates a User object in the DataStore.
     *
     * @param user The User object to update.
     * @return The updated User object, or null if not found.
     */
    @Override
    public User update(User user) {
        if (userMap.containsKey(user.getId())) {
            userMap.put(user.getId(), user);
            logger.info("User updated: {}", user);
            return user;
        }
        return null;
    }

    /**
     * Deletes a User object from the DataStore by its ID.
     *
     * @param id The ID of the User object to delete.
     */
    @Override
    public void delete(long id) {
        userMap.remove(id);
        logger.info("User deleted: {}", id);
    }

    /**
     * Retrieves all User objects from the DataStore.
     *
     * @return A list of all User objects.
     */
    @Override
    public List<User> readAll() {
        logger.info("View all users");
        return List.copyOf(userMap.values());
    }

    /**
     * Registers a new User object in the DataStore.
     *
     * @param login The login of the User object.
     * @param password The password of the User object.
     * @param role The role of the User object.
     * @return True if the registration was successful, false if the login is already taken.
     */
    public boolean register(String login, String password, UserRole role) {
        logger.info("Try register user with login: {}", login);
        if (login == null || password == null) {
            return false;
        }

        if (findByLogin(login) != null) {
            return false;
        } else {
            User user = new User(login, password, role);
            userMap.put(user.getId(), user);
            logger.info("User registered: {}", user);
            return true;
        }
    }

    /**
     * Authenticates a User object in the DataStore.
     *
     * @param login The login of the User object.
     * @param password The password of the User object.
     * @return The authenticated User object, or null if authentication failed.
     */
    public User authenticate(String login, String password) {
        logger.info("Try authenticate user with login: {}", login);
        return userMap.values().stream().filter(user -> user.getLogin().equals(login) && user.getPassword().equals(password)).findFirst().orElse(null);
    }

    /**
     * Retrieves a User object from the DataStore by its login.
     *
     * @param login The login of the User object to retrieve.
     * @return The User object with the specified login, or null if not found.
     */
    public User findByLogin(String login) {
        logger.info("Try find user by login: {}", login);
        return userMap.values().stream().filter(user -> user.getLogin().equals(login)).findFirst().orElse(null);
    }
}
