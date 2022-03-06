package com.len.cache.impl.config;

public class LenProp {

    private static String redisHost;
    private static String redisPort;
    private static String cache;

     static {
        redisHost = PropertiesConfig.getValue("redis.host");
        redisPort = PropertiesConfig.getValue("redis.port");
        cache = PropertiesConfig.getValue("cache");
    }

    public static String getRedisHost() {
        return redisHost;
    }

    public static String getRedisPort() {
        return redisPort;
    }

    public static String getCache() {
        return cache;
    }
}
