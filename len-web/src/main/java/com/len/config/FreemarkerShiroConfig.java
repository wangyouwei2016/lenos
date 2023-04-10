package com.len.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jagregory.shiro.freemarker.ShiroTags;
import com.len.freemarker.LenInclude;

/**
 * freemarker设置shiro变量
 */

@Component
public class FreemarkerShiroConfig implements InitializingBean {

    @Autowired
    private freemarker.template.Configuration configuration;

    @Override
    public void afterPropertiesSet() throws Exception {
        // 支持shiro标签
        configuration.setSharedVariable("shiro", new ShiroTags());
        // 支持lenInclude标签
        configuration.setSharedVariable("lenInclude", new LenInclude());
    }
}
