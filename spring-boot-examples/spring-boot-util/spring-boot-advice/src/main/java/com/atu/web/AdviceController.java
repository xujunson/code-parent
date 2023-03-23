package com.atu.web;

import com.atu.service.ArithmeticCalculator;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: Tom
 * @Date: 2021/7/9 10:19 上午
 * @Description:
 */
@RestController
public class AdviceController {
    @Resource
    ArithmeticCalculator arithmeticCalculator;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public int add(@RequestParam(value= "i") int i, @RequestParam(value= "j") int j) {
        return arithmeticCalculator.add(i,j);
    }
}
