package com.len.menu;

public enum LoginType {

    BLOG("BlogLogin"), SYS("UserLogin");

    private String type;

    private LoginType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return this.type;
    }
}
