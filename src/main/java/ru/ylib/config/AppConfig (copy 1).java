package ru.ylib.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = "ru.ylib")
@Import({WebConfig.class, DataSourceConfig.class})
public class AppConfig {
}