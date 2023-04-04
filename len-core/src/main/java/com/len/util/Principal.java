package com.len.util;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * <p>
 * 博客管理current user message
 */
@Data
public class Principal {

    private String userId;

    private String userName;

    private List<String> roles = new ArrayList<>();

    private String photo;
}
