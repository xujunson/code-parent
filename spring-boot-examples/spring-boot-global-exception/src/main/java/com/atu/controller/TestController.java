package com.atu.controller;

import com.atu.domain.BusinessException;
import com.atu.enums.ErrorEnum;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author: Tom
 * @create: 2023-02-15 11:19
 * @Description: 测试
 */
@Controller
@RequestMapping("test")
public class TestController {
    @RequestMapping("query")
    @ResponseBody
    public Object query() {
        throw new BusinessException(ErrorEnum.NO_AUTH.getErrorCode(), ErrorEnum.NO_AUTH.getErrorMsg());
    }
}
