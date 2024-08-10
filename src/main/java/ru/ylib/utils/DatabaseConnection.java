package ru.ylib.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    static {
        try {
            Class.forName(ConfigLoader.getProperty("db.driver"));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Database driver not found", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        String url = ConfigLoader.getProperty("db.url");
        String user = ConfigLoader.getProperty("db.username");
        String password = ConfigLoader.getProperty("db.password");
        return DriverManager.getConnection(url, user, password);
    }
}
