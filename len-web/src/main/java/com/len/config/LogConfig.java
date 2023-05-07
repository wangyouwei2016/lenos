package com.len.config;

import com.len.mapper.SysLogMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.len.core.annotation.LogAspect;

/**
 * 日志拦截配置
 * @author <a href="https://gitee.com/zzdevelop/lenosp">lenosp</a>
 */
@Configuration
public class LogConfig {

    @Bean(name = "logAspect")
    public LogAspect getLogAspect(SysLogMapper logMapper) {
        return new LogAspect(logMapper);
    }
}
