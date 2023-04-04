package com.len.util;

import org.apache.shiro.authc.UsernamePasswordToken;

import com.len.menu.LoginType;

public class CustomUsernamePasswordToken extends UsernamePasswordToken {

    private LoginType type;

    public CustomUsernamePasswordToken(final String username, final String password, LoginType loginType) {
        super(username, password);
        this.type = loginType;
    }

    public LoginType getType() {
        return type;
    }

    public void setType(LoginType type) {
        this.type = type;
    }
}
