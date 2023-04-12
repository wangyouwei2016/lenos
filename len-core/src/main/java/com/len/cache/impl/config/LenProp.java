package com.len.cache.impl.config;

import org.apache.commons.lang3.StringUtils;

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

    /**
     * 验证码
     */
    private static boolean enableVerifyCode;

    static {
        redisHost = PropertiesConfig.getValue("redis.host");
        redisPort = PropertiesConfig.getValue("redis.port");
        cache = PropertiesConfig.getValue("cache");
        sessionCache = PropertiesConfig.getValue("cache");
        enableVerifyCode = Boolean.parseBoolean(PropertiesConfig.getValue("enableVerifyCode"));
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

    /**
     * 获取缓存配置
     * @return
     */
    public static String getSessionCache() {
        return sessionCache;
    }

    /**
     * 获取验证码配置
     * @return true false
     */
    public static boolean getEnableVerifyCode() {
        return enableVerifyCode;
    }
}
