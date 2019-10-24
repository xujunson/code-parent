package com.atu.service;

import com.atu.model.UserInfo;

public interface UserInfoService {
    /**
     * 通过username查找用户信息;
     */
    public UserInfo findByUsername(String username);
}