package ru.ylib.models;

import ru.ylib.utils.IdGenerator;

/**
 * Represents a user in the system.
 */
public class User {

    private final long id; // Unique identifier for the user
    private String login; // User's login
    private String password; // User's password
    private UserRole role; // User's role in the system

    /**
     * Constructs a new User object with the given login, password, and role.
     * @param login User's login
     * @param password User's password
     * @param role User's role
     */
    public User(String login, String password, UserRole role) {
        this.id = IdGenerator.generateUserId(); // Generate a unique ID for the user
        this.login = login;
        this.password = password;
        this.role = role;
    }

    /**
     * Returns the user's login.
     * @return User's login
     */
    public String getLogin() {
        return login;
    }

    /**
     * Returns the user's password.
     * @return User's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Returns the user's role.
     * @return User's role
     */
    public UserRole getRole() {
        return role;
    }

    /**
     * Returns the user's unique identifier.
     * @return Unique identifier for the user
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the user's login.
     * @param login User's login
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Sets the user's password.
     * @param password User's password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Sets the user's role.
     * @param role User's role
     */
    public void setRole(UserRole role) {
        this.role = role;
    }

    /**
     * Returns a string representation of the User object.
     * @return String representation of the User object
     */
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}