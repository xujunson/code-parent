package com.atu.design.pattern.event_driven.javabean_event;

/**
 * @Author: Tom
 * @Date: 2021/7/6 2:50 下午
 * @Description:
 */
public class Test {
    public static void main(String[] args) {
        PaymentStatusUpdateBean paymentStatusUpdateBean = new PaymentStatusUpdateBean();

        // 添加监听器
        paymentStatusUpdateBean.addPropertyChangeListener(new PaymentStatusUpdateListener());

        // 更新支付状态
        paymentStatusUpdateBean.updateStatus(3);
    }
}