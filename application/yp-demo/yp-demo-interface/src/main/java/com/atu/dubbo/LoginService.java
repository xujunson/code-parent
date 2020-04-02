package com.atu.dubbo;

import com.yunphant.annotation.apidoc.ApiMethod;
import com.yunphant.annotation.apidoc.ApiService;
import com.atu.request.LoginRequest;
import com.atu.response.LoginResponse;

/**
 * @author lidinglin
 * @version 1.0.0
 * @name LoginService
 * @date 2020/4/1 1:28 上午
 * @describe
 */
@ApiService(value = "LoginService", name = "登录测试接口服务")
public interface LoginService {

    /**
     * @param request
     * @return
     */
    @ApiMethod(value = "login", name = "登录测试接口")
    public LoginResponse login(LoginRequest request);
}
