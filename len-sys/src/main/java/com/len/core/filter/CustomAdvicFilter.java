package com.len.core.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

public class CustomAdvicFilter extends FormAuthenticationFilter {

    /*
    @Autowired
    private SysUserService userService;
    
    @Autowired
    private MenuService menuService;
    */

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        return true;
    }
}
