package com.atu.service;

import org.springframework.stereotype.Service;

/**
 * @Author: Tom
 * @Date: 2021/7/9 10:10 上午
 * @Description:
 */
@Service
public class ArithmeticCalculatorImpl implements ArithmeticCalculator {
    @Override
    public int add(int i, int j) {
        int relust = i + j;
        return relust;
    }

    @Override
    public int sub(int i, int j) {
        int relust = i - j;
        return relust;
    }

    @Override
    public int mul(int i, int j) {
        int relust = i * j;
        return relust;
    }

    @Override
    public int div(int i, int j) {
        int relust = i / j;
        return relust;
    }
}
