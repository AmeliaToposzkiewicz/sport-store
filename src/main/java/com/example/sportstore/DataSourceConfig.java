package com.example.sportstore;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfig {
    @Bean
    public DataSource getDataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("com.mysql.cj.jdbc.Driver");
        dataSourceBuilder.url(System.getenv("DB_URL"));
        dataSourceBuilder.username(System.getenv("DB_USER"));
        dataSourceBuilder.password(System.getenv("DB_PASSWORD"));
        return dataSourceBuilder.build();
    }
}
