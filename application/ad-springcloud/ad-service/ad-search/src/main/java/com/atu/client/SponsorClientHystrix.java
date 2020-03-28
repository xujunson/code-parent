package com.atu.client;

import com.atu.ad.vo.CommonResponse;
import com.atu.client.vo.AdPlan;
import com.atu.client.vo.AdPlanGetRequest;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: Tom
 * @date: 2020-03-28 21:24
 * @description: 断路器——防止服务雪崩
 */
@Component
public class SponsorClientHystrix implements SponsorClient {
    @Override
    public CommonResponse<List<AdPlan>> getAdPlans
            (AdPlanGetRequest request) {
        return new CommonResponse<>(-1, "eureka-client-ad-sponsor error");
    }
}
