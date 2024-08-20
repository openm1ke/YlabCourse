package ru.ylib.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import ru.ylib.aspects.AuditAspect;
import ru.ylib.services.UserService;
import ru.ylib.services.UserServiceImpl;
import ru.ylib.servlets.UserServlet;
import ru.ylib.utils.DatabaseConnection;

@Configuration
@ComponentScan(basePackages = "ru.ylib")
@EnableAspectJAutoProxy
public class AopConfig {

    @Bean
    public DatabaseConnection databaseConnection() {
        return new DatabaseConnection();
    }

    @Bean
    public UserService userService(DatabaseConnection databaseConnection) {
        return new UserServiceImpl(databaseConnection);
    }

    @Bean
    public UserServlet userServlet() {
        return new UserServlet();
    }

    @Bean
    public AuditAspect loggingAspect() {
        return new AuditAspect();
    }

}
