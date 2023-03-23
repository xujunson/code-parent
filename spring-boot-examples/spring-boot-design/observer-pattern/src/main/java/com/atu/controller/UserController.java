package com.atu.controller;

import com.atu.service.RegisterObserver;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @Author: Tom
 * @Date: 2022/7/8 18:27
 * @Description:
 */
@RestController
public class UserController implements ApplicationContextAware {


    //观察者列表
    private Collection<RegisterObserver> regObservers;

    private Executor executor = Executors.newFixedThreadPool(10);

    @RequestMapping("register")
    public String register() {
        //注册成功过（类似于被观察者，做了某件事）
        System.out.println("add register");
        //然后就开始通知各个观察者。
        for (RegisterObserver temp : regObservers) {
            temp.sendMsg("注冊成功");
        }
        return "SUCCESS";
    }

    @RequestMapping("asyncRegister")
    public String asyncRegister() {
        System.out.println("add async register");
        //异步通知每个观察者
        for (RegisterObserver temp : regObservers) {
            executor.execute(() -> {
                temp.sendMsg("注冊成功");
            });
        }

        return "SUCCESS";
    }

    /**
     * 利用spring的ApplicationContextAware，初始化所有观察者
     *
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        regObservers = new ArrayList<>(applicationContext.getBeansOfType(RegisterObserver.class).values());
    }
}
