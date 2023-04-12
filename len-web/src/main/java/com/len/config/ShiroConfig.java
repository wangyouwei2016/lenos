package com.len.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
import com.len.core.JwtBasicHttpAuthenticationFilter;
import com.len.core.filter.PermissionFilter;
import com.len.core.filter.VerifyCodeFilter;
import com.len.core.shiro.LoginRealm;
import com.len.core.shiro.RetryLimitCredentialsMatcher;
import com.len.menu.LoginType;

/**
 * shiro 配置 添加redis缓存，支持集群 默认redis缓存，如果单机配置可放开下面 ehcache
 */
@Configuration
public class ShiroConfig {

    private static String sessionCache;

    static {
        sessionCache = LenProp.getSessionCache();
    }

    /**
     * 利用spring 初始化 realm
     * 
     * @return
     */
    @Bean
    public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * redis 储存session配置
     * 
     * @return redisSessionDAO
     */
    private static RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(CacheManagerFactory.getRedisManager());
        return redisSessionDAO;
    }

    /**
     * shiro 默认会话管理器
     * 
     * @return
     */
    public static DefaultWebSessionManager getDefaultWebSessionManager() {
        DefaultWebSessionManager defaultWebSessionManager = new DefaultWebSessionManager();
        defaultWebSessionManager.setSessionIdCookieEnabled(true);
        defaultWebSessionManager.setGlobalSessionTimeout(21600000);
        defaultWebSessionManager.setDeleteInvalidSessions(true);
        defaultWebSessionManager.setSessionValidationSchedulerEnabled(true);
        defaultWebSessionManager.setSessionIdUrlRewritingEnabled(false);
        defaultWebSessionManager.setSessionDAO(sessionDao());
        return defaultWebSessionManager;
    }

    /**
     * 可配置化 session储存
     *
     * @return sessionDAO
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

    /**
     * 凭证
     * 
     * @return
     */
    public RetryLimitCredentialsMatcher getRetryLimitCredentialsMatcher() {
        RetryLimitCredentialsMatcher rm = new RetryLimitCredentialsMatcher(CacheManagerFactory.getCacheManager());
        rm.setHashAlgorithmName("md5");
        rm.setHashIterations(4);
        return rm;

    }

    /**
     * 自定义普通登录 realm
     * 
     * @return LoginRealm
     */
    @Bean(name = "loginRealm")
    public LoginRealm getLoginRealm() {
        LoginRealm realm = new LoginRealm();
        realm.setCredentialsMatcher(getRetryLimitCredentialsMatcher());
        return realm;
    }

    /**
     * 博客认证 realm
     * 
     * @return BlogRealm
     */
    @Bean(name = "blogLoginRealm")
    public BlogRealm blogLoginRealm() {
        return new BlogRealm();
    }

    /**
     * 认证策略
     * 
     * @return AtLeastOneSuccessfulStrategy
     */
    @Bean
    public AtLeastOneSuccessfulStrategy getAtLeastOneSuccessfulStrategy() {
        return new AtLeastOneSuccessfulStrategy();
    }

    /**
     * 多模块认证
     * 
     * @return
     */
    @Bean
    public LenModularRealmAuthenticator getMyModularRealmAuthenticator() {
        LenModularRealmAuthenticator authenticator = new LenModularRealmAuthenticator();
        authenticator.setAuthenticationStrategy(getAtLeastOneSuccessfulStrategy());
        return authenticator;
    }

    /**
     * SecurityManager 配置
     * 
     * @param loginRealm loginRealm
     * @param blogLoginRealm blogLoginRealm
     * @return
     */
    @Bean(name = "securityManager")
    public SecurityManager getSecurityManager(@Qualifier("loginRealm") LoginRealm loginRealm,
        @Qualifier("blogLoginRealm") BlogRealm blogLoginRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        List<Realm> loginRealms = new ArrayList<>();
        securityManager.setAuthenticator(getMyModularRealmAuthenticator());
        loginRealm.setName(LoginType.SYS.toString());
        blogLoginRealm.setName(LoginType.BLOG.toString());
        loginRealms.add(loginRealm);
        loginRealms.add(blogLoginRealm);
        securityManager.setRealms(loginRealms);
        securityManager.setCacheManager(CacheManagerFactory.getCacheManager());
        securityManager.setSessionManager(getDefaultWebSessionManager());
        return securityManager;
    }

    /**
     * 用户登录状态拦截器
     * 
     * @return PermissionFilter
     */
    @Bean
    public PermissionFilter getPermissionFilter() {
        return new PermissionFilter();
    }

    /**
     * 博客登录状态拦截器
     * 
     * @return JwtBasicHttpAuthenticationFilter
     */
    @Bean
    public JwtBasicHttpAuthenticationFilter getAuthenticationFilter() {
        return new JwtBasicHttpAuthenticationFilter();
    }

    /**
     * 验证码拦截
     * 
     * @return VerfityCodeFilter
     */
    @Bean
    public VerifyCodeFilter getVerfityCodeFilter() {
        VerifyCodeFilter vf = new VerifyCodeFilter();
        vf.setFailureKeyAttribute("loginFailure");
        vf.setJcaptchaParam("code");
        vf.setEnableVerifyCode(LenProp.getEnableVerifyCode());
        return vf;
    }

    /**
     * 注册 ShiroFilterFactoryBean
     * 
     * @param securityManager
     * @return
     */
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

    /**
     * shiro委托代理
     * 
     * @return filterRegistrationBean
     */
    @Bean
    public FilterRegistrationBean<?> delegatingFilterProxy() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        DelegatingFilterProxy proxy = new DelegatingFilterProxy();
        proxy.setTargetFilterLifecycle(true);
        proxy.setTargetBeanName("shiroFilter");
        filterRegistrationBean.setFilter(proxy);
        return filterRegistrationBean;
    }

}
