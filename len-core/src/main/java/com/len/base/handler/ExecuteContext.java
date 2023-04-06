package com.len.base.handler;

import org.springframework.context.event.ContextRefreshedEvent;

public interface ExecuteContext {

    void execute(ContextRefreshedEvent event);
}
