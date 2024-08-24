package ru.ylib.models;

import jakarta.persistence.*;
import lombok.*;

/**
 * Represents a user in the system.
 */
@Entity
@Table(name = "app.user")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String login;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;
    // пришлось добавить коструктор с айди для корректной работы маппера
    public User() {
    }

    public User(String login, String password, UserRole role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }
}