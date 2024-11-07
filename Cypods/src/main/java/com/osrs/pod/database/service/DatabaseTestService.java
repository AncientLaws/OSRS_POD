package com.osrs.pod.database.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DatabaseTestService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DatabaseTestService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void testConnection() {
        try {
//            insertSampleData();
            // Query to list all tables in the SQLite database
            List<Map<String, Object>> tables = jdbcTemplate.queryForList("SELECT name FROM sqlite_master WHERE type='table'");
            System.out.println("Database connection successful. Tables in the database: " + tables);

            // If there are specific tables, you can also check if they contain data
            if (!tables.isEmpty()) {
                List<Map<String, Object>> results = jdbcTemplate.queryForList("SELECT * FROM main.items LIMIT 5");
                System.out.println("Sample data from 'items' table: " + results);
            }
        } catch (Exception e) {
            System.err.println("Failed to connect or retrieve data: " + e.getMessage());
        }
    }

    public void insertSampleData() {
        jdbcTemplate.update("INSERT INTO items (id, item_name, item_examine) VALUES (?, ?, ?)", 1, "Sample Item", "This is a test item.");
    }
}