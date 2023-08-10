package com.len;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * 项目启动类
 *
 * @author <a href="https://gitee.com/zzdevelop/lenosp">lenosp</a>
 */
@EnableWebMvc
@EnableTransactionManagement
@MapperScan(basePackages = {"com.len.mapper"})
@SpringBootApplication(scanBasePackages = {"com.len", "org.activiti"},
    exclude = {RedisAutoConfiguration.class,
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
        org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration.class})
public class LenApplication {

    public static void main(String[] args) {
        SpringApplication.run(LenApplication.class, args);
        System.out.println("Server start success!");
        // 没啥意思，我就是想加个注释
    }

}
