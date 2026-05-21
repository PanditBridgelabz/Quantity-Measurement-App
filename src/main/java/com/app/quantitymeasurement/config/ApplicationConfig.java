package com.app.quantitymeasurement.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApplicationConfig {
    private static ApplicationConfig instance;
    private final Properties props = new Properties();

    private ApplicationConfig() {
        try (InputStream in = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            if (in != null) props.load(in);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load application.properties", e);
        }
    }

    public static ApplicationConfig getInstance() {
        if (instance == null) instance = new ApplicationConfig();
        return instance;
    }

    public String getUrl() { return System.getProperty("db.url", props.getProperty("db.url")); }
    public String getUser() { return System.getProperty("db.user", props.getProperty("db.user")); }
    public String getPassword() { return System.getProperty("db.password", props.getProperty("db.password")); }
    public int getPoolSize() { return Integer.parseInt(System.getProperty("db.poolSize", props.getProperty("db.poolSize","5"))); }
}