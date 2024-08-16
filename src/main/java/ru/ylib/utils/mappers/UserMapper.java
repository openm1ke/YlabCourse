package ru.ylib.utils.mappers;

import ru.ylib.models.User;
import ru.ylib.models.UserRole;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper {
    public static User mapRowToUser(ResultSet rs) throws SQLException {
        User user = new User(rs.getString("login"), rs.getString("password"), UserRole.valueOf(rs.getString("role")));
        user.setId(rs.getLong("id"));
        return user;
    }
}
