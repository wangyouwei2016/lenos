package com.len.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jagregory.shiro.freemarker.ShiroTags;
import com.len.freemarker.LenInclude;

@Component
public class FreemarkerShiroConfig implements InitializingBean {

    @Autowired
    private freemarker.template.Configuration configuration;

    @Override
    public void afterPropertiesSet() throws Exception {
        configuration.setSharedVariable("shiro", new ShiroTags());
        configuration.setSharedVariable("lenInclude", new LenInclude());
    }
}
