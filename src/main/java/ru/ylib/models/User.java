package ru.ylib.models;

import lombok.*;
import ru.ylib.utils.IdGenerator;

/**
 * Represents a user in the system.
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class User {
    private final long id;
    private String login;
    private String password;
    private UserRole role;

    /**
     * Constructs a new User object with the given login, password, and role.
     * @param login User's login
     * @param password User's password
     * @param role User's role
     */
    @Builder
    public User(String login, String password, UserRole role) {
        this.id = IdGenerator.generateUserId(); // Generate a unique ID for the user
        this.login = login;
        this.password = password;
        this.role = role;
    }
}