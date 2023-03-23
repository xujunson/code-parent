package com.atu.web;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Tom
 * @Date: 2021/7/27 11:30 上午
 * @Description:
 */
@Api(value = "kafka生产者", tags = {"kafka生产者"})
@Slf4j
@Validated
@RestController
@RequestMapping("/kafka")
public class ProductController {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    /**
     * 生产者发送消息
     *
     * @return
     */
    @GetMapping("/send")
    public String send(@RequestParam(value = "topic") String topic, @RequestParam(value = "msg") String msg) {

        kafkaTemplate.send(topic, msg);
        log.info("发送消息的topic为: {}! 发送的内容为: {}", topic, msg);

        return "发送消息成功!";
    }
}


