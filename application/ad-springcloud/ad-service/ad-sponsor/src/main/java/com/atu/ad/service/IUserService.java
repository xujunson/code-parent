package com.atu.ad.service;

import com.atu.ad.exception.AdException;
import com.atu.ad.vo.CreateUserRequest;
import com.atu.ad.vo.CreateUserResponse;

/**
 * @author: Tom
 * @date: 2020-03-28 9:58
 * @description:
 */
public interface IUserService {
    /**
     * 创建用户
     *
     * @param request
     * @return
     * @throws AdException
     */
    CreateUserResponse createUser(CreateUserRequest request) throws AdException;
}
