package ru.ylib.models;

import lombok.*;

/**
 * Represents a user in the system.
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class User {
    private long id;
    private String login;
    private String password;
    private UserRole role;

    @Builder
    public User(String login, String password, UserRole role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }
}