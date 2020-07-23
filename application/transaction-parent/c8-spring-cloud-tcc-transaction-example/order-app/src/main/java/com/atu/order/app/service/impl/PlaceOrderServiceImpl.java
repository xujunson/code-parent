package com.atu.order.app.service.impl;


import com.alibaba.dubbo.config.annotation.Reference;
import com.atu.domain.order.entity.Order;
import com.atu.domain.order.entity.Shop;
import com.atu.domain.order.service.OrderDomainService;
import com.atu.domain.order.service.ShopService;
import com.atu.order.app.service.PaymentService;
import com.atu.order.app.service.PlaceOrderService;
import org.apache.commons.lang3.tuple.Pair;
import org.mengyun.tcctransaction.CancellingException;
import org.mengyun.tcctransaction.ConfirmingException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: Tom
 * @date: 2020-07-21 17:35
 * @description:
 */
@Service
public class PlaceOrderServiceImpl implements PlaceOrderService {

    @Reference
    ShopService shopService;

    @Reference
    OrderDomainService orderService;

    @Reference
    PaymentService paymentService;

    @Override
    public String placeOrder(long payerUserId, long shopId, List<Pair<Long, Integer>> productQuantities, final BigDecimal redPacketPayAmount) {
        Shop shop = shopService.findById(shopId);

        final Order order = orderService.createOrder(payerUserId, shop.getOwnerUserId(), productQuantities);

        Boolean result = false;

        try {

//            ExecutorService executorService = Executors.newFixedThreadPool(2);

//            Future future1 = executorService.submit(new Runnable() {
//                @Override
//                public void run() {
            paymentService.makePayment(order.getMerchantOrderNo(), redPacketPayAmount, order.getTotalAmount().subtract(redPacketPayAmount));
//                }
//            });

//            Future future2 = executorService.submit(new Runnable() {
//                @Override
//                public void run() {
//                    paymentService.makePayment(order.getMerchantOrderNo(), order, redPacketPayAmount, order.getTotalAmount().subtract(redPacketPayAmount));
//                }
//            });
//
//            future1.get();
//            future2.get();

        } catch (ConfirmingException confirmingException) {
            //exception throws with the tcc transaction status is CONFIRMING,
            //when tcc transaction is confirming status,
            // the tcc transaction recovery will try to confirm the whole transaction to ensure eventually consistent.

            result = true;
        } catch (CancellingException cancellingException) {
            //exception throws with the tcc transaction status is CANCELLING,
            //when tcc transaction is under CANCELLING status,
            // the tcc transaction recovery will try to cancel the whole transaction to ensure eventually consistent.
        } catch (Throwable e) {
            //other exceptions throws at TRYING stage.
            //you can retry or cancel the operation.
            e.printStackTrace();
        }

        return order.getMerchantOrderNo();
    }
}
