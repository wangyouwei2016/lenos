package com.len.service.impl;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import com.len.service.AdminLoginService;

/**
 * 后台login service
 */
@Service
public class AdminLoginServiceImpl implements AdminLoginService {

    @Override
    public void logout() {
        Subject sub = SecurityUtils.getSubject();
        sub.logout();
    }
}
