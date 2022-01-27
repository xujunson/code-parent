package com.atu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Tom
 * @Date: 2022/1/27 10:54 上午
 * @Description:
 */
@Controller
public class HelloController {

    @RequestMapping("index")
    public String index() {
        return "index";
    }

    @ResponseBody
    @RequestMapping("/hello/{id}")
    public Map get(@PathVariable int id) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("name", "test");
        map.put("desc", "test desc");
        return map;
    }
}