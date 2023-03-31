package com.len.cache;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;

import com.len.cache.impl.config.LenProp;
import com.len.exception.LenException;


public class CacheManagerFactory {

    private static CacheManager cacheManager;

    static {
        String provider = LenProp.getCache();
        switch (provider) {
            case "ehcache":
                EhCacheManager ehCacheManager = new EhCacheManager();
                ehCacheManager.setCacheManagerConfigFile("classpath:ehcache/ehcache.xml");
                cacheManager = ehCacheManager;
                break;
            case "redis":
                RedisCacheManager redisCacheManager = new RedisCacheManager();
                redisCacheManager.setRedisManager(getRedisManager());
                redisCacheManager.setPrincipalIdFieldName("id");
                cacheManager = redisCacheManager;
                break;
            default:
                throw new LenException(String.format("不支持的缓存 [%]", provider));

        }
    }

    public static CacheManager getCacheManager() {
        return cacheManager;
    }

    public static RedisManager getRedisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(String.format("%s:%s", LenProp.getRedisHost(), LenProp.getRedisPort()));
        return redisManager;
    }
}
