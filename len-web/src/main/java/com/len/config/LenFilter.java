package com.len.config;

import com.len.service.SysUserService;
import com.len.util.LocalLocale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Locale;

@Component
public class LenFilter implements Filter {

    @Autowired
    SysUserService sysUserService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("FirstFilter init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        Locale locale = request.getLocale();
        if (locale.toString().contains("zh")) {
            LocalLocale.setLocale(Locale.SIMPLIFIED_CHINESE);
        } else {
            LocalLocale.setLocale(Locale.US);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }


    @Override
    public void destroy() {
        System.out.println("FirstFilter destroy");
    }
}