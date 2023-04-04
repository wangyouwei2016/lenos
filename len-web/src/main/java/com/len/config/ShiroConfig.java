package com.len.config;

import java.util.*;

import javax.servlet.Filter;

import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.eis.MemorySessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

import com.len.cache.CacheManagerFactory;
import com.len.cache.impl.config.LenProp;
import com.len.core.BlogRealm;
import com.len.core.MyBasicHttpAuthenticationFilter;
import com.len.core.filter.PermissionFilter;
import com.len.core.filter.VerfityCodeFilter;
import com.len.core.shiro.LoginRealm;
import com.len.core.shiro.RetryLimitCredentialsMatcher;
import com.len.menu.LoginType;

/**
 * 添加redis缓存，支持集群 默认redis缓存，如果单机配置可放开下面 ehcache
 */
@Configuration
public class ShiroConfig {

    private static String sessionCache;

    static {
        sessionCache = LenProp.getSessionCache();
    }

    @Bean
    public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    private static RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(CacheManagerFactory.getRedisManager());
        return redisSessionDAO;
    }

    public static DefaultWebSessionManager getDefaultWebSessionManager() {
        DefaultWebSessionManager defaultWebSessionManager = new DefaultWebSessionManager();
        defaultWebSessionManager.setSessionIdCookieEnabled(true);
        defaultWebSessionManager.setGlobalSessionTimeout(21600000);
        // defaultWebSessionManager.setGlobalSessionTimeout(10000L);
        defaultWebSessionManager.setDeleteInvalidSessions(true);
        defaultWebSessionManager.setSessionValidationSchedulerEnabled(true);
        defaultWebSessionManager.setSessionIdUrlRewritingEnabled(false);
        defaultWebSessionManager.setSessionDAO(sessionDao());
        return defaultWebSessionManager;
    }

    /**
     * session 储存对象
     *
     * @return
     */
    private static SessionDAO sessionDao() {
        SessionDAO sessionDAO;
        switch (sessionCache) {
            case "redis":
                sessionDAO = redisSessionDAO();
                break;
            default:
                sessionDAO = new MemorySessionDAO();
        }
        return sessionDAO;
    }

    public RetryLimitCredentialsMatcher getRetryLimitCredentialsMatcher() {
        RetryLimitCredentialsMatcher rm = new RetryLimitCredentialsMatcher(CacheManagerFactory.getCacheManager());
        rm.setHashAlgorithmName("md5");
        rm.setHashIterations(4);
        return rm;

    }

    @Bean(name = "userLoginRealm")
    public LoginRealm getLoginRealm() {
        LoginRealm realm = new LoginRealm();
        realm.setCredentialsMatcher(getRetryLimitCredentialsMatcher());
        return realm;
    }

    @Bean(name = "blogLoginRealm")
    public BlogRealm blogLoginRealm() {
        return new BlogRealm();
    }

    @Bean
    public AtLeastOneSuccessfulStrategy getAtLeastOneSuccessfulStrategy() {
        return new AtLeastOneSuccessfulStrategy();
    }

    @Bean
    public MyModularRealmAuthenticator getMyModularRealmAuthenticator() {
        MyModularRealmAuthenticator authenticator = new MyModularRealmAuthenticator();
        authenticator.setAuthenticationStrategy(getAtLeastOneSuccessfulStrategy());
        return authenticator;
    }

    @Bean(name = "securityManager")
    public SecurityManager getSecurityManager(@Qualifier("userLoginRealm") LoginRealm loginRealm,
        @Qualifier("blogLoginRealm") BlogRealm blogLoginRealm) {
        DefaultWebSecurityManager dwm = new DefaultWebSecurityManager();
        List<Realm> loginRealms = new ArrayList<>();
        dwm.setAuthenticator(getMyModularRealmAuthenticator());
        loginRealm.setName(LoginType.SYS.toString());
        blogLoginRealm.setName(LoginType.BLOG.toString());
        loginRealms.add(loginRealm);
        loginRealms.add(blogLoginRealm);
        dwm.setRealms(loginRealms);
        dwm.setCacheManager(CacheManagerFactory.getCacheManager());
        dwm.setSessionManager(getDefaultWebSessionManager());
        return dwm;
    }

    @Bean
    public PermissionFilter getPermissionFilter() {
        return new PermissionFilter();
    }

    @Bean
    public MyBasicHttpAuthenticationFilter getAuthenticationFilter() {
        return new MyBasicHttpAuthenticationFilter();
    }

    @Bean
    public VerfityCodeFilter getVerfityCodeFilter() {
        VerfityCodeFilter vf = new VerfityCodeFilter();
        vf.setFailureKeyAttribute("shiroLoginFailure");
        vf.setJcaptchaParam("code");
        vf.setVerfitiCode(true);
        return vf;
    }

    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean
        getShiroFilterFactoryBean(@Qualifier("securityManager") SecurityManager securityManager) {
        ShiroFilterFactoryBean sfb = new ShiroFilterFactoryBean();
        sfb.setSecurityManager(securityManager);
        sfb.setLoginUrl("/login");
        sfb.setUnauthorizedUrl("/goLogin");
        Map<String, Filter> filters = new HashMap<>();
        filters.put("per", getPermissionFilter());
        filters.put("verCode", getVerfityCodeFilter());
        filters.put("jwt", getAuthenticationFilter());
        sfb.setFilters(filters);
        Map<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/login", "verCode,anon");
        filterMap.put("/blogLogin", "verCode,anon");
        filterMap.put("/getCode", "anon");
        filterMap.put("/oauth", "anon");
        filterMap.put("/actuator/**", "anon");
        filterMap.put("/eureka/**", "anon");
        filterMap.put("/img/**", "anon");
        filterMap.put("/logout", "logout");
        filterMap.put("/plugin/**", "anon");
        filterMap.put("/user/**", "per");
        filterMap.put("/blog-admin/**", "jwt");
        filterMap.put("/blog/**", "anon");
        filterMap.put("/**", "authc");
        sfb.setFilterChainDefinitionMap(filterMap);
        return sfb;
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor
        getAuthorizationAttributeSourceAdvisor(@Qualifier("securityManager") SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor as = new AuthorizationAttributeSourceAdvisor();
        as.setSecurityManager(securityManager);
        return as;
    }

    @Bean
    public FilterRegistrationBean delegatingFilterProxy() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        DelegatingFilterProxy proxy = new DelegatingFilterProxy();
        proxy.setTargetFilterLifecycle(true);
        proxy.setTargetBeanName("shiroFilter");
        filterRegistrationBean.setFilter(proxy);
        return filterRegistrationBean;
    }

}
