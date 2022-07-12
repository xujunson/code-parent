package com.atu.template.config.event;

import org.springframework.context.ApplicationEvent;

/**
 * @Author: Tom
 * @Date: 2022/7/12 14:16
 * @Description: 事件基础对象
 */
public class BaseEvent extends ApplicationEvent {

    public BaseEvent(Object source) {
        super(source);
    }

    public BaseEvent() {
        this("");
    }
}
