package com.atu.ad.controller;

import com.alibaba.fastjson.JSON;
import com.atu.ad.service.ICreativeService;
import com.atu.ad.vo.CreativeRequest;
import com.atu.ad.vo.CreativeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Tom
 * @date: 2020-03-28 14:41
 * @description: 创意
 */

@Slf4j
@RestController
public class CreativeOPController {
    @Autowired
    private ICreativeService creativeService;

    @PostMapping("/create/creative")
    public CreativeResponse createCreative(@RequestBody CreativeRequest request) {
        log.info("ad-sponsor: createCreative -> {}",
                JSON.toJSONString(request));
        return creativeService.createCreative(request);
    }
}
