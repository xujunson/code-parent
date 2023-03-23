package com.atu.template.config.listener;

import com.atu.template.config.event.BaseEvent;
import org.springframework.context.ApplicationListener;

/**
 * @Author: Tom
 * @Date: 2022/7/12 14:20
 * @Description:
 */
public interface IEventListener<T extends BaseEvent> extends ApplicationListener<T> {

    /**
     * 观察者的业务逻辑处理
     *
     * @param event
     */
    default void onApplicationEvent(T event) {
        try {
            if (support(event)) {
                handler(event);
            }
        } catch (Throwable e) {
            /**
             *
             */
            handleException(e);
        }
    }

    /**
     * 接口里面，加了default，就可以写方法实现
     *
     * @param event
     * @return
     */
    default boolean support(T event) {
        return true;
    }

    /**
     * 真正实现业务逻辑的接口，给子类去实现。
     *
     * @param event
     */
    void handler(T event);

    /**
     * 异常默认不处理
     *
     * @param exception
     */
    default void handleException(Throwable exception) {
    }

}
