package com.osrs.pod.database.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.sqlite.SQLiteConfig;

import javax.sql.DataSource;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
@ComponentScan(basePackages = {"com.osrs.pod.database"})
public class DatabaseConfig {


    @Bean
    public DataSource dataSource() {
        System.out.println("Initializing dataSource with SQLiteConfig");
        SQLiteConfig sqliteConfig = new SQLiteConfig();
        sqliteConfig.setReadOnly(false); // Set to false for write operations
//        sqliteConfig.setBusyTimeout(5000); // Set busy timeout for locks

        HikariConfig hikariConfig = new HikariConfig();
        try{
            Path dbPath = Paths.get(getClass().getResource("/gebuddyResource.db").toURI());
            hikariConfig.setJdbcUrl("jdbc:sqlite:" + dbPath.toString());
        }catch (URISyntaxException e) {
            throw new RuntimeException("Failed to locate the SQLite database file.", e);
        }

        hikariConfig.setDriverClassName("org.sqlite.JDBC");
        hikariConfig.setMaximumPoolSize(1);
        hikariConfig.setConnectionTimeout(30000);

        hikariConfig.setDataSourceProperties(sqliteConfig.toProperties());

        return new HikariDataSource(hikariConfig);
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}
