package ru.ylib.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Database connection utility class.
 */
public class DatabaseConnection {
    static {
        try {
            Class.forName(ConfigLoader.getProperty("db.driver"));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Database driver not found", e);
        }
    }

    /**
     * Gets a connection to the database.
     * @param url
     * @param user
     * @param password
     * @return
     * @throws SQLException
     */
    public static Connection getConnection(String url, String user, String password) throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    /**
     * Gets a connection to the database.
     * @return
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        String url = ConfigLoader.getProperty("db.url");
        String user = ConfigLoader.getProperty("db.username");
        String password = ConfigLoader.getProperty("db.password");
        return DriverManager.getConnection(url, user, password);
    }
}
