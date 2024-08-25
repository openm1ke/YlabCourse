package ru.ylib.dto;

import jakarta.validation.constraints.*;

import lombok.Data;

@Data
public class UserDTO {
    private long id;
    @NotNull(message = "Login cannot be null")
    @Size(min = 3, max = 50, message = "Login must be between 3 and 50 characters")
    private String login;
    @NotNull(message = "Password cannot be null")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;
    @NotNull(message = "Role cannot be null")
    private String role;

    public UserDTO() {
    }

    public UserDTO(long id, String login, String password, String role) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.role = role;
    }
}