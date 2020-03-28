package com.atu.client;

import com.atu.ad.vo.CommonResponse;
import com.atu.client.vo.AdPlan;
import com.atu.client.vo.AdPlanGetRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author: Tom
 * @date: 2020-03-28 21:15
 * @description: 使用Feign调用微服务
 */
//fallback服务降级，一旦ad-sponsor服务不可用，在调用getAdPlans时，
//实际上会返回SponsorClientHystrix指定的getAdPlans
@FeignClient(value = "eureka-client-ad-sponsor", fallback = SponsorClientHystrix.class)
public interface SponsorClient {
    @RequestMapping(value = "/ad-sponsor/get/adPlan", method = RequestMethod.POST)
    CommonResponse<List<AdPlan>> getAdPlans(@RequestBody AdPlanGetRequest request);
}
