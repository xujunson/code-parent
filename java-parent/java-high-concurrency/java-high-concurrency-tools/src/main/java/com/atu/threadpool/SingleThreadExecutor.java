package com.atu.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: Tom
 * @date: 2020-03-15 18:19
 * @description:
 */
public class SingleThreadExecutor {
    public static void main(String[] args) {
       ExecutorService executorService =  Executors.newSingleThreadExecutor();

        for (int i = 0; i < 1000; i++) {
            executorService.execute(new Task());
        }
    }
}
