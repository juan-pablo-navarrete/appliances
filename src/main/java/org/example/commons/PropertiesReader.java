package org.example.commons;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesReader {
    private static final String BASE_FILE_PATH = "./src/main/resources/";
    private static final String FILE_NAME = "application.properties";

    public static String getProperty(String propertyKey) {
        String property;
        try {
            FileInputStream inputStream = new FileInputStream(BASE_FILE_PATH.concat(FILE_NAME));
            Properties properties = new Properties();
            properties.load(inputStream);
            property = properties.getProperty(propertyKey);

        } catch (IOException exception) {
            throw new RuntimeException("Error reading properties", exception);
        }
        return property;
    }

    public static Class<?> getPropertyClass(String property) {
        String className = getProperty(property);
        try {
            return Class.forName(className);
        } catch (Exception exception) {
            throw new IllegalArgumentException("No such selector class: " + exception.getMessage(), exception);
        }
    }
}
