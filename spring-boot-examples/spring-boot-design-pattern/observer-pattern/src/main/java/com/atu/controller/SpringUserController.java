package com.atu.controller;

import com.atu.config.event.MessageEvent;
import com.atu.template.config.EventPublish;
import com.atu.template.config.event.RegisterMessageEvent;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Tom
 * @Date: 2022/7/12 11:24
 * @Description:
 */
@RestController
public class SpringUserController implements ApplicationContextAware {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @RequestMapping("springListenRegister")
    public String springListenRegister() {
        System.out.println("开始注册");
        System.out.println("add register");
        //用户注册成功，发布事件
        applicationEventPublisher.publishEvent(new MessageEvent("666"));
        return "SUCCESS";
    }


    @Autowired
    private EventPublish eventPublish;
    @RequestMapping("springListenRegisterByTemplate")
    public String springListenRegisterByTemplate() {
        System.out.println("开始注册 ByTemplate");
        System.out.println("add register ByTemplate");
        //用户注册成功，发布事件
        eventPublish.publish(new RegisterMessageEvent("00000001"));
        return "SUCCESS";
    }

    @RequestMapping("springListenRegisterByTemplateAsync")
    public String springListenRegisterByTemplateAsync() {
        System.out.println("开始注册 ByTemplate Async");
        System.out.println("add register ByTemplate Async");
        //用户注册成功，发布事件
        eventPublish.asyncPublish(new RegisterMessageEvent("00000002"));
        return "SUCCESS";
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }
}
