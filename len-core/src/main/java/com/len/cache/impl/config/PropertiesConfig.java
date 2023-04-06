package com.len.cache.impl.config;

import java.io.IOException;
import java.util.Properties;

public class PropertiesConfig {

    private static final String propertiesName = "len.properties";

    private static Properties properties = new Properties();

    static {
        try {
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(propertiesName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getValue(String key) {
        return properties.getProperty(key);
    }

}
