package com.len.util;

import org.apache.shiro.authc.AuthenticationToken;

import com.len.menu.LoginType;

public class JwtToken implements AuthenticationToken {

    private String token;
    private LoginType type;

    public JwtToken(String token, LoginType type) {
        this.token = token;
        this.type = type;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LoginType getType() {
        return type;
    }

    public void setType(LoginType type) {
        this.type = type;
    }
}
