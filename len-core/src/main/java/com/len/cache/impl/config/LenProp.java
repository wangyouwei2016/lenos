package com.len.cache.impl.config;

/**
 * len-web 下 len.properties 配置
 */
public class LenProp {

    /**
     * redis host address
     */
    private static String redisHost;
    /**
     * redis post
     */
    private static String redisPort;
    /**
     * cache
     */
    private static String cache;
    /**
     * session cache
     */
    private static String sessionCache;

    static {
        redisHost = PropertiesConfig.getValue("redis.host");
        redisPort = PropertiesConfig.getValue("redis.port");
        cache = PropertiesConfig.getValue("cache");
        sessionCache = PropertiesConfig.getValue("cache");
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

    public static String getSessionCache() {
        return sessionCache;
    }
}
