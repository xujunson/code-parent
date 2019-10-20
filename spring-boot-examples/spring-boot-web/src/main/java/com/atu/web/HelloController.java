package com.atu.web;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

/**
 * 只需要类添加 @RestController 即可，默认类中的方法都会以 json 的格式返回
 */
@RestController
public class HelloController {
    @RequestMapping("/hello")
    public String hello(Locale locale, Model model) {
        return "Hello World";
    }
}
