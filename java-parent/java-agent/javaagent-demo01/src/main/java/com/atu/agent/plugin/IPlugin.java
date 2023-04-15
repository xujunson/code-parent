package com.atu.agent.plugin;

/**
 * @author: Tom
 * @date: 2023/4/15 13:53
 * @description: TODO
 **/
public interface IPlugin {

    //名称
    String name();

    //监控点
    InterceptPoint[] buildInterceptPoint();

    //拦截器类
    Class adviceClass();

}
