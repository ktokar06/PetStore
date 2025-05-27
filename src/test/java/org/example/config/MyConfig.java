package org.example.config;

import io.restassured.config.Config;

import java.io.IOException;
import java.util.Properties;

public class MyConfig {
    private static final Properties props = new Properties();

    static {
        try {
            props.load(Config.class.getClassLoader().getResourceAsStream("config.properties"));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config", e);
        }
    }

    public static String getBaseUrl() {
        return props.getProperty("base.url", "https://petstore.swagger.io/v2");
    }
}