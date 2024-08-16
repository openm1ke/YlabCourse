package ru.ylib.dto;

import lombok.Data;

@Data
public class UserDTO {
    private long id;
    private String login;
    private String password;
    private String role; // например, "USER", "ADMIN" и т.д.
}