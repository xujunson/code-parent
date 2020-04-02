package com.atu.service.impl;

import com.atu.dubbo.LoginService;
import com.atu.request.LoginRequest;
import com.atu.response.LoginResponse;
import com.atu.service.LoginTestService;
import com.yunphant.components.idgenerator.redis.RedisIdGenerator;
import com.yunphant.utils.core.date.DatePattern;
import com.yunphant.utils.core.date.DateUtil;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author lidinglin
 * @version 1.0.0
 * @name DemoServiceImpl
 * @date 2020/3/18 1:31 上午
 * @describe
 */
@Service
public class LoginTestServiceImpl implements LoginTestService {

    @Resource
    RedisIdGenerator redisIdGenerator;

    @Reference(group = "${com.atu.group}", version = "${com.atu.version}")
    LoginService loginService;


    /**
     * 登录测试接口调用
     *
     * @param userName 姓名
     * @param passWord 密码
     * @return
     */
    @Override
    public String login(String userName, String passWord) {
        LoginRequest request = new LoginRequest();
        request.setReqMsgId(redisIdGenerator.nextUniqueId("redisIdGenerator", 1, 32));
        request.setReqMsgTime(DateUtil.format(new Date(), DatePattern.PURE_DATETIME_PATTERN));
        request.setTxnTypeNo("4000001");
        request.setUserName(userName);
        request.setPassWord(passWord);
        LoginResponse response = loginService.login(request);
        return response.toJson();
    }
}
