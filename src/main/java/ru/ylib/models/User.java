package ru.ylib.models;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class User {

    private long id;
    private String login;
    private String password;
    private UserRole role;
}