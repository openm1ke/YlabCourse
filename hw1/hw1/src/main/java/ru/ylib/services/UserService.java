package ru.ylib.services;

import ru.ylib.models.User;
import ru.ylib.models.UserRole;
import ru.ylib.utils.DataStore;

import javax.xml.crypto.Data;
import java.util.List;
/**
 * This class implements the CRUDService interface for User objects.
 */
public class UserService implements CRUDService<User> {

    /**
     * Creates a new User object and adds it to the DataStore.
     *
     * @param user The User object to create.
     * @return The created User object.
     */
    @Override
    public User create(User user) {
        DataStore.users.add(user);
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
        for (User user : DataStore.users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    /**
     * Updates a User object in the DataStore.
     *
     * @param user The User object to update.
     * @return The updated User object, or null if not found.
     */
    @Override
    public User update(User user) {
        for (User u : DataStore.users) {
            if (u.getId() == user.getId()) {
                u.setLogin(user.getLogin());
                u.setPassword(user.getPassword());
                u.setRole(user.getRole());
                return u;
            }
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
        for (User user : DataStore.users) {
            if (user.getId() == id) {
                DataStore.users.remove(user);
                break;
            }
        }
    }

    /**
     * Retrieves all User objects from the DataStore.
     *
     * @return A list of all User objects.
     */
    @Override
    public List<User> readAll() {
        return DataStore.users;
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
        for (User user : DataStore.users) {
            if (user.getLogin().equals(login)) {
                return false;
            }
        }
        create(new User(login, password, role));
        return true;
    }

    /**
     * Authenticates a User object in the DataStore.
     *
     * @param login The login of the User object.
     * @param password The password of the User object.
     * @return The authenticated User object, or null if authentication failed.
     */
    public User authenticate(String login, String password) {
        for (User user : DataStore.users) {
            if (user.getLogin().equals(login) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Retrieves a User object from the DataStore by its login.
     *
     * @param login The login of the User object to retrieve.
     * @return The User object with the specified login, or null if not found.
     */
    public User findByLogin(String login) {
        for (User user : DataStore.users) {
            if (user.getLogin().equals(login)) {
                return user;
            }
        }
        return null;
    }
}
