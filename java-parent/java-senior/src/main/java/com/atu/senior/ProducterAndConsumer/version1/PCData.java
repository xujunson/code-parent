package com.atu.senior.ProducterAndConsumer.version1;

/**
 * @Author : xsy
 * @Date : Created in 2018/12/21 14:54
 * @Description : 容器数据类型
 */

public class PCData {
    private final int intData;

    public PCData(int d) {
        intData = d;
    }

    public PCData(String d) {
        intData = Integer.valueOf(d);
    }

    public int getData() {
        return intData;
    }

    @Override
    public String toString() {
        return "data:" + intData;
    }
}
