package com.len.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.len.core.annotation.LogAspect;

@Configuration
public class LogConfig {

    @Bean(name = "logAspect")
    public LogAspect getLogAspect() {
        return new LogAspect();
    }

}
