package com.tom.controller;

import com.tom.domain.User;
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
    @Autowired private UserService userService;
    @RequestMapping("thymeleaf")
    public String thymeleaf(Model model) {
        model.addAttribute("name", "Tom");
        return "hello";
    }

    @RequestMapping("db/get")
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
}
