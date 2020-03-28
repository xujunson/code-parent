package com.atu.ad.controller;

import com.alibaba.fastjson.JSON;
import com.atu.ad.exception.AdException;
import com.atu.ad.service.IUserService;
import com.atu.ad.vo.CreateUserRequest;
import com.atu.ad.vo.CreateUserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Tom
 * @date: 2020-03-28 14:31
 * @description:
 */
@Slf4j
@RestController //直接返回json数据
public class UserOPController {
    @Autowired
    private IUserService userService;

    @PostMapping("/create/user")
    public CreateUserResponse createUser(
            @RequestBody CreateUserRequest request) throws AdException {

        log.info("ad-sponsor: createUser -> {}",
                JSON.toJSONString(request));
        return userService.createUser(request);
    }
}
