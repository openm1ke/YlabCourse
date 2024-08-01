package ru.ylib.services;

import ru.ylib.models.User;
import ru.ylib.models.UserRole;
import ru.ylib.utils.DataStore;

import javax.xml.crypto.Data;
import java.util.List;

public class UserService implements CRUDService<User> {

    @Override
    public User create(User user) {
        DataStore.users.add(user);
        return user;
    }


    @Override
    public User read(long id) {
        for (User user : DataStore.users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }


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


    @Override
    public void delete(long id) {
        for (User user : DataStore.users) {
            if (user.getId() == id) {
                DataStore.users.remove(user);
                break;
            }
        }
    }


    @Override
    public List<User> readAll() {
        return DataStore.users;
    }

    public boolean register(String login, String password, UserRole role) {
        for (User user : DataStore.users) {
            if (user.getLogin().equals(login)) {
                return false;
            }
        }
        create(new User(login, password, role));
        return true;
    }


    public User authenticate(String login, String password) {
        for (User user : DataStore.users) {
            if (user.getLogin().equals(login) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public User findByLogin(String login) {
        for (User user : DataStore.users) {
            if (user.getLogin().equals(login)) {
                return user;
            }
        }
        return null;
    }
}
