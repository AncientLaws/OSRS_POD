package com.osrs.pod.database.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.sqlite.SQLiteConfig;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = {"com.osrs.pod.database"})
public class DatabaseConfig {


    @Bean
    public DataSource dataSource() {
        System.out.println("Initializing dataSource with SQLiteConfig");

        SQLiteConfig config = new SQLiteConfig();
        config.setReadOnly(false); // Explicitly set read-only to false if you need write access

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.sqlite.JDBC");
        dataSource.setUrl("jdbc:sqlite:Cypods/src/main/resources/gebuddyResource.db");
        dataSource.setConnectionProperties(config.toProperties());

        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}
