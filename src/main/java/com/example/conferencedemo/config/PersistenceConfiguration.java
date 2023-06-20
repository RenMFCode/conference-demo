package com.example.conferencedemo.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class PersistenceConfiguration {
    // Used to manage database transactions
    @Bean
    public DataSource dataSource() {
        DataSourceBuilder builder = DataSourceBuilder.create();
        // TODO check how to read from properties the file
        builder.url("jdbc:postgresql://localhost:5432/session_speakers");
        builder.username("postgres");
        builder.password("admin");
        System.out.println("My custom datasource bean has been initialized and set");
        return builder.build();
    }
}
