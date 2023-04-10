package com.len.menu;

public enum LoginType {

    /**
     * blog
     */
    BLOG("BlogLogin"),

    /**
     * admin
     */
    SYS("UserLogin");

    private String type;

    LoginType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return this.type;
    }
}
