package com.atu.ad.service;


import com.atu.ad.Application;
import com.atu.ad.exception.AdException;
import com.atu.ad.vo.AdPlanGetRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

/**
 * 测试用例——测试service接口
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class},
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class AdPlanServiceTest {

    @Autowired
    private IAdPlanService planService;

    @Test
    public void testGetAdPlan() throws AdException {

        System.out.println(
                planService.getAdPlanByIds(
                        new AdPlanGetRequest(15L, Collections.singletonList(10L))
                )
        );
    }
}
