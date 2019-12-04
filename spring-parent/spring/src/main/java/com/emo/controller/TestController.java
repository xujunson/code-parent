package com.emo.controller;

import com.emo.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author ：Tom
 * @date ：Created in 2019/9/12 10:55
 * @description：测试控制层
 */
@Controller
public class TestController {

    @Autowired
    private TestService testServiceImpl;

    @RequestMapping("/addUser.do")
    public void addUser() {
        testServiceImpl.addInfo();
    }
}
