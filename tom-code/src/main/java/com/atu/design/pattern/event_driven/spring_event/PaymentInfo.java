package com.atu.design.pattern.event_driven.spring_event;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author: Tom
 * @Date: 2021/7/6 2:58 下午
 * @Description:
 */
@Data
@AllArgsConstructor
public class PaymentInfo {
    private int id;
    private String status;

    @Override
    public String toString() {
        return "PaymentInfo{" +
                "id=" + id +
                ", status='" + status + '\'' +
                '}';
    }
}