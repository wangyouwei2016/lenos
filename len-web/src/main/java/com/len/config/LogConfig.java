package com.len.config;

import com.len.core.annotation.LogAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class LogConfig {

  @Bean(name = "logAspect")
  public LogAspect getLogAspect(){
    return new LogAspect();
  }

}
