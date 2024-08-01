package ru.ylib.models;

import ru.ylib.utils.IdGenerator;

public class User {

    private final long id;
    private final String login;
    private final String password;
    private final UserRole role;

    public User(String login, String password, UserRole role) {
        this.id = IdGenerator.generateUserId();
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public UserRole getRole() {
        return role;
    }

    public long getId() {
        return id;
    }

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
