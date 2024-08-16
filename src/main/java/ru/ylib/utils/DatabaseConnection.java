package ru.ylib.utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Database connection utility class.
 */
public class DatabaseConnection {

    private final HikariDataSource dataSource;

    public DatabaseConnection() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(ConfigLoader.getProperty("db.url"));
        config.setUsername(ConfigLoader.getProperty("db.username"));
        config.setPassword(ConfigLoader.getProperty("db.password"));

        config.setMaximumPoolSize(10); // Количество соединений в пуле
        config.setConnectionTimeout(30000); // Таймаут соединения
        config.setIdleTimeout(600000); // Время простоя соединения

        dataSource = new HikariDataSource(config);
    }

    public DatabaseConnection(String url, String user, String password) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(user);
        config.setPassword(password);

        config.setMaximumPoolSize(10); // Количество соединений в пуле
        config.setConnectionTimeout(30000); // Таймаут соединения
        config.setIdleTimeout(600000); // Время простоя соединения

        dataSource = new HikariDataSource(config);
    }

    public Connection getConnection() throws SQLException {
        if (dataSource == null || dataSource.isClosed()) {
            throw new SQLException("Database connection is not initialized");
        }
        return dataSource.getConnection();
    }

    public void closeConnection() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }
}
