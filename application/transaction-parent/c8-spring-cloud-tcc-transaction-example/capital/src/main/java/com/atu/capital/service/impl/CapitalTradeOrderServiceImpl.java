package com.atu.capital.service.impl;

import com.atu.capital.api.service.CapitalTradeOrderService;
import com.atu.capital.api.service.dto.CapitalTradeOrderDto;
import com.atu.capital.entity.CapitalAccount;
import com.atu.capital.entity.TradeOrder;
import com.atu.capital.repository.CapitalAccountRepository;
import com.atu.capital.repository.TradeOrderRepository;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.mengyun.tcctransaction.api.Compensable;
import org.mengyun.tcctransaction.context.MethodTransactionContextEditor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;

/**
 * @author: Tom
 * @date: 2020-07-21 17:24
 * @description:
 */
@Service("capitalTradeOrderService")
public class CapitalTradeOrderServiceImpl implements CapitalTradeOrderService {

    @Autowired
    CapitalAccountRepository capitalAccountRepository;

    @Autowired
    TradeOrderRepository tradeOrderRepository;

    @Override
    @Compensable(confirmMethod = "confirmRecord", cancelMethod = "cancelRecord", transactionContextEditor = MethodTransactionContextEditor.class)
    @Transactional
    public String record(CapitalTradeOrderDto tradeOrderDto) {

        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("capital try record called. time seq:" + DateFormatUtils.format(Calendar.getInstance(), "yyyy-MM-dd HH:mm:ss"));


        TradeOrder foundTradeOrder = tradeOrderRepository.findByMerchantOrderNo(tradeOrderDto.getMerchantOrderNo());


        //check if trade order has been recorded, if yes, return success directly.
        if (foundTradeOrder == null) {

            TradeOrder tradeOrder = new TradeOrder(
                    tradeOrderDto.getSelfUserId(),
                    tradeOrderDto.getOppositeUserId(),
                    tradeOrderDto.getMerchantOrderNo(),
                    tradeOrderDto.getAmount()
            );

            try {
                tradeOrderRepository.insert(tradeOrder);

                CapitalAccount transferFromAccount = capitalAccountRepository.findByUserId(tradeOrderDto.getSelfUserId());

                transferFromAccount.transferFrom(tradeOrderDto.getAmount());

                capitalAccountRepository.save(transferFromAccount);

            } catch (DataIntegrityViolationException e) {
                //this exception may happen when insert trade order concurrently, if happened, ignore this insert operation.
            }
        }

        return "success";
    }

    @Transactional
    public void confirmRecord(CapitalTradeOrderDto tradeOrderDto) {
        try {
            Thread.sleep(1000l);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("capital confirm record called. time seq:" + DateFormatUtils.format(Calendar.getInstance(), "yyyy-MM-dd HH:mm:ss"));

        TradeOrder tradeOrder = tradeOrderRepository.findByMerchantOrderNo(tradeOrderDto.getMerchantOrderNo());

        //check if the trade order status is DRAFT, if yes, return directly, ensure idempotency.
        if (tradeOrder != null && tradeOrder.getStatus().equals("DRAFT")) {
            tradeOrder.confirm();
            tradeOrderRepository.update(tradeOrder);

            CapitalAccount transferToAccount = capitalAccountRepository.findByUserId(tradeOrderDto.getOppositeUserId());

            transferToAccount.transferTo(tradeOrderDto.getAmount());

            capitalAccountRepository.save(transferToAccount);
        }
    }

    @Transactional
    public void cancelRecord(CapitalTradeOrderDto tradeOrderDto) {
        try {
            Thread.sleep(1000l);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("capital cancel record called. time seq:" + DateFormatUtils.format(Calendar.getInstance(), "yyyy-MM-dd HH:mm:ss"));

        TradeOrder tradeOrder = tradeOrderRepository.findByMerchantOrderNo(tradeOrderDto.getMerchantOrderNo());

        //check if the trade order status is DRAFT, if yes, return directly, ensure idempotency.
        if (null != tradeOrder && "DRAFT".equals(tradeOrder.getStatus())) {
            tradeOrder.cancel();
            tradeOrderRepository.update(tradeOrder);

            CapitalAccount capitalAccount = capitalAccountRepository.findByUserId(tradeOrderDto.getSelfUserId());

            capitalAccount.cancelTransfer(tradeOrderDto.getAmount());

            capitalAccountRepository.save(capitalAccount);
        }
    }
}

