package com.atu.atomic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.stream.IntStream;

/**
 * @author: Tom
 * @date: 2020-03-18 14:29
 * @description: 演示LongAccumulator的用法
 */
public class LongAccumulatorDemo {
    public static void main(String[] args) {
        // 计算初始值和下一个值
        /*LongAccumulator accumulator = new LongAccumulator((x, y) -> x + y, 0);
        accumulator.accumulate(1);
        System.out.println(accumulator.getThenReset());*/


        //1、计算1+2+3...+9
        /*LongAccumulator accumulator = new LongAccumulator((x, y) -> x + y, 0);

        ExecutorService executor = Executors.newFixedThreadPool(8);

        //实现1-9，每一次提交一个计算
        IntStream.range(1, 10).forEach(i -> executor.submit(() -> accumulator.accumulate(i)));
        executor.shutdown();
        while (!executor.isTerminated()) {

        }
        System.out.println(accumulator.getThenReset());*/


        //2、计算最大值
        LongAccumulator accumulator = new LongAccumulator((x, y) -> Math.max(x, y), 0);

        ExecutorService executor = Executors.newFixedThreadPool(8);

        //实现1-9，每一次提交一个计算
        IntStream.range(1, 10).forEach(i -> executor.submit(() -> accumulator.accumulate(i)));
        executor.shutdown();
        while (!executor.isTerminated()) {

        }
        System.out.println(accumulator.getThenReset());

    }
}
