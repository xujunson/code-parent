package com.atu.redis.controller;

import com.atu.redis.entity.User;
import com.atu.redis.service.UserRedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author : xsy
 * @Date : Created in 2018/12/20 16:53
 * @Description :  User控制器
 */
@RestController
@RequestMapping("/user")
public class UserController {
    protected static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRedisService userRedisService;


    /**
     * @return
     * @throws
     * @Title: UserController
     * @Description: 初始化redis数据
     */
    @RequestMapping("/initRedisdata")
    @ResponseBody
    public String initRedisData() {
        userRedisService.redisInitData();
        return "success";
    }

    @RequestMapping("/getUserRedisByLoginName/{loginName}")
    @ResponseBody
    public Map<String, Object> getUserRedisByLoginName(@PathVariable String loginName) {
        Map<String, Object> result = new HashMap<String, Object>();
        User user = userRedisService.getUserRedis(loginName);
        Assert.notNull(user);
        result.put("name", user.getName());
        result.put("loginName", user.getLoginName());
        result.put("departmentName", user.getDepartment().getName());
        result.put("roleName", user.getRoleList().get(0).getName());
        return result;
    }
}
