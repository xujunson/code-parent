package com.atu.service;

import com.atu.domain.User;

import java.util.List;

/**
 * @author: Tom
 * @create: 2023-03-27 16:19
 * @Description: user服务
 */
public interface UserService {
    /**
     * 获取所有用户信息
     */
    List<User> list();

    /**
     * 批量 保存用户信息
     *
     * @param userVOList
     */
    String insertForeach(List<User> userVOList);
}
