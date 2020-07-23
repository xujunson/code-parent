package com.atu.redpacket.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.remoting.TimeoutException;
import com.atu.domain.redpacket.model.RedPacketAccount;
import com.atu.domain.redpacket.model.TradeOrder;
import com.atu.domain.redpacket.service.RedPacketService;
import com.atu.domain.redpacket.service.TradeOrderServiceT;
import com.atu.redpacket.service.RedPacketTradeOrderService;
import com.atu.redpacket.service.dto.RedPacketTradeOrderDto;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.mengyun.tcctransaction.api.Compensable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import java.net.SocketTimeoutException;
import java.util.Calendar;

/**
 * @author: Tom
 * @date: 2020-07-21 17:35
 * @description:
 */
@Service
public class RedPacketTradeOrderServiceImpl implements RedPacketTradeOrderService {

    @Reference
    RedPacketService redPacketService;

    @Reference
    TradeOrderServiceT tradeOrderServiceT;

    @Override
    @Compensable(confirmMethod = "confirmRecord", cancelMethod = "cancelRecord", delayCancelExceptions = {SocketTimeoutException.class, TimeoutException.class})
    @Transactional
    public String record(RedPacketTradeOrderDto tradeOrderDto) {

        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("red packet try record called. time seq:" + DateFormatUtils.format(Calendar.getInstance(), "yyyy-MM-dd HH:mm:ss"));

        TradeOrder foundTradeOrder = tradeOrderServiceT.findByMerchantOrderNo(tradeOrderDto.getMerchantOrderNo());

        //check if trade order has been recorded, if yes, return success directly.
        if (foundTradeOrder == null) {

            TradeOrder tradeOrder = new TradeOrder(
                    tradeOrderDto.getSelfUserId(),
                    tradeOrderDto.getOppositeUserId(),
                    tradeOrderDto.getMerchantOrderNo(),
                    tradeOrderDto.getAmount()
            );

            try {

                tradeOrderServiceT.insert(tradeOrder);

                RedPacketAccount transferFromAccount = redPacketService.findByUserId(tradeOrderDto.getSelfUserId());

                transferFromAccount.transferFrom(tradeOrderDto.getAmount());

                redPacketService.save(transferFromAccount);
            } catch (DataIntegrityViolationException e) {
                //this exception may happen when insert trade order concurrently, if happened, ignore this insert operation.
            }
        }

        return "success";
    }

    @Transactional
    public void confirmRecord(RedPacketTradeOrderDto tradeOrderDto) {

        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("red packet confirm record called. time seq:" + DateFormatUtils.format(Calendar.getInstance(), "yyyy-MM-dd HH:mm:ss"));

        TradeOrder tradeOrder = tradeOrderServiceT.findByMerchantOrderNo(tradeOrderDto.getMerchantOrderNo());

        //check if the trade order status is DRAFT, if yes, return directly, ensure idempotency.
        if (tradeOrder != null && tradeOrder.getStatus().equals("DRAFT")) {
            tradeOrder.confirm();
            tradeOrderServiceT.update(tradeOrder);

            RedPacketAccount transferToAccount = redPacketService.findByUserId(tradeOrderDto.getOppositeUserId());

            transferToAccount.transferTo(tradeOrderDto.getAmount());

            redPacketService.save(transferToAccount);
        }
    }

    @Transactional
    public void cancelRecord(RedPacketTradeOrderDto tradeOrderDto) {

        try {
            Thread.sleep(1000l);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("red packet cancel record called. time seq:" + DateFormatUtils.format(Calendar.getInstance(), "yyyy-MM-dd HH:mm:ss"));

        TradeOrder tradeOrder = tradeOrderServiceT.findByMerchantOrderNo(tradeOrderDto.getMerchantOrderNo());

        //check if the trade order status is DRAFT, if yes, return directly, ensure idempotency.
        if (null != tradeOrder && "DRAFT".equals(tradeOrder.getStatus())) {
            tradeOrder.cancel();
            tradeOrderServiceT.update(tradeOrder);

            RedPacketAccount capitalAccount = redPacketService.findByUserId(tradeOrderDto.getSelfUserId());

            capitalAccount.cancelTransfer(tradeOrderDto.getAmount());

            redPacketService.save(capitalAccount);
        }
    }
}
