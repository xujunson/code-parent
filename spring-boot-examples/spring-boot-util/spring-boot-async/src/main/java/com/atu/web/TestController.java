package com.atu.web;

import com.atu.service.TestAsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author ：mark
 * @date ：Created in 2019/11/12 10:29
 * @description：异步
 */
@Controller
public class TestController {
    @Autowired
    private TestAsyncService testAsyncService;

    @RequestMapping("/doTest.do")
    public void test() throws InterruptedException {
        testAsyncService.service1();
        testAsyncService.service2();
    }
}
