package com.atu.future;

import java.util.Random;
import java.util.concurrent.*;

/**
 * @author: Tom
 * @date: 2020-03-24 15:14
 * @description: 演示一个Future的使用方法,lambda形式
 */
public class OneFutureLambda {
    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(10);
        Callable<Integer> callable = () ->{
            Thread.sleep(3000);
            return new Random().nextInt();
        };

        Future<Integer> future = service.submit(callable);
        try {
            System.out.println(future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        service.shutdown();
    }

}
