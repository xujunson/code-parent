package com.atu.controller;

import com.atu.mapper.vo.ValidVO;
import com.atu.validation.ValidGroup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Email;

/**
 * @Author: Tom
 * @Date: 2022/3/1 14:25
 * @Description:
 */
@RestController
@Slf4j
@Validated
public class ValidController {

    /**
     * @RequestBody注解，用于接受前端发送的json数据
     */
    @PostMapping("/valid/test1")
    public String test1(@Validated @RequestBody ValidVO validVO) {
        log.info("validEntity is {}", validVO);
        return "test1 valid success";
    }


    /**
     * 模拟表单提交
     */
    @PostMapping(value = "/valid/test2")
    public String test2(@Validated ValidVO validVO) {
        log.info("validEntity is {}", validVO);
        return "test2 valid success";
    }

    /**
     * 模拟单参数提交
     */
    @PostMapping(value = "/valid/test3")
    public String test3(@Email String email) {
        log.info("email is {}", email);
        return "email valid success";
    }


    /**
     * 新增
     *
     * @param validVO
     * @return
     */
    @PostMapping(value = "/valid/add")
    public String add(@Validated(value = ValidGroup.Crud.Create.class) ValidVO validVO) {
        log.info("validEntity is {}", validVO);
        return "test3 valid success";
    }

    @PostMapping(value = "/valid/update")
    public String update(@Validated(value = ValidGroup.Crud.Update.class) ValidVO validVO) {
        log.info("validEntity is {}", validVO);
        return "test4 valid success";
    }
}
