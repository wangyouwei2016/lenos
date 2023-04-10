package com.len.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.annotation.Configuration;

/**
 * 获取bean
 */
@Configuration
public class BeanFactoryImpl implements BeanFactoryAware {

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println(String.format("可注释掉类：%s 配置，去除打印以下bean", "BeanFactoryImpl"));
        System.out.println("BeanFactoryAware------->" + beanFactory);
    }
}
