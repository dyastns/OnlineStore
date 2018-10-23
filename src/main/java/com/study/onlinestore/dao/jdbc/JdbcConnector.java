package com.study.onlinestore.dao.jdbc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcConnector {
    private static String dbUrl;
    private static String user;
    private static String password;
    private static JdbcConnector jdbcConnector;

    private JdbcConnector(String dbUrl, String user, String password) {
        this.dbUrl = dbUrl;
        this.user = user;
        this.password = password;
    }

    public static JdbcConnector instance() {
        if (jdbcConnector == null)
            jdbcConnector = getJdbcConnector();
        return jdbcConnector;
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(dbUrl, user, password);
        } catch (SQLException e) {
            throw new RuntimeException("Cannot create connection to database", e);
        }
    }

    private static JdbcConnector getJdbcConnector() {
        Properties dbProperties = getDbConnectionProperties();
        return new JdbcConnector(dbProperties.getProperty("db.url"),
                dbProperties.getProperty("db.user"), dbProperties.getProperty("db.password"));
    }

    private static Properties getDbConnectionProperties() {
        String propertiesLocation = System.getProperty("properties.location");
        if (propertiesLocation != null) {
            File file = new File(propertiesLocation, "db.properties");
            Properties dbProperties = new Properties();
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                dbProperties.load(reader);
            } catch (Exception e) {
                throw new RuntimeException("File with properties for DB connection couldn't be read at: " + file);
            }
            return dbProperties;
        } else {
            throw new RuntimeException("Properties for DB connection was not specified.");
        }
    }
}

