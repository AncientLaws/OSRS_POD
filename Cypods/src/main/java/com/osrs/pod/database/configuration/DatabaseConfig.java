package com.osrs.pod.database.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.sqlite.SQLiteConfig;

import javax.sql.DataSource;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

@Configuration
@ComponentScan(basePackages = {"com.osrs.pod.database"})
public class DatabaseConfig {


    @Bean
    public DataSource dataSource() {
        System.out.println("Initializing dataSource with SQLiteConfig");
        SQLiteConfig sqliteConfig = new SQLiteConfig();
        sqliteConfig.setReadOnly(false);

        HikariConfig hikariConfig = new HikariConfig();
        try {
            // Define the writable directory for the database
            Path userDbPath = Paths.get(System.getProperty("user.home"), ".grandexchangecentral", "gebuddyResource.db");
            if (!Files.exists(userDbPath)) {
                // Ensure parent directory exists
                Files.createDirectories(userDbPath.getParent());

                // Copy database from resources to the writable directory
                try (InputStream is = getClass().getResourceAsStream("/gebuddyResource.db")) {
                    if (is == null) {
                        throw new RuntimeException("Database file not found in resources!");
                    }
                    Files.copy(is, userDbPath, StandardCopyOption.REPLACE_EXISTING);
                }
            }

            hikariConfig.setJdbcUrl("jdbc:sqlite:" + userDbPath.toString());
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize the SQLite database file.", e);
        }

        hikariConfig.setDriverClassName("org.sqlite.JDBC");
        hikariConfig.setMaximumPoolSize(1);
        hikariConfig.setConnectionTimeout(30000);
        hikariConfig.setDataSourceProperties(sqliteConfig.toProperties());

        return new HikariDataSource(hikariConfig);
    }

//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder, DataSource dataSource) {
//        return builder
//                .dataSource(dataSource)
//                .packages("com.osrs.pod.database.domain.entities") // Adjust package based on entities
//                .persistenceUnit("default")
//                .properties(jpaProperties())
//                .build();
//    }
//
//    private Map<String, Object> jpaProperties() {
//        Map<String, Object> properties = new HashMap<>();
//        properties.put(AvailableSettings.DIALECT, "com.osrs.pod.database.SQLDialect"); // Use your custom dialect
//        properties.put(AvailableSettings.SHOW_SQL, "true"); // Enable SQL logging
//        properties.put(AvailableSettings.FORMAT_SQL, "true");
//        properties.put(AvailableSettings.HBM2DDL_AUTO, "update"); // Adjust to "validate" if schema should not be modified
////        properties.put(AvailableSettings.TRANSACTION_COORDINATOR_STRATEGY, "jta"); // Ensure transaction consistency
//        return properties;
//    }
//
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}
