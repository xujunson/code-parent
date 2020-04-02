package com.atu.service;

/**
 * @author lidinglin
 * @version 1.0.0
 * @name LoginTestService
 * @date 2020/3/18 1:30 上午
 * @describe
 */
public interface LoginTestService {

    /**
     * 登录测试接口调用
     *
     * @param userName 姓名
     * @param passWord 密码
     * @return
     */
    public String login(String userName, String passWord);
}
