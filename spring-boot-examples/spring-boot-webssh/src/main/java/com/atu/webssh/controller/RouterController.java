package com.atu.webssh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RouterController {
    @RequestMapping("/websshpage")
    public String websshpage(){

        return "/page/webssh.html";
    }
}
