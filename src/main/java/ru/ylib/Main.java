package ru.ylib;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.ylib.config.AppConfig;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        DataSource dataSource = context.getBean(DataSource.class);

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT * FROM app.car");
            while (resultSet.next()) {
                System.out.println("Car ID: " + resultSet.getInt("id"));
                System.out.println("Brand: " + resultSet.getString("brand"));
                System.out.println("Model: " + resultSet.getString("model"));
                System.out.println("Year: " + resultSet.getInt("year"));
                System.out.println("Price: " + resultSet.getBigDecimal("price"));
                System.out.println("Status: " + resultSet.getString("status"));
                System.out.println("-------------------------------");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        context.close();
    }
}
