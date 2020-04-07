package com.atu.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.WebAsyncTask;

import java.util.Locale;
import java.util.concurrent.Callable;

/**
 * 只需要类添加 @RestController 即可，默认类中的方法都会以 json 的格式返回
 */
@RestController
@Slf4j
public class HelloController {
    @RequestMapping("/hello")
    public String hello(Locale locale, Model model) {
        return "Hello World";
    }
    @RequestMapping("/ac")
    public WebAsyncTask<String> asyncTest() {
        Callable<String> callable = () -> {
            Thread.sleep(1000);
            log.info("=======");
            return "success";
        };
        log.info("------");
        return new WebAsyncTask<>(callable);
    }
}
