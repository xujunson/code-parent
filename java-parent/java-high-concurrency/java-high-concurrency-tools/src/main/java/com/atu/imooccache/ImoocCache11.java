package com.atu.imooccache;

import com.atu.imooccache.computable.ExpensiveFunction;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: Tom
 * @date: 2020-03-24 20:32
 * @description:
 */
public class ImoocCache11 {

    static ImoocCache10<String, Integer> expensiveComputer = new ImoocCache10<>(new ExpensiveFunction());

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(100);

        long start = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            service.submit(() -> {
                Integer result = null;
                try {
                    result = expensiveComputer.compute("666");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                System.out.println(result);
            });
        }
        service.shutdown();
        while (!service.isTerminated()) {

        }
        System.out.println("总耗时：" + (System.currentTimeMillis() - start));
    }
}
