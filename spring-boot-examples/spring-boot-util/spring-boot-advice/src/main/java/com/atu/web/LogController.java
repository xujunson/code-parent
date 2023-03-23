package com.atu.web;

import com.atu.common.annotation.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Tom
 * @Date: 2022/6/23 15:35
 * @Description:
 */
@Slf4j
@RestController
public class LogController {

    @Log(title = "print_log")
    @RequestMapping(value = "/log", method = RequestMethod.POST)
    public void log() {
        log.info("LogController");
    }
}
