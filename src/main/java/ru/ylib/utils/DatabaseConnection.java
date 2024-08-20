package ru.ylib.utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;

@Component
public class DatabaseConnection {

    private final HikariDataSource dataSource;

    public DatabaseConnection() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(ConfigLoader.getProperty("db.url"));
        config.setUsername(ConfigLoader.getProperty("db.username"));
        config.setPassword(ConfigLoader.getProperty("db.password"));
        config.setDriverClassName(ConfigLoader.getProperty("db.driver"));
        config.setMaximumPoolSize(Integer.parseInt(ConfigLoader.getProperty("db.max.pool.size")));
        config.setConnectionTimeout(Integer.parseInt(ConfigLoader.getProperty("db.conn.timeout")));
        config.setIdleTimeout(Integer.parseInt(ConfigLoader.getProperty("db.idle.timeout")));
        dataSource = new HikariDataSource(config);
    }

//    public DatabaseConnection(String url, String user, String password) {
//        HikariConfig config = new HikariConfig();
//        config.setJdbcUrl(url);
//        config.setUsername(user);
//        config.setPassword(password);
//        config.setDriverClassName(ConfigLoader.getProperty("db.driver"));
//        config.setMaximumPoolSize(Integer.parseInt(ConfigLoader.getProperty("db.max.pool.size")));
//        config.setConnectionTimeout(Integer.parseInt(ConfigLoader.getProperty("db.conn.timeout")));
//        config.setIdleTimeout(Integer.parseInt(ConfigLoader.getProperty("db.idle.timeout")));
//        dataSource = new HikariDataSource(config);
//    }

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
