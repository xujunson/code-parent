package com.tom.controller;

import com.tom.domain.User;
import com.tom.rabbitmq.MQSender;
import com.tom.redis.RedisService;
import com.tom.redis.UserKey;
import com.tom.result.Result;
import com.tom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author: Tom
 * @date: 2020-02-27 10:46
 * @description:
 */
@Controller
@RequestMapping("/demo")
public class SampleController {
    @Autowired
    private UserService userService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private MQSender mqSender;

    @RequestMapping("thymeleaf")
    public String thymeleaf(Model model) {
        model.addAttribute("name", "Tom");
        return "hello";
    }

    @RequestMapping("/db/get")
    @ResponseBody
    public Result<User> doGet() {
        User user = userService.getId(3);
        return Result.success(user);
    }

    @RequestMapping("/db/tx")
    @ResponseBody
    public Result<Boolean> dbTx() {
        userService.tx();
        return Result.success(true);
    }

    @RequestMapping("/redis/get")
    @ResponseBody
    public Result<User> redisGet() {
        User v1 = redisService.get(UserKey.getById, "" + 1, User.class);
        return Result.success(v1);
    }

    @RequestMapping("/redis/set")
    @ResponseBody
    public Result<Boolean> redisSet() {
        User user = new User();
        user.setId(1);
        user.setName("1111");
        redisService.set(UserKey.getById, "" + 1, user); //UserKey:id1
        return Result.success(true);
    }

    @RequestMapping("/mq")
    @ResponseBody
    public Result<String> mq(Model model) {
        mqSender.send("hello tom");
        return Result.success("hello world");
    }

    @RequestMapping("/mq/topic")
    @ResponseBody
    public Result<String> topic(Model model) {
        mqSender.sendTopic("hello tom");
        return Result.success("hello world");
    }
    @RequestMapping("/mq/fanout")
    @ResponseBody
    public Result<String> fanout(Model model) {
        mqSender.sendFanout("hello tom");
        return Result.success("hello world");
    }

    @RequestMapping("/mq/header")
    @ResponseBody
    public Result<String> header(Model model) {
        mqSender.sendHeader("hello tom");
        return Result.success("hello world");
    }
}
