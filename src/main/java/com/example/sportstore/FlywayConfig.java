package com.example.sportstore;

import javax.sql.DataSource;

import lombok.RequiredArgsConstructor;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FlywayConfig {

    private final DataSource dataSource;

    @Bean(initMethod = "migrate")
    public Flyway flyway() {
        FluentConfiguration fluentConfiguration = Flyway.configure().dataSource(dataSource).baselineOnMigrate(true)
                .locations("classpath:db/migration");
        return new Flyway(fluentConfiguration);
    }
}
