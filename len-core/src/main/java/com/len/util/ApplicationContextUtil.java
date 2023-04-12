package com.len.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationContextUtil implements ApplicationContextAware {

	
	
	
	
	
	
	
	
    private static ApplicationContext applicationContext;

    public static ApplicationContext getContext() {
        return applicationContext;
    }

    public static Object getBean(String arg) {
        return applicationContext.getBean(arg);
    }

    public static <T> T getBean(Class aClass) {
        return (T)applicationContext.getBean(aClass);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextUtil.applicationContext = applicationContext;
    }
}
