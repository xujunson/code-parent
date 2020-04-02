package com.atu.dubbo.impl;

import com.yunphant.annotation.AttachOperation;
import com.atu.dubbo.LoginService;
import com.atu.request.LoginRequest;
import com.atu.response.LoginResponse;
import com.yunphant.service.YunphantBaseService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;

/**
 * @author lidinglin
 * @version 1.0.0
 * @name LoginServiceImpl
 * @date 2020/3/18 1:31 上午
 * @describe
 */
@Slf4j
@Service(group = "${com.atu.group}", version = "${com.atu.version}")
public class LoginServiceImpl extends YunphantBaseService<LoginResponse, LoginRequest> implements LoginService {

    /**
     * @param request
     * @return
     */
    @Override
    @AttachOperation
    public LoginResponse login(LoginRequest request) {
        LoginResponse response = new LoginResponse();
        return this.mainHandle(response, request);
    }

    @Override
    public void startHandle() {

    }

    @Override
    public LoginResponse checkHandle(LoginResponse response, LoginRequest request) {
        return response;
    }

    @Override
    public LoginResponse doHandle(LoginResponse response, LoginRequest request) {
        return response;
    }

    @Override
    public LoginResponse respHandle(LoginResponse response, LoginRequest request) {
        return response;
    }

    @Override
    public void cleanHandle() {

    }
}
