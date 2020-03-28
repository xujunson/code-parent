package com.atu.controller;

import com.alibaba.fastjson.JSON;
import com.atu.ad.annotation.IgnoreResponseAdvice;
import com.atu.ad.vo.CommonResponse;
import com.atu.client.SponsorClient;
import com.atu.client.vo.AdPlan;
import com.atu.client.vo.AdPlanGetRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author: Tom
 * @date: 2020-03-28 17:52
 * @description:
 */
@Slf4j
@RestController
public class SearchController {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private SponsorClient sponsorClient;

    /**
     * 通过Feign方式调用微服务
     *
     * @param request
     * @return
     */
    @IgnoreResponseAdvice
    @PostMapping("/getAdPlans")
    public CommonResponse<List<AdPlan>> getAdPlans(@RequestBody AdPlanGetRequest request) {

        log.info("ad-search: getAdPlans -> {}",
                JSON.toJSONString(request));
        return sponsorClient.getAdPlans(request);
    }

    /**
     * 通过Ribbon调用其他微服务
     *
     * @param request
     * @return
     */
    //不使用统一响应
    @IgnoreResponseAdvice
    @PostMapping("/getAdPlansByRibbon")
    public CommonResponse<List<AdPlan>> getAdPlansByRibbon(@RequestBody AdPlanGetRequest request) {
        log.info("ad-search: getAdPlansByRibbon -> {}",
                JSON.toJSONString(request));
        return restTemplate.postForEntity(
                "http://eureka-client-ad-sponsor/ad-sponsor/get/adPlan", request, CommonResponse.class
        ).getBody();
    }
}
