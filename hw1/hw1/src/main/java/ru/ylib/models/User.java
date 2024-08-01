package ru.ylib.models;

import ru.ylib.utils.IdGenerator;

public class User {

    private final long id;
    private String login;
    private String password;
    private UserRole role;

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


    public void setLogin(String login) {
        this.login = login;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public void setRole(UserRole role) {
        this.role = role;
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
