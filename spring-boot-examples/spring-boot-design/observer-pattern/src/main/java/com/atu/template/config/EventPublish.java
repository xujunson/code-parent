package com.atu.template.config;

import com.atu.template.config.event.BaseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @Author: Tom
 * @Date: 2022/7/12 14:26
 * @Description:
 */
@Component
public class EventPublish {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ExecutorPool executorPool;

    public void publish(BaseEvent event) {
        applicationContext.publishEvent(event);
    }

    //异步发布（异步非阻塞）
    public void asyncPublish(BaseEvent event) {
        executorPool.eventExecutor().execute(() -> {
            publish(event);
        });
    }
}
