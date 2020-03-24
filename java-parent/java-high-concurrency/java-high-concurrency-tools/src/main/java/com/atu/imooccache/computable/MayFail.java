package com.atu.imooccache.computable;

import java.io.IOException;

/**
 * @author: Tom
 * @date: 2020-03-24 17:27
 * @description: 耗时计算的实现类，有概率计算失败
 */
public class MayFail implements Computable<String, Integer> {
    @Override
    public Integer compute(String arg) throws Exception {
        double random = Math.random();
        if(random > 0.5) {
            throw new IOException("读取文件出错");
        }
        Thread.sleep(3000);
        return Integer.valueOf(arg);
    }
}
