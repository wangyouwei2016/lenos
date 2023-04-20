package com.len.core.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.len.base.handler.ExecuteContext;
import com.len.core.init.job.JobExecuteImpl;

/**
 * 初始化类
 */
@Component
public class LenospInit implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        init(event);
    }

    /**
     * spring 加载后初始化操作
     *
     * @param event
     */
    public void init(ContextRefreshedEvent event) {
        ExecuteContext context = new JobExecuteImpl();
        context.execute(event);
    }

}
