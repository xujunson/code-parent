package com.atu.order.app.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.atu.domain.order.entity.Order;
import com.atu.domain.order.entity.Product;
import com.atu.domain.order.service.OrderDomainService;
import com.atu.domain.order.service.ProductService;
import com.atu.domain.redpacket.model.TradeOrder;
import com.atu.domain.redpacket.service.TradeOrderServiceT;
import com.atu.order.app.controller.vo.PlaceOrderRequest;
import com.atu.order.app.service.AccountService;
import com.atu.order.app.service.PlaceOrderService;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.List;

/**
 * @author: Tom
 * @date: 2020-07-21 17:35
 * @description:
 */
@RestController
public class OrderController {

    @Reference
    PlaceOrderService placeOrderService;

    @Reference
    ProductService productService;

    @Reference
    AccountService accountService;

    @Reference
    OrderDomainService orderService;
    @Reference
    private TradeOrderServiceT tradeOrderServiceT;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView("/index");
        return mv;
    }

    @RequestMapping(value = "/user/{userId}/shop/{shopId}", method = RequestMethod.GET)
    public ModelAndView getProductsInShop(@PathVariable long userId,
                                          @PathVariable long shopId) {
        List<Product> products = productService.findByShopId(shopId);

        ModelAndView mv = new ModelAndView("/shop");

        mv.addObject("products", products);
        mv.addObject("userId", userId);
        mv.addObject("shopId", shopId);

        return mv;
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String getProductsInShop() {
        TradeOrder tradeOrder = tradeOrderServiceT.findByMerchantOrderNo("f91bb32479ee4779a6cc266c3657ac96");
        System.out.println(tradeOrder.toString());
        return "123";
    }

    @RequestMapping(value = "/user/{userId}/shop/{shopId}/product/{productId}/confirm", method = RequestMethod.GET)
    public ModelAndView productDetail(@PathVariable long userId,
                                      @PathVariable long shopId,
                                      @PathVariable long productId) {

        ModelAndView mv = new ModelAndView("product_detail");

        mv.addObject("capitalAmount", accountService.getCapitalAccountByUserId(userId));
        mv.addObject("redPacketAmount", accountService.getRedPacketAccountByUserId(userId));

        mv.addObject("product", productService.findById(productId));

        mv.addObject("userId", userId);
        mv.addObject("shopId", shopId);

        return mv;
    }

    @RequestMapping(value = "/placeorder", method = RequestMethod.POST)
    public RedirectView placeOrder(@RequestParam String redPacketPayAmount,
                                   @RequestParam long shopId,
                                   @RequestParam long payerUserId,
                                   @RequestParam long productId) {


        PlaceOrderRequest request = buildRequest(redPacketPayAmount, shopId, payerUserId, productId);

        String merchantOrderNo = placeOrderService.placeOrder(request.getPayerUserId(), request.getShopId(),
                request.getProductQuantities(), request.getRedPacketPayAmount());

        return new RedirectView("/payresult/" + merchantOrderNo);
    }

    @RequestMapping(value = "/payresult/{merchantOrderNo}", method = RequestMethod.GET)
    public ModelAndView getPayResult(@PathVariable String merchantOrderNo) {

        ModelAndView mv = new ModelAndView("pay_success");

        String payResultTip = null;
        Order foundOrder = orderService.findOrderByMerchantOrderNo(merchantOrderNo);

        payResultTip = foundOrder.getStatus();

        mv.addObject("payResult", payResultTip);

        mv.addObject("capitalAmount", accountService.getCapitalAccountByUserId(foundOrder.getPayerUserId()));
        mv.addObject("redPacketAmount", accountService.getRedPacketAccountByUserId(foundOrder.getPayerUserId()));

        return mv;
    }


    private PlaceOrderRequest buildRequest(String redPacketPayAmount, long shopId, long payerUserId, long productId) {
        BigDecimal redPacketPayAmountInBigDecimal = new BigDecimal(redPacketPayAmount);
        if (redPacketPayAmountInBigDecimal.compareTo(BigDecimal.ZERO) < 0)
            throw new InvalidParameterException("invalid red packet amount :" + redPacketPayAmount);

        PlaceOrderRequest request = new PlaceOrderRequest();
        request.setPayerUserId(payerUserId);
        request.setShopId(shopId);
        request.setRedPacketPayAmount(new BigDecimal(redPacketPayAmount));
        request.getProductQuantities().add(new ImmutablePair<Long, Integer>(productId, 1));
        return request;
    }
}
