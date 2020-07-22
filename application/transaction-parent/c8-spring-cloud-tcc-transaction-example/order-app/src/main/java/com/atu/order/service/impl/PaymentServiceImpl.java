package com.atu.order.service.impl;


import com.atu.order.service.PaymentService;
import com.atu.domaincapital.service.CapitalTradeOrderService;
import com.atu.domaincapital.service.dto.CapitalTradeOrderDto;
import com.atu.domaincapital.entity.Order;
import com.atu.domaincapital.service.OrderDomainService;
import com.atu.domainredpacket.service.RedPacketTradeOrderService;
import com.atu.domainredpacket.service.dto.RedPacketTradeOrderDto;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.mengyun.tcctransaction.api.Compensable;
import org.mengyun.tcctransaction.api.UniqueIdentity;
import org.springframework.dao.OptimisticLockingFailureException;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.net.SocketTimeoutException;
import java.util.Calendar;

/**
 * @author: Tom
 * @date: 2020-07-21 17:35
 * @description:
 */
@DubboService
public class PaymentServiceImpl implements PaymentService {

    @Resource
    CapitalTradeOrderService capitalTradeOrderService;

    @Resource
    RedPacketTradeOrderService redPacketTradeOrderService;

    @Resource
    OrderDomainService orderDomainService;

    @Override
    @Compensable(confirmMethod = "confirmMakePayment", cancelMethod = "cancelMakePayment", asyncConfirm = false, delayCancelExceptions = {SocketTimeoutException.class, org.apache.dubbo.remoting.TimeoutException.class})
    public void makePayment(@UniqueIdentity String orderNo, BigDecimal redPacketPayAmount, BigDecimal capitalPayAmount) {
        System.out.println("order try make payment called.time seq:" + DateFormatUtils.format(Calendar.getInstance(), "yyyy-MM-dd HH:mm:ss"));

        Order order = orderDomainService.findOrderByMerchantOrderNo(orderNo);
        //check if the order status is DRAFT, if no, means that another call makePayment for the same order happened, ignore this call makePayment.
        if (order.getStatus().equals("DRAFT")) {
            order.pay(redPacketPayAmount, capitalPayAmount);
            try {
                orderDomainService.updateOrder(order);
            } catch (OptimisticLockingFailureException e) {
                //ignore the concurrently update order exception, ensure idempotency.
            }
        }

        String result = capitalTradeOrderService.record(buildCapitalTradeOrderDto(order));
        String result2 = redPacketTradeOrderService.record(buildRedPacketTradeOrderDto(order));
    }

    public void confirmMakePayment(String orderNo, BigDecimal redPacketPayAmount, BigDecimal capitalPayAmount) {


        try {
            Thread.sleep(1000l);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("order confirm make payment called. time seq:" + DateFormatUtils.format(Calendar.getInstance(), "yyyy-MM-dd HH:mm:ss"));

        Order foundOrder = orderDomainService.findOrderByMerchantOrderNo(orderNo);

        //check if the trade order status is PAYING, if no, means another call confirmMakePayment happened, return directly, ensure idempotency.
        if (foundOrder != null && foundOrder.getStatus().equals("PAYING")) {
            foundOrder.confirm();
            orderDomainService.updateOrder(foundOrder);
        }
    }

    public void cancelMakePayment(String orderNo, BigDecimal redPacketPayAmount, BigDecimal capitalPayAmount) {

        try {
            Thread.sleep(1000l);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("order cancel make payment called.time seq:" + DateFormatUtils.format(Calendar.getInstance(), "yyyy-MM-dd HH:mm:ss"));

        Order foundOrder = orderDomainService.findOrderByMerchantOrderNo(orderNo);

        //check if the trade order status is PAYING, if no, means another call cancelMakePayment happened, return directly, ensure idempotency.
        if (foundOrder != null && foundOrder.getStatus().equals("PAYING")) {
            foundOrder.cancelPayment();
            orderDomainService.updateOrder(foundOrder);
        }
    }


    private CapitalTradeOrderDto buildCapitalTradeOrderDto(Order order) {

        CapitalTradeOrderDto tradeOrderDto = new CapitalTradeOrderDto();
        tradeOrderDto.setAmount(order.getCapitalPayAmount());
        tradeOrderDto.setMerchantOrderNo(order.getMerchantOrderNo());
        tradeOrderDto.setSelfUserId(order.getPayerUserId());
        tradeOrderDto.setOppositeUserId(order.getPayeeUserId());
        tradeOrderDto.setOrderTitle(String.format("order no:%s", order.getMerchantOrderNo()));

        return tradeOrderDto;
    }

    private RedPacketTradeOrderDto buildRedPacketTradeOrderDto(Order order) {
        RedPacketTradeOrderDto tradeOrderDto = new RedPacketTradeOrderDto();
        tradeOrderDto.setAmount(order.getRedPacketPayAmount());
        tradeOrderDto.setMerchantOrderNo(order.getMerchantOrderNo());
        tradeOrderDto.setSelfUserId(order.getPayerUserId());
        tradeOrderDto.setOppositeUserId(order.getPayeeUserId());
        tradeOrderDto.setOrderTitle(String.format("order no:%s", order.getMerchantOrderNo()));

        return tradeOrderDto;
    }
}
