package ru.ylib.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ylib.dao.UserDAO;
import ru.ylib.models.User;

import java.sql.SQLException;

@Service
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public User create(User user) {
        try {
            return userDAO.create(user);
        } catch (SQLException e) {
            throw new RuntimeException("Error creating user", e);
        }
    }

    @Override
    public User read(long id) {
        try {
            return userDAO.findById(id);
        } catch (SQLException e) {
            throw new RuntimeException("Error reading user", e);
        }
    }

    @Override
    public User update(User user) {
        try {
            return userDAO.update(user);
        } catch (SQLException e) {
            throw new RuntimeException("Error updating user", e);
        }
    }

    @Override
    public void delete(long id) {
        try {
            userDAO.delete(id);
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting user", e);
        }
    }
}
