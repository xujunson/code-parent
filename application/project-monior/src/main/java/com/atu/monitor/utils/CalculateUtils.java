package com.atu.monitor.utils;

import java.text.DecimalFormat;

/**
 * @Author: Tom
 * @Date: 2021/6/10 10:03 上午
 * @Description:
 */
public class CalculateUtils {
    public static String getRate(double rate) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(rate * 100)+"%";
    }
}
