package ru.ylib.dao;

import org.springframework.stereotype.Component;
import ru.ylib.models.User;
import ru.ylib.models.UserRole;

import javax.sql.DataSource;
import java.sql.*;

@Component
public class UserDAO {

    private final DataSource dataSource;

    public UserDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public User create(User user) throws SQLException {
        String sql = "INSERT INTO app.user (login, password, role) VALUES (?, ?, ?) RETURNING id";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getRole().name());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user.setId(resultSet.getLong("id"));
                return user;
            }
        }
        return null;
    }

    public User findById(long id) throws SQLException {
        String sql = "SELECT * FROM app.user WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                user.setRole(UserRole.valueOf(resultSet.getString("role")));
                return user;
            }
        }
        return null;
    }

    public User update(User user) throws SQLException {
        String sql = "UPDATE app.user SET login = ?, password = ?, role = ? WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getRole().name());
            statement.setLong(4, user.getId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                return user;
            }
        }
        return null;
    }

    public void delete(long id) throws SQLException {
        String sql = "DELETE FROM app.user WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        }
    }
}
