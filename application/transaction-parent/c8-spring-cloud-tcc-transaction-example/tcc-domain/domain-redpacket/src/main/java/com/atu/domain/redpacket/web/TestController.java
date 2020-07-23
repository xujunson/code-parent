package com.atu.domain.redpacket.web;

import com.atu.domain.redpacket.model.TradeOrder;
import com.atu.domain.redpacket.service.TradeOrderServiceT;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author: Tom
 * @date: 2020-07-22 22:15
 * @description:
 */
@RestController
public class TestController {
    @Resource
    private TradeOrderServiceT tradeOrderServiceT;

    @RequestMapping("/getInfo")
    public String getUser() {
        TradeOrder tradeOrder = tradeOrderServiceT.findByMerchantOrderNo("f91bb32479ee4779a6cc266c3657ac96");
        System.out.println(tradeOrder.toString());
        return "123";
    }
}
