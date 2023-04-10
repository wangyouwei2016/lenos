package com.len.config;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.len.service.SysUserService;
import com.len.util.LocalLocale;

/**
 * 拦截器
 */
@Component
public class LenFilter implements Filter {

    /**
     * 支持语言 扩展语言可直接在此添加
     */
    private static Locale[] supportLocales = {Locale.SIMPLIFIED_CHINESE, Locale.US};
    @Autowired
    SysUserService sysUserService;

    /**
     * 初始化加载
     * 
     * @param filterConfig
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("FirstFilter init");
    }

    /**
     * 请求拦截
     * 
     * @param servletRequest request
     * @param servletResponse response
     * @param filterChain 过滤连
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
        throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        Locale locale = request.getLocale();
        LocalLocale.setLocale(getLocale(locale));
        filterChain.doFilter(servletRequest, servletResponse);
    }

    /**
     * 获取语言
     *
     * @param locale 地理位置对象
     * @return 匹配的Locale
     */
    private Locale getLocale(Locale locale) {
        String reqLanguage = locale.getLanguage();
        for (Locale support : supportLocales) {
            if (reqLanguage.equals(support.getLanguage())) {
                return support;
            }
        }
        return Locale.US;
    }

    /**
     * 销毁回调
     */
    @Override
    public void destroy() {
        System.out.println("FirstFilter destroy");
    }
}