package com.atu.redpacket.service.impl;

import com.atu.redpacket.domain.model.RedPacketAccount;
import com.atu.redpacket.domain.model.TradeOrder;
import com.atu.redpacket.domain.service.RedPacketService;
import com.atu.redpacket.domain.service.TradeOrderService;
import com.atu.redpacket.service.RedPacketTradeOrderService;
import com.atu.redpacket.service.dto.RedPacketTradeOrderDto;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.mengyun.tcctransaction.api.Compensable;
import org.mengyun.tcctransaction.dubbo.context.DubboTransactionContextEditor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;

/**
 * @author: Tom
 * @date: 2020-07-21 17:35
 * @description:
 */
@DubboService
public class RedPacketTradeOrderServiceImpl implements RedPacketTradeOrderService {

    @Autowired
    RedPacketService redPacketService;

    @Autowired
    TradeOrderService tradeOrderService;

    @Override
    @Compensable(confirmMethod = "confirmRecord", cancelMethod = "cancelRecord", transactionContextEditor = DubboTransactionContextEditor.class)
    @Transactional
    public String record(RedPacketTradeOrderDto tradeOrderDto) {

        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("red packet try record called. time seq:" + DateFormatUtils.format(Calendar.getInstance(), "yyyy-MM-dd HH:mm:ss"));

        TradeOrder foundTradeOrder = tradeOrderService.findByMerchantOrderNo(tradeOrderDto.getMerchantOrderNo());

        //check if trade order has been recorded, if yes, return success directly.
        if (foundTradeOrder == null) {

            TradeOrder tradeOrder = new TradeOrder(
                    tradeOrderDto.getSelfUserId(),
                    tradeOrderDto.getOppositeUserId(),
                    tradeOrderDto.getMerchantOrderNo(),
                    tradeOrderDto.getAmount()
            );

            try {

                tradeOrderService.insert(tradeOrder);

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

        TradeOrder tradeOrder = tradeOrderService.findByMerchantOrderNo(tradeOrderDto.getMerchantOrderNo());

        //check if the trade order status is DRAFT, if yes, return directly, ensure idempotency.
        if (tradeOrder != null && tradeOrder.getStatus().equals("DRAFT")) {
            tradeOrder.confirm();
            tradeOrderService.update(tradeOrder);

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

        TradeOrder tradeOrder = tradeOrderService.findByMerchantOrderNo(tradeOrderDto.getMerchantOrderNo());

        //check if the trade order status is DRAFT, if yes, return directly, ensure idempotency.
        if (null != tradeOrder && "DRAFT".equals(tradeOrder.getStatus())) {
            tradeOrder.cancel();
            tradeOrderService.update(tradeOrder);

            RedPacketAccount capitalAccount = redPacketService.findByUserId(tradeOrderDto.getSelfUserId());

            capitalAccount.cancelTransfer(tradeOrderDto.getAmount());

            redPacketService.save(capitalAccount);
        }
    }
}
